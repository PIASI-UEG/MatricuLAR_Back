package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.controller.ResponsavelController;
import br.ueg.piasi.MatricuLAR.dto.AssinaturaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.ResponsavelDTO;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapper;
import br.ueg.piasi.MatricuLAR.model.*;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkResponsavel;
import br.ueg.piasi.MatricuLAR.repository.MatriculaRepository;
import br.ueg.piasi.MatricuLAR.service.MatriculaService;
import br.ueg.piasi.MatricuLAR.util.DestinatarioAssiDig;
import br.ueg.piasi.MatricuLAR.util.RemetenteAssiDig;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import com.aspose.pdf.Document;
import com.aspose.pdf.EmbeddedFileCollection;
import com.aspose.pdf.FileSpecification;
import com.aspose.pdf.facades.PdfFileSignature;
import io.swagger.v3.core.util.Json;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;

import static br.ueg.piasi.MatricuLAR.util.TermoDeResponsabilidade.JASPER_TERMO;
import static br.ueg.piasi.MatricuLAR.util.TermoDeResponsabilidade.JASPER_TERMO_ASSINADO;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MatriculaServiceImpl extends BaseCrudService<Matricula, Long, MatriculaRepository>
        implements MatriculaService {

    @Autowired
    private DocumentoMatriculaServiceImpl documentoMatriculaService;

    @Autowired
    private ResponsavelServiceImpl responsavelService;

    @Autowired
    private PessoaServiceImpl pessoaService;

    @Autowired
    private MatriculaMapper mapper;

    @Autowired
    private ResponsavelController responsavelController;


    private final Path root = Paths.get("docs");


    @Override
    protected void prepararParaIncluir(Matricula matricula) {

        matricula.setStatus(StatusMatricula.INATIVO);
        matricula.setPessoa(pessoaService.incluir(
                        Pessoa.builder()
                                .nome(matricula.getPessoa().getNome())
                                .cpf(matricula.getPessoa().getCpf())
                                .build()
                )
        );
    }

    @Override
    protected void validarDados(Matricula entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Matricula entidade) {

    }

    @Override
    public Matricula incluir(Matricula matricula) {

        matricula.setDocumentoMatricula(new HashSet<>());
        matricula.setAdvertencias(new HashSet<>());
        Set<Responsavel> responsavelSet = tratarMatriculaResponsaveis(matricula);

        matricula = super.incluir(matricula);

        salvarResponsaveis(responsavelSet, matricula);

        return matricula;
    }

    @Override
    public Matricula alterar(Matricula entidade, Long id) {

        return super.alterar(entidade, id);
    }

    private void salvarResponsaveis(Set<Responsavel> responsavelSet, Matricula matricula) {

        if (Objects.isNull(responsavelSet)) {
            throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_SEM_RESPONSAVEL);
        }
        Set<Responsavel> responsavelSetSalvos = new HashSet<>();

        for (Responsavel responsavel : responsavelSet) {
            responsavel.setMatricula(matricula);
            responsavelSetSalvos.add(responsavelService.incluir(responsavel));
        }

        matricula.setResponsaveis(responsavelSetSalvos);
    }

    private Set<Responsavel> tratarMatriculaResponsaveis(Matricula matricula) {

        if (Objects.nonNull(matricula.getTutorList()) && !matricula.getTutorList().isEmpty()) {
            Set<Responsavel> responsavelSet = new HashSet<>();

            for (Tutor tutor : matricula.getTutorList()) {

                responsavelSet.add(
                        Responsavel.builder()
                                .matricula(matricula)
                                .pessoa(tutor.getPessoa())
                                .tutor(true)
                                .vinculo(tutor.getVinculo())
                                .build());
            }

            matricula.setResponsaveis(new HashSet<>());
            return responsavelSet;
        } else
            throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_SEM_RESPONSAVEL);
    }

    public Matricula uploadDocumento(Long idMatricula, TipoDocumento tipoDocumento, MultipartFile multipartFile) {

        if (Objects.nonNull(repository.findById(idMatricula).orElse(null))) {
            documentoMatriculaService.uploadDocumentos(idMatricula, tipoDocumento, multipartFile);
            return repository.findById(idMatricula).get();
        }

        throw new BusinessException(SistemaMessageCode.ERRO_INCLUIR_DOCUMENTO_MATRICULA_NAO_ENCONTRADA);
    }

    public Resource geraTermo(String cpfCrianca) throws JRException, IOException {
        System.out.println("gerando termo");
        Pessoa crianca = pessoaService.obterPeloId(cpfCrianca);
        List<AssinaturaDTO> assinatura = preencheDTO(repository.findMatriculaByPessoa(crianca).getId());

        Map<String, Object> parametros = new HashMap<String, Object>();

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(assinatura);


        JasperReport report = JasperCompileManager.compileReport(JASPER_TERMO);

        JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        //CAMINHO ONDE SERÁ SALVO O PDF (por enquanto deixando na pasta fotos)
        Path caminhoTermo = this.root.resolve("Termo-Responsabilidade" + assinatura.get(0).getCpfCrianca() + ".pdf");
        JasperExportManager.exportReportToPdfFile(print, caminhoTermo.toString());
        System.out.println("Gerando pdf");
        Path caminho = this.root.resolve("Termo-Responsabilidade" + assinatura.get(0).getCpfCrianca() + ".pdf");
        return new UrlResource(caminho.toUri());
    }


    public Resource getDocumentoMatricula(String caminhoDoc) {
        return documentoMatriculaService.getDocumentoMatricula(caminhoDoc);
    }

//    public String uploadTermoAssinadoFront(MultipartFile multipartFile, PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
//
//        // TODO implementar o salvamento do arquivo assinado e da chave publica
//        // TODO apos isso implemenar algo para receber um pdf e validar ele de acordo com o pdf gerado no back
////        try {
////            if (!Files.exists(root)) {
////                Files.createDirectories(root);
////            }
////
////            Path pathCaminhoDoc = this.root.resolve(nomeArquivo);
////            Files.copy(documento.getInputStream(), pathCaminhoDoc);
////
////            this.repository.saveAndFlush(
////                    DocumentoMatricula.builder()
////                            .matricula(Matricula.builder().id(idMatricula).build())
////                            .idTipoDocumento(tipoDocumento.getId())
////                            .caminhoDocumento(nomeArquivo)
////                            .aceito(false)
////                            .build()
////            );
////
////        }catch (Exception e){
////            throw new BusinessException(SistemaMessageCode.ERRO_INCLUIR_DOCUMENTO, e);
////        }
////        if (Objects.nonNull(multipartFile)){
////            if(validarAssinatura(publicKey, multipartFile)) {
////                return "Documento verificado, satatus ok";
////            }else {
////                return "Documento nao aceito, satatus invalido";
////            }
////        }
////        return "Documento in";
//
//    }

//    private boolean validarAssinatura(PublicKey pubKey, MultipartFile multipartFile) throws
//            NoSuchAlgorithmException, InvalidKeyException, SignatureException {
//        Signature clientSig = Signature.getInstance("DSA");
//        clientSig.initVerify(pubKey);
//        clientSig.update(termo.toString().getBytes());
//
//        //verifica assinatura
//        if (clientSig.verify(assinatura)) {
//            //Mensagem corretamente assinada
//            System.out.println("A Mensagem recebida foi assinada corretamente.");
//        } else {
//            //Mensagem não pode ser validada
//            System.out.println("A Mensagem recebida NÃO pode ser validada.");
//        }
//    }

    public Matricula uploadTermoValidar(String cpfCrianca, MultipartFile documento) {

        try {
            //Pegar chave publica
            Matricula criancaMatri = obterPeloId(1l);
            MatriculaDTO matriculaDTO = mapper.toDTO(criancaMatri);
            List<ResponsavelDTO> responsaveis = matriculaDTO.getResponsaveis();
            ResponsavelDTO responsavelDTO = responsaveis.get(0);
            String chavePublica = responsavelDTO.getChavePub();
            PublicKey pubKey = convertChave(chavePublica);

            // pegar termo do back e gerar hash para comparar
            File termo = new File(this.root.resolve("Termo-Responsabilidade" + cpfCrianca + ".pdf").toUri());
            String hashArqAntigo = calcularHashSHA256(termo);
            System.out.println("Document Hash " + hashArqAntigo);

            //remover anexo do pdf onde esta assinatura

            // Configure um novo arquivo para ser adicionado como anexo


            // Cria um arquivo temporario
            File termoTemporario = File.createTempFile("temp", ".pdf");
            documento.transferTo(termoTemporario);

            // Carregar o arquivo e salva o anexo
            Document document = new Document(termoTemporario.getAbsolutePath());
            System.out.println("embed" + document.getEmbeddedFiles());
            EmbeddedFileCollection attachments = document.getEmbeddedFiles();

            // Verifique se há anexos
            if (attachments.size() > 0) {
                System.out.println("cheguei aqui");
                Files.copy(attachments.get_Item(1).getContents(), this.root.resolve("assinatura.p7s"));
            } else {
                System.out.println("Não há anexos no PDF.");
            }

//            byte[] assinatura = new FileInputStream( new File(this.root.resolve("assinatura.p7s").toString())).readAllBytes();
//
//            // Assinatura do PDF (já temos essa informação)
//
//            // Verificar a assinatura usando a chave pública
//            Signature signature = Signature.getInstance("SHA256withRSA");
//            signature.initVerify(pubKey);
//            signature.update(hashArqAntigo.getBytes());
//            boolean assinaturaValida = signature.verify(assinatura);
//


            // Inicializando o objeto Signature para verificação
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(pubKey);

// Lendo o conteúdo do arquivo .p7s
            FileInputStream fis = new FileInputStream(this.root.resolve("assinatura.p7s").toString());
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                signature.update(buffer, 0, length);
            }


// Verificando a assinatura
            boolean assinaturaValida = signature.verify(fis.readAllBytes());
            if (assinaturaValida) {
                System.out.println("A assinatura é válida.");
            } else {
                System.out.println("A assinatura é inválida.");
            }
            fis.close();

            // Excluir o arquivo temporário
            termoTemporario.delete();



        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new BusinessException(SistemaMessageCode.ERRO_CHAVE_PUBLICA_NAO_EXISTE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return repository.findById(1L).get();


    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String calcularHashSHA256(File arquivo) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        FileInputStream fis = new FileInputStream(arquivo);
        DigestInputStream dis = new DigestInputStream(fis, digest);

        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = dis.read(buffer)) != -1) {

        }

        byte[] hashBytes = digest.digest();


        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }


        dis.close();

        return hexString.toString();
    }

    private PublicKey convertChave(String chavePublica) throws NoSuchAlgorithmException, InvalidKeySpecException {
        JSONObject jwk = new JSONObject(chavePublica);

        String modulusBase64 = jwk.getString("n");
        String exponentBase64 = jwk.getString("e");

        byte[] modulusBytes = Base64.getUrlDecoder().decode(modulusBase64);
        byte[] exponentBytes = Base64.getUrlDecoder().decode(exponentBase64);

        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(new java.math.BigInteger(1, modulusBytes), new java.math.BigInteger(1, exponentBytes));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(rsaPublicKeySpec);
    }


    public Matricula uploadTermo(String cpfCrianca, String chavePub) throws JRException, NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            System.out.println("chavePubchegou" + chavePub);


            // salvar chave publica no primeiro tutor
            Matricula criancaMatri = obterPeloId(1l);
            MatriculaDTO matriculaDTO = mapper.toDTO(criancaMatri);
            List<ResponsavelDTO> responsaveis = matriculaDTO.getResponsaveis();
            ResponsavelDTO responsavelDTO = responsaveis.get(0);

            responsavelDTO.setChavePub(chavePub);

            PkResponsavel pkResponsavel = new PkResponsavel();

            pkResponsavel.setMatricula(1L);
            pkResponsavel.setPessoa("12345678911");

            responsavelController.alterar(responsavelDTO, pkResponsavel);

        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
        return repository.findMatriculaByPessoa(pessoaService.obterPeloId(cpfCrianca));
    }

    private List<AssinaturaDTO> preencheDTO(Long idMatricula) {
        List<AssinaturaDTO> assinatura = new ArrayList<>();
        Matricula matricula = this.obterPeloId(idMatricula);
        MatriculaDTO matriculaDTO = this.mapper.toDTO(matricula);
        Endereco endereco = matricula.getEndereco();
        ResponsavelDTO responsavel = matriculaDTO.getResponsaveis().get(0);
        assinatura.add(AssinaturaDTO.builder()
                .endereco(endereco.getLogradouro() + ", " + endereco.getComplemento() + ", " + endereco.getBairro())
                .nomeResponsavel(responsavel.getNomeResponsavel())
                .cpfCrianca(matricula.getPessoa().getCpf())
                .cpfResponsavel(responsavel.getCpfResponsavel())
                .build());
        return assinatura;
    }

    public Matricula validaMatricula(Matricula matricula) {

        for (DocumentoMatricula documento : matricula.getDocumentoMatricula()) {

            if (!documento.getAceito()) {
                throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_NAO_ACEITO, documento.getCaminhoDocumento());
            }

        }

        matricula.setStatus(StatusMatricula.ATIVO);

        return alterar(matricula, matricula.getId());

    }

    public Matricula atualizaContraChequeMatricula(Long idMatricula, TipoDocumento tipoDocumento, MultipartFile multipartFile) {

        try {
            if (Objects.nonNull(repository.findById(idMatricula).orElse(null))) {
                documentoMatriculaService.atualizaContraChequeMatricula(idMatricula, tipoDocumento, multipartFile);
                return repository.findById(idMatricula).get();
            }
            throw new BusinessException(SistemaMessageCode.ERRO_INCLUIR_DOCUMENTO_MATRICULA_NAO_ENCONTRADA);

        } catch (Exception e) {
            throw new BusinessException(SistemaMessageCode.ERRO_INCLUIR_DOCUMENTO_MATRICULA_NAO_ENCONTRADA);
        }

    }
}
