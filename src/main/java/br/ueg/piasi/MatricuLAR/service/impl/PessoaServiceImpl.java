package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.repository.PessoaRepository;
import br.ueg.piasi.MatricuLAR.service.PessoaService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PessoaServiceImpl extends BaseCrudService<Pessoa, String, PessoaRepository>
        implements PessoaService {


    @Autowired
    PessoaRepository repository;


    @Override
    protected void prepararParaIncluir(Pessoa entidade) {

    }

    @Override
    protected void validarDados(Pessoa entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Pessoa entidade) {

    }

    public Pessoa findByCPF(String cpf){
        return repository.findByCpf(cpf).orElse(null);
    }
}
