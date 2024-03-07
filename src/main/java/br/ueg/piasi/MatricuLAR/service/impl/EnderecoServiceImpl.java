package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.Endereco;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.repository.EnderecoRepository;
import br.ueg.piasi.MatricuLAR.repository.PessoaRepository;
import br.ueg.piasi.MatricuLAR.service.EnderecoService;
import br.ueg.piasi.MatricuLAR.service.PessoaService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl extends BaseCrudService<Endereco, Long, EnderecoRepository>
        implements EnderecoService {

    @Override
    protected void prepararParaIncluir(Endereco entidade) {

    }

    @Override
    protected void validarDados(Endereco entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Endereco entidade) {

    }
}
