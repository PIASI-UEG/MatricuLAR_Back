package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.Turma;
import br.ueg.piasi.MatricuLAR.repository.TurmaRepository;
import br.ueg.piasi.MatricuLAR.service.TurmaService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
public class TurmaServiceImpl extends BaseCrudService<Turma, Long, TurmaRepository>
        implements TurmaService {

    @Override
    protected void prepararParaIncluir(Turma entidade) {

    }

    @Override
    protected void validarDados(Turma entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Turma entidade) {

    }

    @Override
    public Turma incluir(Turma turma) {
        if(Objects.isNull(turma.getTurmaMatriculas())){
            turma.setTurmaMatriculas(new HashSet<>());
        }
        return super.incluir(turma);
    }
}
