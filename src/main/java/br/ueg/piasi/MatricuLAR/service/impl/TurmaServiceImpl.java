package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.Turma;
import br.ueg.piasi.MatricuLAR.repository.TurmaRepository;
import br.ueg.piasi.MatricuLAR.service.TurmaService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

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
}
