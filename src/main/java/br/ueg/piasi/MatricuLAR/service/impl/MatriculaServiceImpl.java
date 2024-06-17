package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.dto.*;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapper;
import br.ueg.piasi.MatricuLAR.model.*;
import br.ueg.piasi.MatricuLAR.repository.MatriculaRepository;
import br.ueg.piasi.MatricuLAR.service.InformacoesMatriculaService;
import br.ueg.piasi.MatricuLAR.service.MatriculaService;
import br.ueg.prog.webi.api.dto.SearchFieldValue;
import br.ueg.prog.webi.api.exception.ApiMessageCode;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MatriculaServiceImpl extends BaseCrudService<Matricula, Long, MatriculaRepository>
        implements MatriculaService {

    private final ControlePeriodoMatriculaServiceImpl controlePeriodoMatriculaServiceImpl;

    public MatriculaServiceImpl(DocumentoMatriculaServiceImpl documentoMatriculaService,
                                InformacoesMatriculaService informacoesMatriculaService,
                                ResponsavelServiceImpl responsavelService,
                                PessoaServiceImpl pessoaService,
                                TutorServiceImpl tutorService,
                                MatriculaMapper mapper,
                                EnderecoServiceImpl enderecoService,
                                MatriculaRepository matriculaRepository,
                                NecessidadeEspecialServiceImpl necessidadeEspecialServiceImpl,
                                ControlePeriodoMatriculaServiceImpl controlePeriodoMatriculaServiceImpl) {
        this.documentoMatriculaService = documentoMatriculaService;
        this.informacoesMatriculaService = informacoesMatriculaService;
        this.responsavelService = responsavelService;
        this.pessoaService = pessoaService;
        this.tutorService = tutorService;
        this.mapper = mapper;
        this.enderecoService = enderecoService;
        this.matriculaRepository = matriculaRepository;
        this.necessidadeEspecialServiceImpl = necessidadeEspecialServiceImpl;
        this.controlePeriodoMatriculaServiceImpl = controlePeriodoMatriculaServiceImpl;
    }

    private final DocumentoMatriculaServiceImpl documentoMatriculaService;
    private final InformacoesMatriculaService informacoesMatriculaService;
    private final ResponsavelServiceImpl responsavelService;
    private final PessoaServiceImpl pessoaService;
    private final TutorServiceImpl tutorService;
    private final MatriculaMapper mapper;
    private final MatriculaRepository matriculaRepository;
    private final NecessidadeEspecialServiceImpl necessidadeEspecialServiceImpl;
    private final EnderecoServiceImpl enderecoService;

    private final Path root = Paths.get("docs");
    private InformacoesMatricula informacoesMatricula;
    private List<NecessidadeEspecial> necessidadeEspeciais;
    private  Set<Responsavel> responsavelSet;




    @Override
    protected void prepararParaIncluir(Matricula matricula) {

        matricula.setStatus(StatusMatricula.AGUARDANDO_ACEITE);
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
        tratarAntesDeSalvar(matricula);

        matricula = super.incluir(matricula);

        tratarDepoisDeSalvar(matricula);

        return matricula;
    }

    public Matricula incluirComDocumentos(Matricula matricula,List<MultipartFile> documentos) {
        tratarAntesDeSalvar(matricula);

        matricula = super.incluir(matricula);

        tratarDepoisDeSalvar(matricula);
        uploadDocumentos(matricula.getId(), documentos.toArray(new MultipartFile[documentos.size()]), matricula.getInformacoesMatricula());
        return repository.findById(matricula.getId()).get();
    }

    private void tratarDepoisDeSalvar(Matricula matricula) {

        if(this.informacoesMatricula != null){
            this.informacoesMatricula.setMatricula(matricula);
            this.informacoesMatricula = informacoesMatriculaService.incluir(informacoesMatricula);
        }

        matricula.setResponsaveis(salvarResponsaveis(this.responsavelSet, matricula));

        matricula.setInformacoesMatricula(informacoesMatricula);
    }

    private void tratarAntesDeSalvar(Matricula matricula) {

        BigDecimal rendaFamiliar = Objects.nonNull(matricula.getInformacoesMatricula()) ?
                matricula.getInformacoesMatricula().getRendaFamiliar() : new BigDecimal(1);
        if(rendaFamiliar.compareTo(new BigDecimal(100000)) > 0){
            throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_RENDA_FAMILIAR_VALOR_ALTO);
        }

        matricula.setTutorList(salvarTutores(matricula.getTutorList()));
        validarIdadeResponsaveis(matricula);
        this.responsavelSet = tratarMatriculaResponsaveis(matricula);
        validarTodosCpf(matricula);
        validarIdadeCrianca(matricula);
        matricula.setDocumentoMatricula(new HashSet<>());
        matricula.setAdvertencias(new HashSet<>());

        if(Objects.nonNull(matricula.getInformacoesMatricula())){
            this.informacoesMatricula = matricula.getInformacoesMatricula();
            matricula.setInformacoesMatricula(null);
        }

        if(Objects.isNull(matricula.getEndereco().getId())){
            Endereco endereco = enderecoService.incluir(matricula.getEndereco());
            matricula.setEndereco(endereco);
        }

        if(Objects.nonNull(matricula.getNecessidades())){
            for(NecessidadeEspecial necessidadeEspecial : matricula.getNecessidades()){
                necessidadeEspecial.setMatricula(matricula);
            }
        }

    }

    private List<Tutor> salvarTutores(List<Tutor> tutorList) {

        if (Objects.isNull(tutorList) || tutorList.isEmpty()) {
            throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_SEM_RESPONSAVEL);
        }
        List<Tutor> tutoresSalvos = new ArrayList<>();
        for (Tutor tutor : tutorList) {
            if(tutorService.tutorExists(tutor)){
                tutoresSalvos.add(tutor);
            } else{
                Tutor tutorSalvo = tutorService.incluir(tutor);
                tutoresSalvos.add(tutorSalvo);
            }
        }
        return tutoresSalvos;
    }

    private void validarIdadeResponsaveis(Matricula matricula) {
        List<LocalDate> nascimentoTutores = matricula.getTutorList()
                .stream()
                .map(Tutor :: getDataNascimento)
                .toList();
        for (LocalDate nascimento : nascimentoTutores) {
            if (Objects.isNull(nascimento)) {
                throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_RESPONSAVEL_NASCIMENTO_NAO_INFORMADO);
            }
            if (Period.between(nascimento, LocalDate.now()).getYears() < 18) {
                throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_RESPONSAVEL_MENOR_IDADE);
            }
        }
    }

    private void validarIdadeCrianca(Matricula matricula) {
        LocalDate matriculaNascimento = matricula.getNascimento();
        if(Objects.isNull(matriculaNascimento)){
            throw new BusinessException(SistemaMessageCode.ERRO_DATA_NASCIMENTO_ALUNO_DEVE_SER_INFORMADA);
        }
        if(Period.between(matriculaNascimento, LocalDate.now()).getYears() >= 8 ){
            throw new BusinessException(SistemaMessageCode.ERRO_IDADE_ALUNO_ACIMA_DO_PERMITIDO);
        }
    }

    private void validarTodosCpf(Matricula matricula) {
        List<String> cpfs = new ArrayList<>(this.responsavelSet
                .stream()
                .map(Responsavel::getPessoa)
                .map(Pessoa::getCpf)
                .toList());
        cpfs.add(matricula.getPessoa().getCpf());

        List<String> valida = new ArrayList<>();
        for(String cpf : cpfs){
            if (!valida.contains(cpf)) {
                valida.add(cpf);
            }else{
                throw new BusinessException(SistemaMessageCode.ERRO_CPF_REPETIDO_TUTORES_CRIANCA,
                        cpf, matricula.getPessoa().getNome());
            }
        }


    }

    @Override
    public Matricula alterar(Matricula matricula, Long id) {

        if(Objects.isNull(matricula.getStatus())){
            StatusMatricula statusMatricula = repository.findById(id)
                    .orElseThrow(() -> new BusinessException(SistemaMessageCode.ERRO_MATRICULA_NAO_ENCONTRADA))
                    .getStatus();
            matricula.setStatus(statusMatricula);
        }
        tratarAntesDeAlterar(matricula, id);
        matricula=  super.alterar(matricula, id);
        tratarDepoisDeAlterar(matricula,id);

        assert matricula.getId() != null;
        return repository.findById(matricula.getId()).get();
    }

    private void tratarDepoisDeAlterar(Matricula matricula, Long id) {

        if(this.informacoesMatricula != null){
            this.informacoesMatricula.setMatricula(Matricula.builder().id(id).build());
            this.informacoesMatricula = informacoesMatriculaService.alterar(informacoesMatricula, matricula.getId());
        }

        salvarResponsaveisAlterar(this.responsavelSet, id);


    }

    private void salvarResponsaveisAlterar(Set<Responsavel> responsavelSet, Long idMatricula){

        if (Objects.isNull(responsavelSet)) {
            throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_SEM_RESPONSAVEL);
        }

        for (Responsavel responsavel : responsavelSet) {
            responsavel.setMatricula(Matricula.builder().id(idMatricula).build());
            if(responsavel.getId() != null){
                responsavelService.alterar(responsavel, responsavel.getId());
            }else{
               responsavelService.incluir(responsavel);
            }
        }
    }

    private void tratarAntesDeAlterar(Matricula matricula, Long id) {
        matricula.setTutorList(salvarTutores(matricula.getTutorList()));
        validarIdadeResponsaveis(matricula);
        this.responsavelSet = tratarMatriculaResponsaveis(matricula);
        validarTodosCpf(matricula);
        validarIdadeCrianca(matricula);
        matricula.setDocumentoMatricula(new HashSet<>());
        matricula.setAdvertencias(new HashSet<>());

        if(Objects.nonNull(matricula.getInformacoesMatricula())){
            this.informacoesMatricula = matricula.getInformacoesMatricula();
            matricula.setInformacoesMatricula(null);
        }

        if(Objects.isNull(matricula.getEndereco().getId())){
            Endereco endereco = enderecoService.incluir(matricula.getEndereco());
            matricula.setEndereco(endereco);
        }

        if(Objects.nonNull(matricula.getNecessidades())){
            for(NecessidadeEspecial necessidadeEspecial : matricula.getNecessidades()){
                necessidadeEspecial.setMatricula(matricula);
            }
        }
    }

    private Set<Responsavel> salvarResponsaveis(Set<Responsavel> responsavelSet, Matricula matricula){

        if (Objects.isNull(responsavelSet)) {
            throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_SEM_RESPONSAVEL);
        }
        Set<Responsavel> responsavelSetSalvos = new HashSet<>();

        for (Responsavel responsavel : responsavelSet) {
            responsavel.setMatricula(matricula);
            responsavelSetSalvos.add(responsavelService.incluir(responsavel));
        }

        return  responsavelSetSalvos;
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
            matricula.setTutorList(new ArrayList<>());
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

    public Resource getDocumentoMatricula(DocumentoMatriculaDTO documentoMatriculaDTO){
        String caminhoDoc = documentoMatriculaService.obterPorIdMatriculaETipoDocumento
                (documentoMatriculaDTO.getIdMatricula(), documentoMatriculaDTO.getTipoDocumento());
        return documentoMatriculaService.getDocumentoMatricula(caminhoDoc);
    }

    public Resource getTermo(String caminhoDocumento){
        return documentoMatriculaService.getTermo(caminhoDocumento);
    }

    public Matricula validaMatricula(Matricula matricula) {

        validaDocumentosTutores(matricula.getDocumentoMatricula().stream().toList(), matricula.getTutorList().get(0).getCasado());
        validaDocumentosNaoObrigatorios(matricula.getInformacoesMatricula(), matricula.getDocumentoMatricula().stream().toList());

        matricula.setStatus(StatusMatricula.ATIVO);

        return alterar(matricula, matricula.getId());

    }

    private void validaDocumentosNaoObrigatorios(InformacoesMatricula informacoesMatricula, List<DocumentoMatricula> list) {
        for(DocumentoMatricula documento : list) {
                if (informacoesMatricula.getPossuiEcaminhamentoCRAS() &&
                        TipoDocumento.ENCAMINHAMENTO_CRAS.getId().equals(documento.getIdTipoDocumento()) &&
                        !documento.getAceito()) {
                    throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_NAO_ACEITO,
                            TipoDocumento.ENCAMINHAMENTO_CRAS.getDescricao());
                }
                if (informacoesMatricula.getPossuiVeiculoProprio() &&
                        TipoDocumento.DOCUMENTO_VEICULO.getId().equals(documento.getIdTipoDocumento()) &&
                        !documento.getAceito()) {
                    throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_NAO_ACEITO,
                            TipoDocumento.DOCUMENTO_VEICULO.getDescricao());
                }
                if (informacoesMatricula.getPossuiBeneficiosDoGoverno() &&
                        TipoDocumento.COMPROVANTE_BOLSA_FAMILIA.getId().equals(documento.getIdTipoDocumento()) &&
                        !documento.getAceito()) {
                    throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_NAO_ACEITO,
                            TipoDocumento.COMPROVANTE_BOLSA_FAMILIA.getDescricao());
                }
        }
    }

    public void validaDocumentosTutores(List<DocumentoMatricula> documentosCasados, boolean casado) {

        List<TipoDocumento> documentosObrigatorios = Arrays.asList(TipoDocumento.values());
        if(!casado){
            documentosObrigatorios.removeAll(TipoDocumento.getDocumentosObrigatoriosCasados());
        }
        List<String> idDocumentosObrigatorios = documentosObrigatorios.stream().map(TipoDocumento::getId).toList();
        for (DocumentoMatricula documentoMatricula : documentosCasados ){

            if (idDocumentosObrigatorios.contains(documentoMatricula.getIdTipoDocumento()) && !documentoMatricula.getAceito()){
                throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_NAO_ACEITO,
                        TipoDocumento.getById(documentoMatricula.getIdTipoDocumento()).getDescricao());
            }
        }

    }


    public Matricula atualizaDocumentoMatricula(Long idMatricula, TipoDocumento tipoDocumento, MultipartFile multipartFile) {


        if (Objects.nonNull(repository.findById(idMatricula).orElse(null))){
            documentoMatriculaService.atualizaDocumentoMatricula(idMatricula, tipoDocumento, multipartFile);
            return repository.findById(idMatricula).get();
        }
        throw new BusinessException(SistemaMessageCode.ERRO_INCLUIR_DOCUMENTO_MATRICULA_NAO_ENCONTRADA);

    }

    @Override
    public Matricula obterPeloId(Long id) {
        Matricula matricula = super.obterPeloId(id);

        List<Responsavel> responsavelTutores = matricula
                .getResponsaveis()
                .stream()
                .filter(Responsavel::getTutor).toList();

        List<Tutor> tutorList = new ArrayList<>();

        responsavelTutores.forEach(responsavel -> {
            tutorList.add(tutorService.obterPeloId(responsavel.getPessoa().getCpf()));
        });

        matricula.setTutorList(tutorList);
        return matricula;
    }

    public List<Matricula> listarMatriculasListagemPorStatus(StatusMatricula statusMatricula) {

      return repository.findByStatusFetchTurma(statusMatricula)
                .orElseThrow(() -> new BusinessException
                        (SistemaMessageCode.ERRO_LISTAR_MATRICULA_STATUS, statusMatricula.getDescricao()));
    }

    public List<Matricula> listarMatriculaListagemPage(int offset, int pageSize, StatusMatricula statusMatricula) {
        return repository.findByStatusFetchTurmaPage(offset, pageSize, statusMatricula.getId())
                .orElseThrow(() -> new BusinessException
                        (SistemaMessageCode.ERRO_LISTAR_MATRICULA_STATUS, statusMatricula.getDescricao()
                        )
                );
    }

    public Integer countRowsWithStatus(StatusMatricula statusMatricula) {
        return repository.countAllWithStatus(statusMatricula.getId());
    }

    public Matricula geraTermo(Long idMatricula, String cpfTutor) throws JRException, IOException {
        String jasperTermo = "termo.jrxml";
        try (InputStream termo = this.getClass().getClassLoader().getResourceAsStream(jasperTermo)) {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            System.out.println("gerando termo");
            List<DadosTermoDTO> listDadosTermo = preencheDTO(idMatricula, cpfTutor);

            Map<String, Object> parametros = new HashMap<String, Object>();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listDadosTermo);

            System.out.println(dataSource.getData());

            String cpfCrianca = listDadosTermo.get(0).getCpfCrianca();
            Path caminhoTermo = this.root.resolve("Termo-Responsabilidade-"+ cpfCrianca +".pdf");

            JasperReport report = JasperCompileManager.compileReport(termo);

            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

            //CAMINHO ONDE SERÁ SALVO O PDF (por enquanto deixando na pasta fotos)
            JasperExportManager.exportReportToPdfFile(print, caminhoTermo.toString());
            System.out.println("Gerando pdf");

            String caminhoT2 = geraTermoAutorizacao(idMatricula, cpfTutor);
            concatenaTermos(caminhoTermo.toString(), caminhoT2, cpfCrianca);

            File deleteTermo1 = new File(caminhoTermo.toString());
            deleteTermo1.delete();

            File deleteTermo2 = new File(caminhoT2);
            deleteTermo2.delete();
            return obterPeloId(idMatricula);

        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public Matricula geraPdfDadosMatricula(Long idMatricula) throws JRException, IOException {
        String jasperTermo = "dados_matricula.jrxml";
        try (InputStream pdf = this.getClass().getClassLoader().getResourceAsStream(jasperTermo)) {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            List<MatriculaRelatorioDTO> listDados = new ArrayList<>();
            MatriculaRelatorioDTO matricula = mapper.toMatriculaRelatorioDTO(obterPeloId(idMatricula));

            listDados.add(matricula);
            //            System.out.println(listDados.get(0));

            Map<String, Object> parametros = new HashMap<String, Object>();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listDados);
            System.out.println(dataSource.getData());

            Path caminhoTermo = this.root.resolve(idMatricula+"_Dados-Matricula.pdf");

            JasperReport report = JasperCompileManager.compileReport(pdf);

            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

            //CAMINHO ONDE SERÁ SALVO O PDF (por enquanto deixando na pasta fotos)
            JasperExportManager.exportReportToPdfFile(print, caminhoTermo.toString());
            System.out.println("Gerando pdf");
            return obterPeloId(idMatricula);

        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void concatenaTermos(String caminhoTermo1, String caminhoTermo2, String cpfCrianca) throws IOException {
        PdfReader termo1 = new PdfReader(caminhoTermo1);
        PdfReader termo2 = new PdfReader(caminhoTermo2);

        PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(this.root.resolve("Termos-"+cpfCrianca+".pdf").toString()));

        copy.addDocument(termo1);
        copy.addDocument(termo2);
        copy.close();
    }

    public Page<MatriculaListagemDTO> mapPageToMatriculaListagemDto(Page<Matricula> page){
        return page.map(this.mapper::toMatriculaListagemDTO);
    }

    public Page<MatriculaListagemDTO> getPageListagemDTO(List<SearchFieldValue> searchFieldValues, Integer page, Integer size, List<String> sort) {
        Sort sortObject = Sort.unsorted();
        if (Objects.nonNull(sort)) {
            List<Sort.Order> orderList = new ArrayList<>();
            sort.forEach((s) -> {
                orderList.add(Sort.Order.asc(s));
            });
            sortObject = Sort.by(orderList);
        }

        Pageable pageable = PageRequest.of(page, size, sortObject);
        Page<Matricula> listSearchFields = searchFieldValuesPage(pageable, searchFieldValues);
        if (listSearchFields.isEmpty()) {
            throw new BusinessException(ApiMessageCode.SEARCH_FIELDS_RESULT_NONE);
        } else {
            return mapPageToMatriculaListagemDto(listSearchFields);
        }
    }

    public String geraTermoAutorizacao(Long idMatricula, String cpfTutor) throws JRException, IOException {
        String jasperTermo = "uso_de_imagem.jrxml";
        try (InputStream termo = this.getClass().getClassLoader().getResourceAsStream(jasperTermo)) {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            List<DadosTermoDTO> listDadosTermo = preencheDTO(idMatricula, cpfTutor);

            Map<String, Object> parametros = new HashMap<String, Object>();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listDadosTermo);

            Path caminhoTermo = this.root.resolve("Termo-Autorizacao_Imagem-"+listDadosTermo.get(0).getCpfCrianca()+".pdf");

            JasperReport report = JasperCompileManager.compileReport(termo);

            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

            //CAMINHO ONDE SERÁ SALVO O PDF (por enquanto deixando na pasta fotos)
            JasperExportManager.exportReportToPdfFile(print, caminhoTermo.toString());

            return caminhoTermo.toString();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    private List<DadosTermoDTO> preencheDTO(Long idMatricula, String nomeTutor){
        List<DadosTermoDTO> dadosTermo = new ArrayList<>();
        DadosTermoDTO dados = new DadosTermoDTO();
        Matricula matricula = obterPeloId(idMatricula);
        MatriculaDTO matriculaDTO = mapper.toDTO(matricula);
        if(!matriculaDTO.getTutorDTOList().isEmpty()){
            matriculaDTO.getTutorDTOList().forEach(tutorDTO -> {
                if(tutorDTO.getNomeTutor().equals(nomeTutor)){
                    dados.setNomeTutor(nomeTutor);
                    dados.setTelefoneTutor(tutorDTO.getPessoaTelefone());
                }
            });
        } else{
            throw new BusinessException(SistemaMessageCode.ERRO_GERAR_TERMO);
        }
        dados.setNomeCrianca(matriculaDTO.getNome());
        dados.setCpfCrianca(matriculaDTO.getCpf());
        dadosTermo.add(dados);
        return dadosTermo;
    }

    public Matricula uploadDocumentos(Long idMatricula, MultipartFile[] documentos, InformacoesMatricula informacoesMatricula) {

        Matricula matricula = obterPeloId(idMatricula);
        if (Objects.nonNull(matricula)) {
            List<Tutor> turores = matricula.getTutorList();
            boolean tutoresCasados = turores.get(0).getCasado();

            if (tutoresCasados && documentos.length < 17){
                    throw new BusinessException(SistemaMessageCode.ERRO_QUANTIDADE_DOCUMENTO_OBRIGATORIO);
            }
            if (!tutoresCasados && documentos.length < 10){
                throw new BusinessException(SistemaMessageCode.ERRO_QUANTIDADE_DOCUMENTO_OBRIGATORIO);
            }

            for(int i = 0 ; i < documentos.length; i++){
                TipoDocumento tipoDocumento = tipoDocumentoPelaPosicao(i);
                String nomeDoc = documentos[i].getOriginalFilename()
                        .substring(documentos[i].getOriginalFilename().length()-3);
                if(!nomeDoc.equals("txt")){
                    uploadDocumento(idMatricula, tipoDocumento, documentos[i]);
                }else validaDocObrigatorio(tipoDocumento, tutoresCasados, informacoesMatricula);
            }
            return matricula;
        }
        excluir(idMatricula);
        throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_NAO_ENCONTRADA, idMatricula);
    }

    private void validaDocObrigatorio(TipoDocumento tipoDocumento, boolean tutoresCasados,
                                      InformacoesMatricula informacoesMatricula) {

        if (tipoDocumento.equals(TipoDocumento.CPF_CRIANCA)){
            return;
        }

        if(Objects.nonNull(informacoesMatricula)){

            if(tipoDocumento.equals(TipoDocumento.DOCUMENTO_VEICULO) && informacoesMatricula.getPossuiVeiculoProprio()){
                throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_DOCUMENTO_OBRIGATORIO, tipoDocumento.getDescricao());
            }
            if (tipoDocumento.equals(TipoDocumento.COMPROVANTE_BOLSA_FAMILIA) && informacoesMatricula.getPossuiBeneficiosDoGoverno()){
                throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_DOCUMENTO_OBRIGATORIO, tipoDocumento.getDescricao());
            }
            if (tipoDocumento.equals(TipoDocumento.ENCAMINHAMENTO_CRAS) && informacoesMatricula.getPossuiEcaminhamentoCRAS()){
                throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_DOCUMENTO_OBRIGATORIO, tipoDocumento.getDescricao());
            }
        }else throw new BusinessException(SistemaMessageCode.ERRO_INFORMACOES_MATRICULA_NAO_INFORMADA);

        List<TipoDocumento> documentosNaoObrigados = TipoDocumento.getDocumentosNaoObrigatoriosGeral();
        List<TipoDocumento> documentosObrigatoriosCasados = TipoDocumento.getDocumentosObrigatoriosCasados();
        if (!tutoresCasados) {
            if (!documentosObrigatoriosCasados.contains(tipoDocumento) && !documentosNaoObrigados.contains(tipoDocumento)) {
                throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_DOCUMENTO_OBRIGATORIO, tipoDocumento.getDescricao());
            }
        }else{
            if (!documentosNaoObrigados.contains(tipoDocumento)){
                throw new BusinessException(SistemaMessageCode.ERRO_DOCUMENTO_DOCUMENTO_OBRIGATORIO, tipoDocumento.getDescricao());
            }
        }
    }


    private TipoDocumento tipoDocumentoPelaPosicao(int i) {
        return TipoDocumento.getByPosicaoArray(i);
    }

    public List<Matricula> listarAlunosPorTurma(Long idTurma) {
        List<Matricula> matriculas =  matriculaRepository.findByTurma_Id(idTurma).orElse(null);

       if(Objects.isNull(matriculas) || matriculas.isEmpty()) {
            throw new BusinessException(SistemaMessageCode.ERRO_SEM_ALUNOS_TURMA, idTurma);
        }

        return matriculas;
    }

    public void addTurmaPorNroMatricula(Long idAluno, Turma turma) {
        Matricula matricula = repository.findById(idAluno)
                .orElseThrow(() ->
                        new BusinessException(SistemaMessageCode.ERRO_MATRICULA_NAO_ENCONTRADA, idAluno));
        if(Objects.nonNull(matricula.getTurma()) && matricula.getTurma().getId().equals(turma.getId())){
            throw new BusinessException(SistemaMessageCode.ERRO_ALUNO_JA_ESTA_NA_TURMA,
                    matricula.getPessoa().getNome());
        }
        if(Objects.nonNull(matricula.getStatus()) && !matricula.getStatus().equals(StatusMatricula.ATIVO)){
            throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_NAO_ATIVA_PARA_TURMA,
                    matricula.getPessoa().getNome());
        }
        matricula.setTurma(turma);
        matriculaRepository.save(matricula);
    }

    public Long quantidadeMatriculasPorStatus(StatusMatricula statusMatricula) {
        return matriculaRepository.countByStatus(statusMatricula);
    }

    public Long quantidadeTotalMatriculas() {
        return repository.count();
    }

    public void validaPeriodoMatricula() {
        if (!controlePeriodoMatriculaServiceImpl.obterPeloId(1L)
                .getAceitandoCadastroMatricula()){
            throw  new BusinessException(SistemaMessageCode.ERRO_PERIODO_MATRICULA_NAO_ACEITANDO);
        };
    }

    public void removerTurma(List<Long> idMatriculas) {
        for (Long idMatricula : idMatriculas){
            Matricula matricula = matriculaRepository.findById(idMatricula)
                    .orElseThrow(() ->
                            new BusinessException(SistemaMessageCode.ERRO_MATRICULA_NAO_ENCONTRADA, idMatricula));
            matricula.setTurma(null);
            matriculaRepository.saveAndFlush(matricula);
        }
    }
}
