package br.ueg.piasi.MatricuLAR;

import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import br.ueg.piasi.MatricuLAR.repository.PessoaRepository;
import br.ueg.piasi.MatricuLAR.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class InitialRunner implements ApplicationRunner {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private PessoaRepository pessoaRepository;

    public void init() throws IOException {
        Pessoa pessoa = Pessoa.builder()
                .cpf("000")
                .nome("Teste")
                .fone("(62)99999-9999")
                .build();
        pessoa = pessoaRepository.save(pessoa);

        Usuario usuario = Usuario.builder()
                .pessoa(pessoa)
                .senha("admin")
                .cargo("Tester")
                .telefone("(00) 0000-0000")
                .build();

        usuarioService.incluir(usuario);

        System.out.println("Fim da inicialização");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
