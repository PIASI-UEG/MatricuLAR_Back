package br.ueg.piasi.MatricuLAR;

import br.ueg.piasi.MatricuLAR.enums.Cargo;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.Turno;
import br.ueg.piasi.MatricuLAR.enums.Vinculo;
import br.ueg.piasi.MatricuLAR.model.*;
import br.ueg.piasi.MatricuLAR.repository.ControlePeriodoMatriculaRepository;
import br.ueg.piasi.MatricuLAR.repository.DocumentoMatriculaRepository;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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

    public InitialRunner(UsuarioServiceImpl usuarioService,
                         EnderecoServiceImpl enderecoService,
                         NecessidadeEspecialServiceImpl necessidadeEspecialService,
                         TurmaServiceImpl turmaService,
                         MatriculaServiceImpl matriculaService,
                         ControlePeriodoMatriculaRepository controlePeriodoMatriculaService, MatriculaRepository matriculaRepository, InformacoesMatriculaServiceImpl informacoesMatriculaServiceImpl, DocumentoMatriculaServiceImpl documentoMatriculaServiceImpl, DocumentoMatriculaRepository documentoMatriculaRepository) {
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
        this.turmaService = turmaService;
        this.matriculaService = matriculaService;
        this.controlePeriodoMatriculaRepository = controlePeriodoMatriculaService;
        this.matriculaRepository = matriculaRepository;
        this.documentoMatriculaRepository = documentoMatriculaRepository;
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

        // Exemplo 1
        Pessoa pessoaUsuario1 = Pessoa.builder()
                .cpf("30025328000")
                .nome("Maria Silva")
                .telefone("62988888888")
                .build();

        Usuario usuario1 = Usuario.builder()
                .pessoa(pessoaUsuario1)
                .senha("senha123")
                .cargo(Cargo.COORDENADORA)
                .email("maria.silva@example.com")
                .build();
        usuarioService.incluir(usuario1);


// Exemplo 2
        Pessoa pessoaUsuario2 = Pessoa.builder()
                .cpf("72671992084")
                .nome("José Santos")
                .telefone("62988888889")
                .build();

        Usuario usuario2 = Usuario.builder()
                .pessoa(pessoaUsuario2)
                .senha("senha456")
                .cargo(Cargo.SECRETARIA)
                .email("jose.santos@example.com")
                .build();
        usuarioService.incluir(usuario2);


// Exemplo 3
        Pessoa pessoaUsuario3 = Pessoa.builder()
                .cpf("35825340068")
                .nome("Ana Oliveira")
                .telefone("62988888890")
                .build();

        Usuario usuario3 = Usuario.builder()
                .pessoa(pessoaUsuario3)
                .senha("senha789")
                .cargo(Cargo.SECRETARIA)
                .email("ana.oliveira@example.com")
                .build();
        usuarioService.incluir(usuario3);


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

        ControlePeriodoMatricula controle = ControlePeriodoMatricula.builder()
                .id(1L)
                .aceitandoCadastroMatricula(true)
                .dataInicio(null)
                .dataFim(null)
                .atualizarPeriodoAutomatico(false)
                .build();
        controlePeriodoMatriculaRepository.save(controle);

        DocumentoMatricula documentoMatricula = DocumentoMatricula.builder()
                .matricula(Matricula.builder().id(1L).build())
                .aceito(true)
                .caminhoDocumento("1_FC.jpeg")
                .idTipoDocumento("FC")
                .build();

        HashSet<DocumentoMatricula> documentos = new HashSet<>();

        DocumentoMatricula dcMAtricula = documentoMatriculaRepository.save(documentoMatricula);

        documentos.add(dcMAtricula);

        Matricula matriculaZ = matriculaRepository.findById(1L).get();
        matriculaZ.setStatus(StatusMatricula.ATIVO);
        matriculaZ.setDocumentoMatricula(documentos);

        matriculaRepository.save(matriculaZ);

        // Exemplo 1
        Pessoa pessoaTutor1 = Pessoa.builder()
                .cpf("98765432100")
                .nome("João Silva")
                .telefone("62988888888")
                .build();

        Pessoa pessoaTutor2_1 = Pessoa.builder()
                .cpf("98765432101")
                .nome("Maria Santos")
                .telefone("62988888889")
                .build();

        Tutor tutor1 = Tutor.builder()
                .pessoa(pessoaTutor1)
                .cpf(pessoaTutor1.getCpf())
                .empresaCnpj("22333444555566")
                .empresaNome("Empresa Exemplo 1")
                .telefoneFixoEmpresarial("6233338888")
                .profissao("Engenheiro")
                .vinculo(Vinculo.PAI)
                .casado(true)
                .moraComConjuge(true)
                .dataNascimento(LocalDate.of(1975, 5, 15))
                .telefoneReserva("62999887766")
                .build();

        Tutor tutor2_1 = Tutor.builder()
                .pessoa(pessoaTutor2_1)
                .cpf(pessoaTutor2_1.getCpf())
                .empresaCnpj("22333444555566")
                .empresaNome("Empresa Exemplo 1")
                .telefoneFixoEmpresarial("6233338888")
                .profissao("Professora")
                .casado(true)
                .moraComConjuge(true)
                .dataNascimento(LocalDate.of(1978, 6, 20))
                .vinculo(Vinculo.MAE)
                .telefoneReserva("62999887767")
                .build();

        Endereco endereco1 = Endereco.builder()
                .cep("98765432")
                .bairro("Bairro Exemplo 1")
                .cidade("Cidade Exemplo 1")
                .logradouro("Rua Exemplo 1, 123")
                .complemento("Apto 101")
                .build();
        endereco1 = enderecoService.incluir(endereco1);

        NecessidadeEspecial necessidadeEspecial1 = NecessidadeEspecial.builder()
                .titulo("Necessidade Especial 1")
                .build();

        Pessoa pessoaMatricula1 = Pessoa.builder()
                .cpf("98765432102")
                .nome("Carlos Pereira")
                .build();

        InformacoesMatricula infoMatricula1 = InformacoesMatricula.builder()
                .frequentouOutraCreche(true)
                .observacao("Observação Exemplo 1")
                .possuiBeneficiosDoGoverno(true)
                .possuiEcaminhamentoCRAS(true)
                .tipoResidencia("alugado")
                .possuiVeiculoProprio(true)
                .rendaFamiliar(BigDecimal.valueOf(3000))
                .build();

        Matricula matricula1 = Matricula.builder()
                .pessoa(pessoaMatricula1)
                .status(StatusMatricula.INATIVO)
                .tutorList(List.of(tutor1, tutor2_1))
                .nascimento(LocalDate.of(2023, 1, 1))
                .endereco(endereco1)
                .necessidades(Collections.singleton(necessidadeEspecial1))
                .informacoesMatricula(infoMatricula1)
                .build();

        matriculaService.incluir(matricula1);

        ControlePeriodoMatricula controle1 = ControlePeriodoMatricula.builder()
                .id(2L)
                .aceitandoCadastroMatricula(true)
                .dataInicio(null)
                .dataFim(null)
                .atualizarPeriodoAutomatico(false)
                .build();
        controlePeriodoMatriculaRepository.save(controle1);

        DocumentoMatricula documentoMatricula1 = DocumentoMatricula.builder()
                .matricula(Matricula.builder().id(2L).build())
                .aceito(true)
                .caminhoDocumento("2_FC.jpeg")
                .idTipoDocumento("FC")
                .build();

        HashSet<DocumentoMatricula> documentos1 = new HashSet<>();

        DocumentoMatricula dcMatricula1 = documentoMatriculaRepository.save(documentoMatricula1);

        documentos1.add(dcMatricula1);

        Matricula matricula1_1 = matriculaRepository.findById(2L).get();
        matricula1_1.setStatus(StatusMatricula.INATIVO);
        matricula1_1.setDocumentoMatricula(documentos1);

        matriculaRepository.save(matricula1_1);


// Exemplo 2
        Pessoa pessoaTuto2 = Pessoa.builder()
                .cpf("12345678901")
                .nome("Ana Oliveira")
                .telefone("62977777777")
                .build();

        Pessoa pessoaTutor2_2 = Pessoa.builder()
                .cpf("12345678902")
                .nome("Pedro Lima")
                .telefone("62977777778")
                .build();

        Tutor tuto2 = Tutor.builder()
                .pessoa(pessoaTuto2)
                .cpf(pessoaTuto2.getCpf())
                .empresaCnpj("33444555666677")
                .empresaNome("Empresa Exemplo 2")
                .telefoneFixoEmpresarial("6233337777")
                .profissao("Advogado")
                .vinculo(Vinculo.TIO)
                .casado(false)
                .moraComConjuge(false)
                .dataNascimento(LocalDate.of(1985, 10, 5))
                .telefoneReserva("62999776655")
                .build();

        Tutor tutor2_2 = Tutor.builder()
                .pessoa(pessoaTutor2_2)
                .cpf(pessoaTutor2_2.getCpf())
                .empresaCnpj("33444555666677")
                .empresaNome("Empresa Exemplo 2")
                .telefoneFixoEmpresarial("6233337777")
                .profissao("Médica")
                .casado(false)
                .moraComConjuge(false)
                .dataNascimento(LocalDate.of(1987, 11, 12))
                .vinculo(Vinculo.TIA)
                .telefoneReserva("62999776656")
                .build();

        Endereco endereco2 = Endereco.builder()
                .cep("87654321")
                .bairro("Bairro Exemplo 2")
                .cidade("Cidade Exemplo 2")
                .logradouro("Av Exemplo 2, 456")
                .complemento("Casa")
                .build();
        endereco2 = enderecoService.incluir(endereco2);

        NecessidadeEspecial necessidadeEspecial2 = NecessidadeEspecial.builder()
                .titulo("Necessidade Especial 2")
                .build();

        Pessoa pessoaMatricula2 = Pessoa.builder()
                .cpf("12345678903")
                .nome("Mariana Costa")
                .build();

        InformacoesMatricula infoMatricula2 = InformacoesMatricula.builder()
                .frequentouOutraCreche(false)
                .observacao("Observação Exemplo 2")
                .possuiBeneficiosDoGoverno(false)
                .possuiEcaminhamentoCRAS(false)
                .tipoResidencia("proprio")
                .possuiVeiculoProprio(false)
                .rendaFamiliar(BigDecimal.valueOf(2500))
                .build();

        Matricula matricula2 = Matricula.builder()
                .pessoa(pessoaMatricula2)
                .status(StatusMatricula.INATIVO)
                .tutorList(List.of(tuto2, tutor2_2))
                .nascimento(LocalDate.of(2023, 1, 1))
                .endereco(endereco2)
                .necessidades(Collections.singleton(necessidadeEspecial2))
                .informacoesMatricula(infoMatricula2)
                .build();

        matriculaService.incluir(matricula2);

        ControlePeriodoMatricula controle2 = ControlePeriodoMatricula.builder()
                .id(3L)
                .aceitandoCadastroMatricula(true)
                .dataInicio(null)
                .dataFim(null)
                .atualizarPeriodoAutomatico(false)
                .build();
        controlePeriodoMatriculaRepository.save(controle2);

        DocumentoMatricula documentoMatricula2 = DocumentoMatricula.builder()
                .matricula(Matricula.builder().id(3L).build())
                .aceito(true)
                .caminhoDocumento("3_FC.jpeg")
                .idTipoDocumento("FC")
                .build();

        HashSet<DocumentoMatricula> documentos2 = new HashSet<>();

        DocumentoMatricula dcMatricula2 = documentoMatriculaRepository.save(documentoMatricula2);

        documentos2.add(dcMatricula2);

        Matricula matricula2_1 = matriculaRepository.findById(3L).get();
        matricula2_1.setStatus(StatusMatricula.ATIVO);
        matricula2_1.setDocumentoMatricula(documentos2);

        matriculaRepository.save(matricula2_1);


// Exemplo 3
        Pessoa pessoaTutor3 = Pessoa.builder()
                .cpf("65432198700")
                .nome("Lucas Martins")
                .telefone("62966666666")
                .build();

        Pessoa pessoaTutor2_3 = Pessoa.builder()
                .cpf("65432198701")
                .nome("Fernanda Rocha")
                .telefone("62966666667")
                .build();

        Tutor tutor3 = Tutor.builder()
                .pessoa(pessoaTutor3)
                .cpf(pessoaTutor3.getCpf())
                .empresaCnpj("44555666777788")
                .empresaNome("Empresa Exemplo 3")
                .telefoneFixoEmpresarial("6233336666")
                .profissao("Arquiteto")
                .vinculo(Vinculo.PAI)
                .casado(true)
                .moraComConjuge(true)
                .dataNascimento(LocalDate.of(1983, 3, 15))
                .telefoneReserva("62999665544")
                .build();

        Tutor tutor2_3 = Tutor.builder()
                .pessoa(pessoaTutor2_3)
                .cpf(pessoaTutor2_3.getCpf())
                .empresaCnpj("44555666777788")
                .empresaNome("Empresa Exemplo 3")
                .telefoneFixoEmpresarial("6233336666")
                .profissao("Dentista")
                .casado(true)
                .moraComConjuge(true)
                .dataNascimento(LocalDate.of(1985, 4, 18))
                .vinculo(Vinculo.MAE)
                .telefoneReserva("62999665545")
                .build();

        Endereco endereco3 = Endereco.builder()
                .cep("65432198")
                .bairro("Bairro Exemplo 3")
                .cidade("Cidade Exemplo 3")
                .logradouro("Rua Exemplo 3, 789")
                .complemento("Casa")
                .build();
        endereco3 = enderecoService.incluir(endereco3);

        NecessidadeEspecial necessidadeEspecial3 = NecessidadeEspecial.builder()
                .titulo("Necessidade Especial 3")
                .build();

        Pessoa pessoaMatricula3 = Pessoa.builder()
                .cpf("65432198702")
                .nome("Joana Souza")
                .build();

        InformacoesMatricula infoMatricula3 = InformacoesMatricula.builder()
                .frequentouOutraCreche(false)
                .observacao("Observação Exemplo 3")
                .possuiBeneficiosDoGoverno(true)
                .possuiEcaminhamentoCRAS(false)
                .tipoResidencia("proprio")
                .possuiVeiculoProprio(false)
                .rendaFamiliar(BigDecimal.valueOf(1800))
                .build();

        Matricula matricula3 = Matricula.builder()
                .pessoa(pessoaMatricula3)
                .status(StatusMatricula.INATIVO)
                .tutorList(List.of(tutor3, tutor2_3))
                .nascimento(LocalDate.of(2023, 1, 1))
                .endereco(endereco3)
                .necessidades(Collections.singleton(necessidadeEspecial3))
                .informacoesMatricula(infoMatricula3)
                .build();

        matriculaService.incluir(matricula3);

        ControlePeriodoMatricula controle3 = ControlePeriodoMatricula.builder()
                .id(4L)
                .aceitandoCadastroMatricula(true)
                .dataInicio(null)
                .dataFim(null)
                .atualizarPeriodoAutomatico(false)
                .build();
        controlePeriodoMatriculaRepository.save(controle3);

        DocumentoMatricula documentoMatricula3 = DocumentoMatricula.builder()
                .matricula(Matricula.builder().id(4L).build())
                .aceito(true)
                .caminhoDocumento("4_FC.jpeg")
                .idTipoDocumento("FC")
                .build();

        HashSet<DocumentoMatricula> documentos3 = new HashSet<>();

        DocumentoMatricula dcMatricula3 = documentoMatriculaRepository.save(documentoMatricula3);

        documentos3.add(dcMatricula3);

        Matricula matricula3_1 = matriculaRepository.findById(4L).get();
        matricula3_1.setStatus(StatusMatricula.AGUARDANDO_ACEITE);
        matricula3_1.setDocumentoMatricula(documentos3);

        matriculaRepository.save(matricula3_1);


// Exemplo 4
        Pessoa pessoaTutor4 = Pessoa.builder()
                .cpf("32198765400")
                .nome("Roberto Nunes")
                .telefone("62955555555")
                .build();

        Pessoa pessoaTutor2_4 = Pessoa.builder()
                .cpf("32198765401")
                .nome("Juliana Costa")
                .telefone("62955555556")
                .build();

        Tutor tutor4 = Tutor.builder()
                .pessoa(pessoaTutor4)
                .cpf(pessoaTutor4.getCpf())
                .empresaCnpj("55666777888899")
                .empresaNome("Empresa Exemplo 4")
                .telefoneFixoEmpresarial("6233335555")
                .profissao("Analista de Sistemas")
                .vinculo(Vinculo.PAI)
                .casado(true)
                .moraComConjuge(true)
                .dataNascimento(LocalDate.of(1990, 2, 28))
                .telefoneReserva("62999554433")
                .build();

        Tutor tutor2_4 = Tutor.builder()
                .pessoa(pessoaTutor2_4)
                .cpf(pessoaTutor2_4.getCpf())
                .empresaCnpj("55666777888899")
                .empresaNome("Empresa Exemplo 4")
                .telefoneFixoEmpresarial("6233335555")
                .profissao("Designer")
                .casado(true)
                .moraComConjuge(true)
                .dataNascimento(LocalDate.of(1992, 3, 30))
                .vinculo(Vinculo.MAE)
                .telefoneReserva("62999554434")
                .build();

        Endereco endereco4 = Endereco.builder()
                .cep("32198765")
                .bairro("Bairro Exemplo 4")
                .cidade("Cidade Exemplo 4")
                .logradouro("Av Exemplo 4, 321")
                .complemento("Casa")
                .build();
        endereco4 = enderecoService.incluir(endereco4);

        NecessidadeEspecial necessidadeEspecial4 = NecessidadeEspecial.builder()
                .titulo("Necessidade Especial 4")
                .build();

        Pessoa pessoaMatricula4 = Pessoa.builder()
                .cpf("32198765402")
                .nome("Gustavo Almeida")
                .build();

        InformacoesMatricula infoMatricula4 = InformacoesMatricula.builder()
                .frequentouOutraCreche(true)
                .observacao("Observação Exemplo 4")
                .possuiBeneficiosDoGoverno(false)
                .possuiEcaminhamentoCRAS(true)
                .tipoResidencia("alugado")
                .possuiVeiculoProprio(true)
                .rendaFamiliar(BigDecimal.valueOf(3500))
                .build();

        Matricula matricula4 = Matricula.builder()
                .pessoa(pessoaMatricula4)
                .status(StatusMatricula.INATIVO)
                .tutorList(List.of(tutor4, tutor2_4))
                .nascimento(LocalDate.of(2023, 1, 1))
                .endereco(endereco4)
                .necessidades(Collections.singleton(necessidadeEspecial4))
                .informacoesMatricula(infoMatricula4)
                .build();

        matriculaService.incluir(matricula4);

        ControlePeriodoMatricula controle4 = ControlePeriodoMatricula.builder()
                .id(5L)
                .aceitandoCadastroMatricula(true)
                .dataInicio(null)
                .dataFim(null)
                .atualizarPeriodoAutomatico(false)
                .build();
        controlePeriodoMatriculaRepository.save(controle4);

        DocumentoMatricula documentoMatricula4 = DocumentoMatricula.builder()
                .matricula(Matricula.builder().id(5L).build())
                .aceito(true)
                .caminhoDocumento("5_FC.jpeg")
                .idTipoDocumento("FC")
                .build();

        HashSet<DocumentoMatricula> documentos4 = new HashSet<>();

        DocumentoMatricula dcMatricula4 = documentoMatriculaRepository.save(documentoMatricula4);

        documentos4.add(dcMatricula4);

        Matricula matricula4_1 = matriculaRepository.findById(5L).get();
        matricula4_1.setStatus(StatusMatricula.AGUARDANDO_ACEITE);
        matricula4_1.setDocumentoMatricula(documentos4);

        matriculaRepository.save(matricula4_1);


// Exemplo 5
        Pessoa pessoaTutor5 = Pessoa.builder()
                .cpf("78912345600")
                .nome("Bruno Ferreira")
                .telefone("62944444444")
                .build();

        Pessoa pessoaTutor2_5 = Pessoa.builder()
                .cpf("78912345601")
                .nome("Patrícia Gomes")
                .telefone("62944444445")
                .build();

        Tutor tutor5 = Tutor.builder()
                .pessoa(pessoaTutor5)
                .cpf(pessoaTutor5.getCpf())
                .empresaCnpj("66777888999900")
                .empresaNome("Empresa Exemplo 5")
                .telefoneFixoEmpresarial("6233334444")
                .profissao("Gerente")
                .vinculo(Vinculo.PAI)
                .casado(true)
                .moraComConjuge(true)
                .dataNascimento(LocalDate.of(1979, 9, 10))
                .telefoneReserva("62999443322")
                .build();

        Tutor tutor2_5 = Tutor.builder()
                .pessoa(pessoaTutor2_5)
                .cpf(pessoaTutor2_5.getCpf())
                .empresaCnpj("66777888999900")
                .empresaNome("Empresa Exemplo 5")
                .telefoneFixoEmpresarial("6233334444")
                .profissao("Enfermeira")
                .casado(true)
                .moraComConjuge(true)
                .dataNascimento(LocalDate.of(1980, 8, 15))
                .vinculo(Vinculo.MAE)
                .telefoneReserva("62999443323")
                .build();

        Endereco endereco5 = Endereco.builder()
                .cep("78912345")
                .bairro("Bairro Exemplo 5")
                .cidade("Cidade Exemplo 5")
                .logradouro("Rua Exemplo 5, 654")
                .complemento("Apto 202")
                .build();
        endereco5 = enderecoService.incluir(endereco5);

        NecessidadeEspecial necessidadeEspecial5 = NecessidadeEspecial.builder()
                .titulo("Necessidade Especial 5")
                .build();

        Pessoa pessoaMatricula5 = Pessoa.builder()
                .cpf("78912345602")
                .nome("Bianca Ribeiro")
                .build();

        InformacoesMatricula infoMatricula5 = InformacoesMatricula.builder()
                .frequentouOutraCreche(true)
                .observacao("Observação Exemplo 5")
                .possuiBeneficiosDoGoverno(true)
                .possuiEcaminhamentoCRAS(true)
                .tipoResidencia("proprio")
                .possuiVeiculoProprio(true)
                .rendaFamiliar(BigDecimal.valueOf(4000))
                .build();

        Matricula matricula5 = Matricula.builder()
                .pessoa(pessoaMatricula5)
                .status(StatusMatricula.INATIVO)
                .tutorList(List.of(tutor5, tutor2_5))
                .nascimento(LocalDate.of(2023, 1, 1))
                .endereco(endereco5)
                .necessidades(Collections.singleton(necessidadeEspecial5))
                .informacoesMatricula(infoMatricula5)
                .build();

        matriculaService.incluir(matricula5);

        ControlePeriodoMatricula controle5 = ControlePeriodoMatricula.builder()
                .id(6L)
                .aceitandoCadastroMatricula(true)
                .dataInicio(null)
                .dataFim(null)
                .atualizarPeriodoAutomatico(false)
                .build();
        controlePeriodoMatriculaRepository.save(controle5);

        DocumentoMatricula documentoMatricula5 = DocumentoMatricula.builder()
                .matricula(Matricula.builder().id(6L).build())
                .aceito(true)
                .caminhoDocumento("6_FC.jpeg")
                .idTipoDocumento("FC")
                .build();

        HashSet<DocumentoMatricula> documentos5 = new HashSet<>();

        DocumentoMatricula dcMatricula5 = documentoMatriculaRepository.save(documentoMatricula5);

        documentos5.add(dcMatricula5);

        Matricula matricula5_1 = matriculaRepository.findById(6L).get();
        matricula5_1.setStatus(StatusMatricula.AGUARDANDO_RENOVACAO);
        matricula5_1.setDocumentoMatricula(documentos5);

        matriculaRepository.save(matricula5_1);


        System.out.println("\n*** Fim da Inserção de dados para testes ***\n");
    }

}
