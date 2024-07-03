package br.ueg.piasi.MatricuLAR.service.impl;

import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkResponsavel;
import br.ueg.piasi.MatricuLAR.repository.ResponsavelRepository;
import br.ueg.piasi.MatricuLAR.service.ResponsavelService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ResponsavelServiceImpl extends BaseCrudService<Responsavel, PkResponsavel, ResponsavelRepository>
        implements ResponsavelService {

    private final PessoaServiceImpl pessoaServiceImpl;

    public ResponsavelServiceImpl(PessoaServiceImpl pessoaServiceImpl) {
        this.pessoaServiceImpl = pessoaServiceImpl;
    }

    @Override
    protected void prepararParaIncluir(Responsavel responsavel) {
        Pessoa pessoa = pessoaServiceImpl.findByCPF(responsavel.getPessoa().getCpf());
        if (Objects.nonNull(pessoa)) {
            responsavel.setPessoa(pessoa);
        }else{
            pessoa = pessoaServiceImpl.incluir(responsavel.getPessoa());
            responsavel.setPessoa(pessoa);
        }
    }

    @Override
    protected void validarDados(Responsavel entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Responsavel entidade) {

    }

    public Responsavel incluirResponsavel(Responsavel responsavel) {
        prepararParaIncluir(responsavel);
        return repository.save(responsavel);
    }
}
