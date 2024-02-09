package br.ueg.piasi.MatricuLAR.service.impl;

import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkResponsavel;
import br.ueg.piasi.MatricuLAR.repository.ResponsavelRepository;
import br.ueg.piasi.MatricuLAR.service.ResponsavelService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class ResponsavelServiceImpl extends BaseCrudService<Responsavel, PkResponsavel, ResponsavelRepository>
        implements ResponsavelService {
    @Override
    protected void prepararParaIncluir(Responsavel entidade) {

    }

    @Override
    protected void validarDados(Responsavel entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Responsavel entidade) {

    }
}
