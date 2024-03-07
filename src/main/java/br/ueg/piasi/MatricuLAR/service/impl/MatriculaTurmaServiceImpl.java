package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.MatriculaTurma;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkMatriculaTurma;
import br.ueg.piasi.MatricuLAR.repository.MatriculaTurmaRepository;
import br.ueg.piasi.MatricuLAR.service.MatriculaTurmaService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class MatriculaTurmaServiceImpl extends BaseCrudService<MatriculaTurma, PkMatriculaTurma, MatriculaTurmaRepository>
        implements MatriculaTurmaService {


    @Override
    protected void prepararParaIncluir(MatriculaTurma entidade) {

    }

    @Override
    protected void validarDados(MatriculaTurma entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(MatriculaTurma entidade) {

    }
}
