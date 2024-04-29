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
import org.apache.bcel.verifier.VerificationResult;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
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
import java.io.FileOutputStream;
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
    private MatriculaRepository matriculaRepository;

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


    public Matricula uploadTermoValidar(String cpfCrianca, MultipartFile documento) {

        try {
            //Pegar chave publica do responsavel pelo cpf da crianca
            Matricula criancaMatri = matriculaRepository.findMatriculaByPessoa(pessoaService.obterPeloId(cpfCrianca));
            MatriculaDTO matriculaDTO = mapper.toDTO(criancaMatri);
            List<ResponsavelDTO> responsaveis = matriculaDTO.getResponsaveis();
            ResponsavelDTO responsavelDTO = responsaveis.get(0);
            String chavePublica = responsavelDTO.getChavePub();
            PublicKey pubKey = convertChave(chavePublica);

            // pegar termo do back e gerar hash para comparar
            File termo = new File(this.root.resolve("Termo-Responsabilidade" + cpfCrianca + ".pdf").toUri());
            String hashArqAntigo = calcularHashSHA256(termo);

            // Cria um arquivo temporario do termo assinado
            File termoTemporario = File.createTempFile("temp", ".pdf");
            documento.transferTo(termoTemporario);

            // Carrega o termo assinado e pega os anexos
            Document document = new Document(termoTemporario.getAbsolutePath());
            EmbeddedFileCollection attachments = document.getEmbeddedFiles();

            // Verifique se há anexos, se tiver pega o anexo da assinatura
            if (attachments.size() > 0) {
                Files.copy(attachments.get_Item(1).getContents(), this.root.resolve("assinatura.p7s"));
            } else {
                System.out.println("Não há anexos no PDF.");
            }

            File assinaturaArq = new File(this.root.resolve("assinatura.p7s").toString());


            // Inicializando o objeto Signature com o algoritmo utilizado
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(pubKey);

            // Lendo o conteúdo do arquivo da assinatura.p7s
            FileInputStream ass = new FileInputStream(this.root.resolve("assinatura.p7s").toString());
            byte[] assinaturaBytes = ass.readAllBytes();


            //verifica o hash
            signature.update(hashArqAntigo.getBytes());


            // Verificando a assinatura
            if (signature.verify(assinaturaBytes)) {
                System.out.println("A assinatura é válida.");
            } else {
                System.out.println("A assinatura é inválida.");
            }
            ass.close();

            // Excluir o arquivo temporário
            if (termoTemporario.delete() && assinaturaArq.delete()){
                System.out.println("Arquivos apagados");
            }


            return criancaMatri;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new BusinessException(SistemaMessageCode.ERRO_CHAVE_PUBLICA_NAO_EXISTE);
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
