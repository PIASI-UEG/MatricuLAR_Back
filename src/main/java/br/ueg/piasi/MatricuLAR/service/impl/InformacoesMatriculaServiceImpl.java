package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.InformacoesMatricula;
import br.ueg.piasi.MatricuLAR.repository.InformacoesMatriculaRepository;
import br.ueg.piasi.MatricuLAR.service.InformacoesMatriculaService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class InformacoesMatriculaServiceImpl extends BaseCrudService<InformacoesMatricula, Long, InformacoesMatriculaRepository>
        implements InformacoesMatriculaService {


    @Override
    protected void prepararParaIncluir(InformacoesMatricula entidade) {

    }

    @Override
    protected void validarDados(InformacoesMatricula entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(InformacoesMatricula entidade) {

    }
}
