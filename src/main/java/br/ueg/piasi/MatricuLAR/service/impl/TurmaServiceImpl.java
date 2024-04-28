package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode;
import br.ueg.piasi.MatricuLAR.model.Turma;
import br.ueg.piasi.MatricuLAR.repository.TurmaRepository;
import br.ueg.piasi.MatricuLAR.service.TurmaService;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Turma adicionaAlunosTurma(Long idTurma, List<Long> idAlunos) {
        Turma turma = repository.findById(idTurma).orElseThrow(() ->
                new BusinessException(SistemaMessageCode.ERRO_TURMA_NAO_ENCONTRADA, idTurma));
        for (Long idAluno : idAlunos){
            matriculaService.addTurmaPorNroMatricula(idAluno, turma);
        }
        return repository.findById(idTurma).get();
    }
}
