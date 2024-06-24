package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.Turma;
import br.ueg.piasi.MatricuLAR.repository.TurmaRepository;
import br.ueg.piasi.MatricuLAR.service.TurmaService;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TurmaServiceImpl extends BaseCrudService<Turma, Long, TurmaRepository>
        implements TurmaService {

    @Autowired
    private MatriculaServiceImpl matriculaService;

    @Override
    protected void prepararParaIncluir(Turma entidade) {

    }

    @Override
    protected void validarDados(Turma turma) {
    }

    @Override
    protected void validarCamposObrigatorios(Turma entidade) {
    }

    @Override
    public Turma incluir(Turma modelo) {
        modelo.setAlunos(new HashSet<>());
        return super.incluir(modelo);
    }

    public Turma adicionaAlunosTurma(Long idTurma, List<Long> idAlunos) {
        Turma turma = repository.findById(idTurma).orElseThrow(() ->
                new BusinessException(SistemaMessageCode.ERRO_TURMA_NAO_ENCONTRADA, idTurma));
        for (Long idAluno : idAlunos){
            matriculaService.addTurmaPorNroMatricula(idAluno, turma);
        }
        return repository.findById(idTurma).get();
    }

    public Long quantidadeTotal() {
        return repository.count();
    }

    @Override
    public Turma alterar(Turma turma, Long id) {
        if(Objects.isNull(turma.getAlunos()) || turma.getAlunos().isEmpty()){
            turma.setAlunos(new HashSet<>());
            Turma turmaBd = repository.getReferenceById(id);
            if(Objects.nonNull(turmaBd.getAlunos()) && !turmaBd.getAlunos().isEmpty()){
                turma.setAlunos(turmaBd.getAlunos());
            }
        }
        return super.alterar(turma, id);
    }

    public Turma adicionarAlunoTurma(Long idTurma, Long idAluno) {
        Matricula aluno = matriculaService.obterPeloId(idAluno);
        Turma turma = repository.findById(idTurma).orElseThrow(() ->
                new BusinessException(SistemaMessageCode.ERRO_TURMA_NAO_ENCONTRADA, idTurma));
        if(aluno != null && aluno.getStatus().equals(StatusMatricula.ATIVO)){
            if(!turma.getAlunos().contains(aluno)){
                matriculaService.addTurmaPorNroMatricula(idAluno, turma);
            }
            else {
                throw new BusinessException(SistemaMessageCode.ERRO_ALUNO_JA_ESTA_NA_TURMA, idAluno);
            }
        }
        else {
            throw new BusinessException(SistemaMessageCode.ERRO_MATRICULA_STATUS_NAO_ATIVA, idAluno);
        }
        return repository.findById(idTurma).get();
    }

    public Turma removeAlunosTurma(Long idTurma, List<Long> idAlunos) {

        if(!repository.existsById(idTurma)) {
            throw new BusinessException(SistemaMessageCode.ERRO_TURMA_NAO_ENCONTRADA, idTurma);
        }

        matriculaService.removerTurma(idAlunos);

        return repository.findById(idTurma).get();
    }

    @Override
    public Turma excluir(Long id) {
        Turma turma = repository.findById(id)
                .orElseThrow(()-> new BusinessException(SistemaMessageCode.ERRO_TURMA_NAO_ENCONTRADA, id));
        if (turma.getAlunos() == null || turma.getAlunos().isEmpty()){
            throw new BusinessException(SistemaMessageCode.ERRO_EXLCUIR_TURMA_ALUNOS_NA_TURMA);
        }
        return super.excluir(id);
    }
}
