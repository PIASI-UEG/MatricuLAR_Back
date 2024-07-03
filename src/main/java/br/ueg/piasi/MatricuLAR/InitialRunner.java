package br.ueg.piasi.MatricuLAR;

import br.ueg.piasi.MatricuLAR.enums.Cargo;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.Turno;
import br.ueg.piasi.MatricuLAR.enums.Vinculo;
import br.ueg.piasi.MatricuLAR.model.*;
import br.ueg.piasi.MatricuLAR.repository.ControlePeriodoMatriculaRepository;
import br.ueg.piasi.MatricuLAR.repository.DocumentoMatriculaRepository;
import br.ueg.piasi.MatricuLAR.repository.MatriculaRepository;
import br.ueg.piasi.MatricuLAR.repository.UsuarioRepository;
import br.ueg.piasi.MatricuLAR.service.impl.*;
import net.sf.jasperreports.engine.JRException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class InitialRunner implements ApplicationRunner {

    private final UsuarioServiceImpl usuarioService;
    private final EnderecoServiceImpl enderecoService;
    private final TurmaServiceImpl turmaService;
    private final MatriculaServiceImpl matriculaService;
    private final ControlePeriodoMatriculaRepository controlePeriodoMatriculaRepository;
    private final MatriculaRepository matriculaRepository;
    private final DocumentoMatriculaRepository documentoMatriculaRepository;
    private final UsuarioRepository usuarioRepository;

    public InitialRunner(UsuarioServiceImpl usuarioService,
                         EnderecoServiceImpl enderecoService,
                         NecessidadeEspecialServiceImpl necessidadeEspecialService,
                         TurmaServiceImpl turmaService,
                         MatriculaServiceImpl matriculaService,
                         ControlePeriodoMatriculaRepository controlePeriodoMatriculaService, MatriculaRepository matriculaRepository, InformacoesMatriculaServiceImpl informacoesMatriculaServiceImpl, DocumentoMatriculaServiceImpl documentoMatriculaServiceImpl, DocumentoMatriculaRepository documentoMatriculaRepository, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
        this.turmaService = turmaService;
        this.matriculaService = matriculaService;
        this.controlePeriodoMatriculaRepository = controlePeriodoMatriculaService;
        this.matriculaRepository = matriculaRepository;
        this.documentoMatriculaRepository = documentoMatriculaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            insereDadosParaTestes();
            if (Objects.isNull(usuarioRepository.findUsuarioByPessoaCpf("12345678900").orElse(null))) {
                insereUsuarioAdmin();
            }
            if (Objects.isNull(controlePeriodoMatriculaRepository.findById(Long.valueOf(1)).orElse(null))) {
                insereControles();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insereUsuarioAdmin() {

        Pessoa pessoaUsuario = Pessoa.builder()
                .cpf("12345678900")
                .nome("Administrador")
                .telefone("6230996423")
                .build();

        Usuario usuario = Usuario.builder()
                .pessoa(pessoaUsuario)
                .senha("admin")
                .cargo(Cargo.ADMIN)
                .email("admin@associacaosagradafamilia.com.br")
                .build();
        usuarioService.incluir(usuario);
    }

    public void insereControles() {
        ControlePeriodoMatricula controle = ControlePeriodoMatricula.builder()
                .id(1L)
                .aceitandoCadastroMatricula(true)
                .dataInicio(null)
                .dataFim(null)
                .atualizarPeriodoAutomatico(false)
                .build();
        controlePeriodoMatriculaRepository.save(controle);
    }

    public void insereDadosParaTestes() throws IOException, JRException {

        //Insere pessoa de teste para tutor
        Pessoa pessoaTutor = Pessoa.builder()
                .cpf("12345678911")
                .nome("Teste Tutor")
                .telefone("62999999999")
                .build();


        Pessoa pessoaTutor2 = Pessoa.builder()
                .cpf("12345678912")
                .nome("Teste Tutor2")
                .telefone("62999999999")
                .build();


        //Insere tutor de teste
        Tutor tutor = Tutor.builder()
                .pessoa(pessoaTutor)
                .cpf(pessoaTutor.getCpf())
                .empresaCnpj("11222333444455")
                .empresaNome("Empresa Teste")
                .telefoneFixoEmpresarial("6233339999")
                .profissao("Profissão de teste")
                .vinculo(Vinculo.AVO)
                .casado(false)
                .moraComConjuge(false)
                .dataNascimento(LocalDate.of(1980, 1, 1))
                .telefoneReserva("62999219901")
                .build();

        Tutor tutor2 = Tutor.builder()
                .pessoa(pessoaTutor2)
                .cpf(pessoaTutor2.getCpf())
                .empresaCnpj("11222333444455")
                .empresaNome("Empresa Teste")
                .telefoneFixoEmpresarial("6233339999")
                .profissao("Profissão de teste")
                .casado(false)
                .moraComConjuge(false)
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .vinculo(Vinculo.AVO)
                .telefoneReserva("62999219901")
                .build();

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
        turma = turmaService.incluir(turma);

        Pessoa pessoaMatricula = Pessoa.builder()
                .cpf("12345678922")
                .nome("Teste Matricula")
                .build();

        InformacoesMatricula infoMatricula = InformacoesMatricula.builder()
                .frequentouOutraCreche(false)
                .observacao("Não possui observações")
                .possuiBeneficiosDoGoverno(false)
                .possuiEcaminhamentoCRAS(false)
                .tipoResidencia("proprio")
                .possuiVeiculoProprio(false)
                .rendaFamiliar(BigDecimal.valueOf(1200))
                .build();

        Matricula matricula = Matricula.builder()
                .pessoa(pessoaMatricula)
                .status(StatusMatricula.INATIVO)
                .tutorList(List.of(tutor, tutor2))
                .nascimento(LocalDate.now())
                .endereco(endereco)
                .necessidades(Collections.singleton(necessidadeEspecial))
                .informacoesMatricula(infoMatricula)
                .build();

        matriculaService.incluir(matricula);

        DocumentoMatricula documentoMatricula = DocumentoMatricula.builder()
                .matricula(Matricula.builder().id(1L).build())
                .aceito(true)
                .caminhoDocumento("1_FC.jpeg")
                .idTipoDocumento("FC")
                .build();

        HashSet<DocumentoMatricula> documentos = new HashSet<>();

        DocumentoMatricula dcMAtricula = documentoMatriculaRepository.save(documentoMatricula);

        documentos.add(dcMAtricula);

        Matricula matricula1 = matriculaRepository.findById(1L).get();
        matricula1.setStatus(StatusMatricula.ATIVO);
        matricula1.setDocumentoMatricula(documentos);

        matriculaRepository.save(matricula1);
        System.out.println("\n*** Fim da Inserção de dados para testes ***\n");
    }

}
