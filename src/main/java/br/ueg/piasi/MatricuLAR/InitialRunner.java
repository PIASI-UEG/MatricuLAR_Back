package br.ueg.piasi.MatricuLAR;

import br.ueg.piasi.MatricuLAR.enums.Cargo;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.Turno;
import br.ueg.piasi.MatricuLAR.enums.Vinculo;
import br.ueg.piasi.MatricuLAR.model.*;
import br.ueg.piasi.MatricuLAR.repository.ControlePeriodoMatriculaRepository;
import br.ueg.piasi.MatricuLAR.repository.MatriculaRepository;
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
import java.util.HashSet;
import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class InitialRunner implements ApplicationRunner {

    private final UsuarioServiceImpl usuarioService;

    private final EnderecoServiceImpl enderecoService;

    private final NecessidadeEspecialServiceImpl necessidadeEspecialService;

    private final TurmaServiceImpl turmaService;

    private final MatriculaServiceImpl matriculaService;

    private final ControlePeriodoMatriculaRepository controlePeriodoMatriculaRepository;
    private final MatriculaRepository matriculaRepository;
    private final InformacoesMatriculaServiceImpl informacoesMatriculaServiceImpl;
    private final DocumentoMatriculaServiceImpl documentoMatriculaServiceImpl;

    public InitialRunner(UsuarioServiceImpl usuarioService,
                         PessoaServiceImpl pessoaService,
                         EnderecoServiceImpl enderecoService,
                         NecessidadeEspecialServiceImpl necessidadeEspecialService,
                         TurmaServiceImpl turmaService,
                         TutorServiceImpl tutorService,
                         MatriculaServiceImpl matriculaService,
                         ControlePeriodoMatriculaRepository controlePeriodoMatriculaService, MatriculaRepository matriculaRepository, InformacoesMatriculaServiceImpl informacoesMatriculaServiceImpl, DocumentoMatriculaServiceImpl documentoMatriculaServiceImpl) {
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
        this.necessidadeEspecialService = necessidadeEspecialService;
        this.turmaService = turmaService;
        this.matriculaService = matriculaService;
        this.controlePeriodoMatriculaRepository = controlePeriodoMatriculaService;
        this.matriculaRepository = matriculaRepository;
        this.informacoesMatriculaServiceImpl = informacoesMatriculaServiceImpl;
        this.documentoMatriculaServiceImpl = documentoMatriculaServiceImpl;
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
        turma = turmaService.incluir(turma);

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

        ControlePeriodoMatricula controle = ControlePeriodoMatricula.builder()
                .id(1L)
                .aceitandoCadastroMatricula(true)
                .dataInicio(null)
                .dataFim(null)
                .atualizarPeriodoAutomatico(false)
                .build();
        controlePeriodoMatriculaRepository.save(controle);

        Matricula matricula1 = matriculaRepository.findById(1L).get();
        matricula1.setStatus(StatusMatricula.ATIVO);

        InformacoesMatricula infoMatricula = InformacoesMatricula.builder()
                .matricula(matricula1)
                .frequentouOutraCreche(false)
                .observacao("Não possui observações")
                .possuiBeneficiosDoGoverno(false)
                .possuiEcaminhamentoCRAS(false)
                .tipoResidencia("proprio")
                .possuiVeiculoProprio(false)
                .rendaFamiliar(BigDecimal.valueOf(1200))
                .build();

        InformacoesMatricula informacoesMatricula = informacoesMatriculaServiceImpl.incluir(infoMatricula);
        DocumentoMatricula documentoMatricula = DocumentoMatricula.builder()
                .matricula(matricula1)
                .aceito(true)
                .caminhoDocumento("1_FC.jpeg")
                .idTipoDocumento("FC")
                .build();

        HashSet<DocumentoMatricula> documentos = new HashSet<>();

        DocumentoMatricula dcMAtricula = documentoMatriculaServiceImpl.incluir(documentoMatricula);

        documentos.add(dcMAtricula);

        matricula1.setInformacoesMatricula(informacoesMatricula);
        matricula.setDocumentoMatricula(documentos);

        matriculaRepository.save(matricula1);
        System.out.println("\n*** Fim da Inserção de dados para testes ***\n");
    }

}
