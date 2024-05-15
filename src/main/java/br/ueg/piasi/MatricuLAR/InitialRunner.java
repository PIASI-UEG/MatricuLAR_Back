package br.ueg.piasi.MatricuLAR;

import br.ueg.piasi.MatricuLAR.enums.Cargo;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.Turno;
import br.ueg.piasi.MatricuLAR.enums.Vinculo;
import br.ueg.piasi.MatricuLAR.model.*;
import br.ueg.piasi.MatricuLAR.service.impl.*;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class InitialRunner implements ApplicationRunner {

    private final UsuarioServiceImpl usuarioService;

    private final PessoaServiceImpl pessoaService;

    private final EnderecoServiceImpl enderecoService;

    private final NecessidadeEspecialServiceImpl necessidadeEspecialService;

    private final TurmaServiceImpl turmaService;

    private final TutorServiceImpl tutorService;

    private final MatriculaServiceImpl matriculaService;

    public InitialRunner(UsuarioServiceImpl usuarioService,
                         PessoaServiceImpl pessoaService,
                         EnderecoServiceImpl enderecoService,
                         NecessidadeEspecialServiceImpl necessidadeEspecialService,
                         TurmaServiceImpl turmaService,
                         TutorServiceImpl tutorService,
                         MatriculaServiceImpl matriculaService) {
        this.usuarioService = usuarioService;
        this.pessoaService = pessoaService;
        this.enderecoService = enderecoService;
        this.necessidadeEspecialService = necessidadeEspecialService;
        this.turmaService = turmaService;
        this.tutorService = tutorService;
        this.matriculaService = matriculaService;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            insereDadosParaTestes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insereDadosParaTestes() throws IOException, JRException {

        //Insere pessoa de teste para usuario
        Pessoa pessoaUsuario = Pessoa.builder()
                .cpf("12345678900")
                .nome("Teste Usuario")
                .telefone("62999999999")
                .build();

        //Insere usuario de teste
        Usuario usuario = Usuario.builder()
                .pessoa(pessoaUsuario)
                .senha("admin")
                .cargo(Cargo.ADMIN)
                .email("admin@gmail.com")
                .build();
        usuarioService.incluir(usuario);

        //Insere pessoa de teste para tutor
        Pessoa pessoaTutor = Pessoa.builder()
                .cpf("12345678911")
                .nome("Teste Tutor")
                .telefone("62999999999")
                .build();
        pessoaTutor = pessoaService.incluir(pessoaTutor);

        Pessoa pessoaTutor2 = Pessoa.builder()
                .cpf("12345678912")
                .nome("Teste Tutor2")
                .telefone("62999999999")
                .build();
        pessoaTutor2 = pessoaService.incluir(pessoaTutor2);

        //Insere tutor de teste
        Tutor tutor = Tutor.builder()
                .pessoa(pessoaTutor)
                .cpf(pessoaTutor.getCpf())
                .telefoneWhatsapp(true)
                .empresaCnpj("11222333444455")
                .empresaNome("Empresa Teste")
                .telefoneFixoEmpresarial("6233339999")
                .profissao("Profissão de teste")
                .vinculo(Vinculo.AVO)
                .casado(false)
                .moraComConjuge(false)
                .dataNascimento(LocalDate.of(1980, 1, 1))
                .build();
        tutor = tutorService.incluir(tutor);

        Tutor tutor2 = Tutor.builder()
                .pessoa(pessoaTutor2)
                .cpf(pessoaTutor2.getCpf())
                .telefoneWhatsapp(true)
                .empresaCnpj("11222333444455")
                .empresaNome("Empresa Teste")
                .telefoneFixoEmpresarial("6233339999")
                .profissao("Profissão de teste")
                .casado(false)
                .moraComConjuge(false)
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .vinculo(Vinculo.PAI)
                .build();
        tutor2 = tutorService.incluir(tutor2);

        //Insere endereço de teste
        Endereco endereco = Endereco.builder()
                .cep("12345678")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .logradouro("Av Teste Qd 00 Lt 99")
                .complemento("Testes testes")
                .build();
        endereco = enderecoService.incluir(endereco);

        //Insere necessidade especial de teste
        NecessidadeEspecial necessidadeEspecial = NecessidadeEspecial.builder()
                .titulo("Necessidade Teste")
                .build();
        necessidadeEspecialService.incluir(necessidadeEspecial);

        //Insere turma de teste
        Turma turma = Turma.builder()
                .titulo("Turma para teste")
                .nomeProfessor("Professor de Teste")
                .turno(Turno.MATUTINO)
                .ano(LocalDate.now().getYear())
                .horaInicio("0700")
                .horaFim("1130")
                .telefoneProfessor("62991922192")
                .build();
        turma= turmaService.incluir(turma);

        Pessoa pessoaMatricula = Pessoa.builder()
                .cpf("12345678922")
                .nome("Teste Matricula")
                .build();

        Matricula matricula = Matricula.builder()
                .pessoa(pessoaMatricula)
                .status(StatusMatricula.INATIVO)
                .tutorList(List.of(tutor, tutor2))
                .nascimento(LocalDate.now())
                .endereco(endereco)
                .necessidades(new HashSet<>())

                .build();

        matriculaService.incluir(matricula);

        System.out.println("\n*** Fim da Inserção de dados para testes ***\n");
    }

}
