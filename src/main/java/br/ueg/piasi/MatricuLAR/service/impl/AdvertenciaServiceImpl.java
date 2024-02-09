package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.Advertencia;
import br.ueg.piasi.MatricuLAR.model.Endereco;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkAdvertencia;
import br.ueg.piasi.MatricuLAR.repository.AdvertenciaRepository;
import br.ueg.piasi.MatricuLAR.repository.EnderecoRepository;
import br.ueg.piasi.MatricuLAR.service.AdvertenciaService;
import br.ueg.piasi.MatricuLAR.service.EnderecoService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class AdvertenciaServiceImpl extends BaseCrudService<Advertencia, PkAdvertencia, AdvertenciaRepository>
        implements AdvertenciaService {

    @Override
    protected void prepararParaIncluir(Advertencia entidade) {

    }

    @Override
    protected void validarDados(Advertencia entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Advertencia entidade) {

    }
}
