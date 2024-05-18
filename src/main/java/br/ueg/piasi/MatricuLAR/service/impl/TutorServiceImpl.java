package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Tutor;
import br.ueg.piasi.MatricuLAR.repository.TutorRepository;
import br.ueg.piasi.MatricuLAR.service.TutorService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TutorServiceImpl extends BaseCrudService<Tutor, String, TutorRepository>
        implements TutorService {

    private final PessoaServiceImpl pessoaServiceImpl;

    public TutorServiceImpl(PessoaServiceImpl pessoaServiceImpl) {
        this.pessoaServiceImpl = pessoaServiceImpl;
    }

    @Override
    protected void prepararParaIncluir(Tutor tutor) {
        tutor.setPessoa(pessoaServiceImpl.incluir(
                        Pessoa.builder()
                                .nome(tutor.getPessoa().getNome())
                                .cpf(tutor.getPessoa().getCpf())
                                .telefone(tutor.getPessoa().getTelefone())
                                .build()
                )
        );
    }

    @Override
    protected void validarDados(Tutor entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Tutor entidade) {

    }
}
