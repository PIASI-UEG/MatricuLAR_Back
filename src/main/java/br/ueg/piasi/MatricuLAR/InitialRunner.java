package br.ueg.piasi.MatricuLAR;

import br.ueg.piasi.MatricuLAR.model.Endereco;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import br.ueg.piasi.MatricuLAR.repository.EnderecoRepository;
import br.ueg.piasi.MatricuLAR.repository.PessoaRepository;
import br.ueg.piasi.MatricuLAR.service.impl.EnderecoServiceImpl;
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

    @Autowired
    private EnderecoServiceImpl enderecoService;

    public void init() throws IOException {

        //Inicia pessoa de teste
        Pessoa pessoa = Pessoa.builder()
                .cpf("12345678900")
                .nome("Teste")
                .telefone("62999999999")
                .build();
        pessoa = pessoaRepository.save(pessoa);

        //Inicia usuario de teste
        Usuario usuario = Usuario.builder()
                .pessoa(pessoa)
                .senha("admin")
                .cargo("Tester")
                .email("admin@gmail.com")
                .build();
        usuarioService.incluir(usuario);

        //Inicia endereço de teste
        Endereco endereco = Endereco.builder()
                .cep("12345678")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .logradouro("Av Teste Qd 00 Lt 99")
                .complemento("Testes testes")
                .build();
        enderecoService.incluir(endereco);

        System.out.println("\n*** Fim da inicialização ***\n");
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
