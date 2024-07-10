package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Tutor;
import br.ueg.piasi.MatricuLAR.repository.PessoaRepository;
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
    private final PessoaRepository pessoaRepository;
    private final TutorRepository tutorRepository;

    public TutorServiceImpl(PessoaServiceImpl pessoaServiceImpl, PessoaRepository pessoaRepository, TutorRepository tutorRepository) {
        this.pessoaServiceImpl = pessoaServiceImpl;
        this.pessoaRepository = pessoaRepository;
        this.tutorRepository = tutorRepository;
    }

    @Override
    protected void prepararParaIncluir(Tutor tutor) {
        if(pessoaRepository.findById(tutor.getCpf()).isPresent()){
            tutor.setPessoa(pessoaServiceImpl.obterPeloId(tutor.getCpf()));
        } else{
            tutor.setPessoa(pessoaServiceImpl.incluir(
                            Pessoa.builder()
                                    .nome(tutor.getPessoa().getNome())
                                    .cpf(tutor.getPessoa().getCpf())
                                    .telefone(tutor.getPessoa().getTelefone())
                                    .build()
                    )
            );
        }
    }

    @Override
    protected void validarDados(Tutor entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Tutor entidade) {

    }

    protected boolean tutorExists(Tutor tutor) {
        return tutorRepository.findById(tutor.getCpf()).isPresent();
    }
}
