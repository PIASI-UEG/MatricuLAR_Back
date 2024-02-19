package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.piasi.MatricuLAR.model.Tutor;
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
    public Matricula incluir(Matricula matricula) {

        Set<Responsavel> responsavelSet = tratarMatriculaResponsaveis(matricula);
        matricula.setAdvertencias(new HashSet<>());
        matricula.setTurmas(new HashSet<>());

        matricula = super.incluir(matricula);

        salvarResponsaveis(responsavelSet, matricula);

        return matricula;
    }

    private void salvarResponsaveis(Set<Responsavel> responsavelSet, Matricula matricula){

        if (Objects.nonNull(responsavelSet)) {
            Set<Responsavel> responsavelSetSalvos = new HashSet<>();
            for (Responsavel responsavel : responsavelSet) {
                responsavel.setMatricula(matricula);
                responsavelSetSalvos.add(responsavelService.incluir(responsavel));
            }

            matricula.setResponsaveis(responsavelSetSalvos);
        }
        else
            System.out.println(("A matricula deve ter pelo menos um responsavel"));
    }

    private Set<Responsavel> tratarMatriculaResponsaveis(Matricula matricula) {

        if (Objects.nonNull(matricula.getTutorList()) && !matricula.getTutorList().isEmpty()){
            Set<Responsavel> responsavelSet = new HashSet<>();

            for (Tutor tutor : matricula.getTutorList()){
                responsavelSet.add(Responsavel.builder()
                                .matricula(matricula)
                                .pessoa(tutor.getPessoa())
                                .tutor(true)
                                .vinculo(tutor.getVinculo())
                        .build());
            }

            matricula.setResponsaveis(new HashSet<>());
            return  responsavelSet;
        }
        else matricula.setResponsaveis(new HashSet<>());
        return null;
    }

}
