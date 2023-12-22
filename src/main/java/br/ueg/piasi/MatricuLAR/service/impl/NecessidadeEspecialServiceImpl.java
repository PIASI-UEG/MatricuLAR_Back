package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.NecessidadeEspecial;
import br.ueg.piasi.MatricuLAR.repository.NecessidadeEspecialRepository;
import br.ueg.piasi.MatricuLAR.service.NecessidadeEspecialService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class NecessidadeEspecialServiceImpl extends BaseCrudService<NecessidadeEspecial, Long, NecessidadeEspecialRepository>
        implements NecessidadeEspecialService {


    @Override
    protected void prepararParaIncluir(NecessidadeEspecial entidade) {

    }

    @Override
    protected void validarDados(NecessidadeEspecial entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(NecessidadeEspecial entidade) {

    }
}
