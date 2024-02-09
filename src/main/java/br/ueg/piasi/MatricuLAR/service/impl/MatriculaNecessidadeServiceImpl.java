package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.MatriculaNecessidade;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkMatriculaNecessidade;
import br.ueg.piasi.MatricuLAR.repository.MatriculaNecessidadeRepository;
import br.ueg.piasi.MatricuLAR.service.MatriculaNecessidadeService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class MatriculaNecessidadeServiceImpl extends BaseCrudService<MatriculaNecessidade, PkMatriculaNecessidade, MatriculaNecessidadeRepository>
        implements MatriculaNecessidadeService {


    @Override
    protected void prepararParaIncluir(MatriculaNecessidade entidade) {

    }

    @Override
    protected void validarDados(MatriculaNecessidade entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(MatriculaNecessidade entidade) {

    }
}
