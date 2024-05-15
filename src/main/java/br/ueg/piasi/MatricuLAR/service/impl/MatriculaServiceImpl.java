package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.dto.DadosTermoDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapper;
import br.ueg.piasi.MatricuLAR.model.*;
import br.ueg.piasi.MatricuLAR.repository.MatriculaRepository;
import br.ueg.piasi.MatricuLAR.service.InformacoesMatriculaService;
import br.ueg.piasi.MatricuLAR.service.MatriculaService;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MatriculaServiceImpl extends BaseCrudService<Matricula, Long, MatriculaRepository>
        implements MatriculaService {

    @Autowired
    private DocumentoMatriculaServiceImpl documentoMatriculaService;
    @Autowired
    private InformacoesMatriculaService informacoesMatriculaService;
    @Autowired
    private ResponsavelServiceImpl responsavelService;
    @Autowired
    private PessoaServiceImpl pessoaService;
    @Autowired
    private TutorServiceImpl tutorService;
    @Autowired
    private MatriculaMapper mapper;
    @Autowired
    private EnderecoServiceImpl enderecoService;
    private static String JASPER_TERMO = ".\\src\\main\\resources\\termo.jrxml";

    private final Path root = Paths.get("docs");

    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private NecessidadeEspecialServiceImpl necessidadeEspecialServiceImpl;

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

        matricula.setDocumentoMatricula(new HashSet<>());
        matricula.setAdvertencias(new HashSet<>());

        //TODO tratar info matriculas, setar como null, e dps salvar
        InformacoesMatricula informacoesMatricula = matricula.getInformacoesMatricula();
        matricula.setInformacoesMatricula(null);
        Set<Responsavel> responsavelSet = tratarMatriculaResponsaveis(matricula);

        //TODO tratar endereco
        if(Objects.isNull(matricula.getEndereco().getId())){
            Endereco endereco = enderecoService.incluir(matricula.getEndereco());
             matricula.setEndereco(endereco);
        }

        //TODO tratar necessidade{
        if(Objects.nonNull(matricula.getNecessidades())){
            List<NecessidadeEspecial> necessidadeEspecials = new ArrayList<>();
            for(NecessidadeEspecial especial : matricula.getNecessidades()){
                necessidadeEspecials.add(necessidadeEspecialServiceImpl.incluir(especial));
            }
            matricula.setNecessidades(new HashSet<>(necessidadeEspecials));
        }

        matricula = super.incluir(matricula);

        if(informacoesMatricula != null){
            informacoesMatricula.setMatricula(matricula);
            informacoesMatricula = informacoesMatriculaService.incluir(informacoesMatricula);
        }

        salvarResponsaveis(responsavelSet, matricula);
        matricula.setInformacoesMatricula(informacoesMatricula);
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

    public Resource getDocumentoMatricula(String caminhoDoc){
        return documentoMatriculaService.getDocumentoMatricula(caminhoDoc);
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

    public List<Matricula> listarMatriculaListaemPage(int offset, int pageSize, StatusMatricula statusMatricula) {
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
        try {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            System.out.println("gerando termo");
            List<DadosTermoDTO> listDadosTermo = preencheDTO(idMatricula, cpfTutor);

            Map<String, Object> parametros = new HashMap<String, Object>();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listDadosTermo);

            Path caminhoTermo = this.root.resolve("Termo-Responsabilidade-"+listDadosTermo.get(0).getCpfCrianca()+".pdf");

            JasperReport report = JasperCompileManager.compileReport(JASPER_TERMO);

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

    private List<DadosTermoDTO> preencheDTO(Long idMatricula, String cpfTutor){
        List<DadosTermoDTO> dadosTermo = new ArrayList<>();
        DadosTermoDTO dados = new DadosTermoDTO();
        Matricula matricula = obterPeloId(idMatricula);
        MatriculaDTO matriculaDTO = mapper.toDTO(matricula);
        Endereco endereco = matricula.getEndereco();
        if(!matriculaDTO.getTutorDTOList().isEmpty()){
            matriculaDTO.getTutorDTOList().forEach(tutorDTO -> {
                if(tutorDTO.getCpf().equals(cpfTutor)){
                    dados.setCpfResponsavel(tutorDTO.getCpf());
                    dados.setNomeResponsavel(tutorDTO.getNomeTutor());
                }
            });
        } else{
            throw new BusinessException(SistemaMessageCode.ERRO_GERAR_TERMO);
        }
        dados.setNomeCrianca(matriculaDTO.getNome());
        dados.setEndereco(endereco.getLogradouro()+", "+endereco.getComplemento()+", "+endereco.getBairro());
        dados.setCpfCrianca(matriculaDTO.getCpf());
        dadosTermo.add(dados);
        System.out.println(dados);
        return dadosTermo;
    }

    public Matricula uploadDocumentos(Long idMatricula, MultipartFile[] documentos) {

        for(MultipartFile documento : documentos){

            uploadDocumento(idMatricula, TipoDocumento.CPF_CRIANCA, documento);
        }
        return null;
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
        matricula.setTurma(turma);
        matriculaRepository.save(matricula);
    }
}
