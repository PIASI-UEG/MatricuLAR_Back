package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.dto.AssinaturaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.ResponsavelDTO;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapper;
import br.ueg.piasi.MatricuLAR.model.*;
import br.ueg.piasi.MatricuLAR.repository.MatriculaRepository;
import br.ueg.piasi.MatricuLAR.service.MatriculaService;
import br.ueg.piasi.MatricuLAR.util.DestinatarioAssiDig;
import br.ueg.piasi.MatricuLAR.util.RemetenteAssiDig;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
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

    private void salvarResponsaveis(Set<Responsavel> responsavelSet, Matricula matricula){

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

        if (Objects.nonNull(matricula.getTutorList()) && !matricula.getTutorList().isEmpty()){
            Set<Responsavel> responsavelSet = new HashSet<>();

            for (Tutor tutor : matricula.getTutorList()){

                responsavelSet.add(
                        Responsavel.builder()
                                .matricula(matricula)
                                .pessoa(tutor.getPessoa())
                                .tutor(true)
                                .vinculo(tutor.getVinculo())
                        .build());
            }

            matricula.setResponsaveis(new HashSet<>());
            return  responsavelSet;
        }
        else
            throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_SEM_RESPONSAVEL);
    }

    public Matricula uploadDocumento(Long idMatricula, TipoDocumento tipoDocumento, MultipartFile multipartFile) {

        if (Objects.nonNull(repository.findById(idMatricula).orElse(null))){
            documentoMatriculaService.uploadDocumentos(idMatricula, tipoDocumento, multipartFile);
            return repository.findById(idMatricula).get();
        }

        throw new BusinessException(SistemaMessageCode.ERRO_INCLUIR_DOCUMENTO_MATRICULA_NAO_ENCONTRADA);
    }

    public Resource geraTermo(String cpfCrianca) throws JRException, MalformedURLException {
        System.out.println("gerando termo");
        Pessoa crianca = pessoaService.obterPeloId(cpfCrianca);
        List<AssinaturaDTO> assinatura = preencheDTO(repository.findMatriculaByPessoa(crianca).getId());

        Map<String, Object> parametros = new HashMap<String, Object>();

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(assinatura);


        JasperReport report = JasperCompileManager.compileReport(JASPER_TERMO_ASSINADO);

        JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

        //CAMINHO ONDE SERÁ SALVO O PDF (por enquanto deixando na pasta fotos)
        JasperExportManager.exportReportToPdfFile(print, ".\\src\\main\\resources\\images\\Termo-Responsabilidade-Assinado"+assinatura.get(0).getCpfCrianca()+".pdf");
        System.out.println("Gerando pdf");
        return new UrlResource(".\\src\\main\\resources\\images\\Termo-Responsabilidade-Assinado"+assinatura.get(0).getCpfCrianca()+".pdf");
    }


    public Resource getDocumentoMatricula(String caminhoDoc){
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



    public Matricula uploadTermo(String cpfCrianca, MultipartFile termoAssinado) throws JRException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        try {
            documentoMatriculaService.uploadTermo(cpfCrianca, termoAssinado);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
        return repository.findMatriculaByPessoa(pessoaService.obterPeloId(cpfCrianca));
    }

    private List<AssinaturaDTO> preencheDTO(Long idMatricula){
        List<AssinaturaDTO> assinatura = new ArrayList<>();
        Matricula matricula = this.obterPeloId(idMatricula);
        MatriculaDTO matriculaDTO = this.mapper.toDTO(matricula);
        Endereco endereco = matricula.getEndereco();
        ResponsavelDTO responsavel = matriculaDTO.getResponsaveis().get(0);
        assinatura.add(AssinaturaDTO.builder()
                .endereco(endereco.getLogradouro()+", "+endereco.getComplemento()+", "+endereco.getBairro())
                .nomeResponsavel(responsavel.getNomeResponsavel())
                .cpfCrianca(matricula.getPessoa().getCpf())
                .cpfResponsavel(responsavel.getCpfResponsavel())
                .build());
        return assinatura;
    }

    public Matricula validaMatricula(Matricula matricula) {

        for (DocumentoMatricula documento : matricula.getDocumentoMatricula()){

            if (!documento.getAceito()){
                throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_NAO_ACEITO, documento.getCaminhoDocumento());
            }

        }

        matricula.setStatus(StatusMatricula.ATIVO);

        return alterar(matricula, matricula.getId());

    }

    public Matricula atualizaContraChequeMatricula(Long idMatricula, TipoDocumento tipoDocumento, MultipartFile multipartFile) {

        try {
            if (Objects.nonNull(repository.findById(idMatricula).orElse(null))){
                documentoMatriculaService.atualizaContraChequeMatricula(idMatricula, tipoDocumento, multipartFile);
                return repository.findById(idMatricula).get();
            }
            throw new BusinessException(SistemaMessageCode.ERRO_INCLUIR_DOCUMENTO_MATRICULA_NAO_ENCONTRADA);

        }catch (Exception e){
            throw new BusinessException(SistemaMessageCode.ERRO_INCLUIR_DOCUMENTO_MATRICULA_NAO_ENCONTRADA);
        }

    }
}
