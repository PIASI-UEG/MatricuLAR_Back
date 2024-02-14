package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.MatriculaTurma;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.piasi.MatricuLAR.repository.MatriculaRepository;
import br.ueg.piasi.MatricuLAR.service.MatriculaService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MatriculaServiceImpl extends BaseCrudService<Matricula, String, MatriculaRepository>
        implements MatriculaService {

    @Autowired
    private MatriculaTurmaServiceImpl matriculaTurmaService;

    @Autowired
    private ResponsavelServiceImpl responsavelService;


    @Override
    protected void prepararParaIncluir(Matricula entidade) {

    }

    @Override
    protected void validarDados(Matricula entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Matricula entidade) {

    }

    @Override
    public Matricula alterar(Matricula entidade, String id) {
        if(Objects.isNull(entidade.getResponsaveis())){
            entidade.setResponsaveis(new HashSet<>());
            entidade.setNecessidades(new HashSet<>());
            entidade.setTurmas(new HashSet<>());
        }
        return super.alterar(entidade, id);
    }

    @Override
    public Matricula incluir(Matricula matricula) {

//        Set<MatriculaNecessidade> matriculaNecessidades = tratarMatriculaNecessidade(matricula);

        Set<MatriculaTurma> matriculaTurmas = tratarMatriculaTurmas(matricula);

        Set<Responsavel> matriculaResponsaveis = tratarMatriculaResponsaveis(matricula);

        Matricula matriculaSalva = super.incluir(matricula);

//        if (Objects.nonNull(matriculaNecessidades)){
//
//            Set<MatriculaNecessidade> matriculaNecessidadesSalvas = new HashSet<>();
//            salvaNecessidadesMatricula(matriculaSalva, matriculaNecessidades, matriculaNecessidadesSalvas);
//            matriculaSalva.setNecessidades(matriculaNecessidadesSalvas);
//        }

        if (Objects.nonNull(matriculaTurmas)){
            Set<MatriculaTurma> matriculaTurmasSalvas = new HashSet<>();
            salvaTurmasMatricula(matriculaSalva, matriculaTurmas, matriculaTurmasSalvas);
            matriculaSalva.setTurmas(matriculaTurmasSalvas);
        }

        if (Objects.nonNull(matriculaResponsaveis)){
            Set<Responsavel> matriculaResponsaveisSalvas = new HashSet<>();
            salvaResponsaveisMatricula(matriculaSalva, matriculaResponsaveis, matriculaResponsaveisSalvas);
            matriculaSalva.setResponsaveis(matriculaResponsaveisSalvas);

        }

        return matriculaSalva;
    }

    private void salvaResponsaveisMatricula(Matricula matriculaSalva, Set<Responsavel> matriculaResponsaveis, Set<Responsavel> matriculaResponsaveisSalvas) {

        for (Responsavel responsavel : matriculaResponsaveis){

            responsavel.setMatricula(matriculaSalva);
            matriculaResponsaveisSalvas.add(
                    responsavelService.incluir(responsavel)
            );
        }

    }

    private void salvaTurmasMatricula(Matricula matriculaSalva, Set<MatriculaTurma> matriculaTurmas, Set<MatriculaTurma> matriculaTurmasSalvas) {

        for (MatriculaTurma matriculaTurma : matriculaTurmas){
            matriculaTurma.setMatricula(matriculaSalva);
            matriculaTurmasSalvas.add(
                    matriculaTurmaService.incluir(matriculaTurma)
            );
        }

    }


    private Set<Responsavel> tratarMatriculaResponsaveis(Matricula matricula) {

        if(Objects.nonNull(matricula.getResponsaveis()) && !matricula.getResponsaveis().isEmpty()){
            Set<Responsavel> matriculaResponsaveis = matricula.getResponsaveis();
            matricula.setResponsaveis(new HashSet<>());
            return matriculaResponsaveis;
        }

        matricula.setResponsaveis(new HashSet<>());
        return null;
    }

    private Set<MatriculaTurma> tratarMatriculaTurmas(Matricula matricula) {

        if(Objects.nonNull(matricula.getTurmas()) && !matricula.getTurmas().isEmpty()){
            Set<MatriculaTurma> matriculaTurmas = matricula.getTurmas();
            matricula.setTurmas(new HashSet<>());
            return matriculaTurmas;
        }

        matricula.setTurmas(new HashSet<>());
        return null;

    }

//    private Set<MatriculaNecessidade> tratarMatriculaNecessidade(Matricula matricula) {
////
////        if(Objects.nonNull(matricula.getNecessidades()) && !matricula.getNecessidades().isEmpty()){
////            Set<MatriculaNecessidade> matriculaNecessidades = matricula.getNecessidades();
////            matricula.setNecessidades(new HashSet<>());
////            return matriculaNecessidades;
////        }
////
////        matricula.setNecessidades(new HashSet<>());
//        return null;
//    }
}
