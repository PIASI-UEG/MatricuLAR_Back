package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.model.*;
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
public class MatriculaServiceImpl extends BaseCrudService<Matricula, Long, MatriculaRepository>
        implements MatriculaService {

    @Autowired
    private DocumentoMatriculaServiceImpl documentoMatriculaService;

    @Autowired
    private ResponsavelServiceImpl responsavelService;

    @Autowired
    private PessoaServiceImpl pessoaService;




    @Override
    protected void prepararParaIncluir(Matricula matricula) {
        matricula.setStatus(StatusMatricula.INATIVO);
        matricula.setPessoa(pessoaService.incluir(
                        Pessoa.builder()
                                .nome(matricula.getPessoa().getNome())
                                .cpf(matricula.getPessoa().getCpf())
                                .build()
                )
        );
    }

    @Override
    protected void validarDados(Matricula entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Matricula entidade) {

    }

    @Override
    public Matricula incluir(Matricula matricula) {

        Set<DocumentoMatricula> documentos = matricula.getDocumentoMatricula();
        matricula.setDocumentoMatricula(new HashSet<>());

        Set<Responsavel> responsavelSet = tratarMatriculaResponsaveis(matricula);

        matricula.setAdvertencias(new HashSet<>());

        matricula = super.incluir(matricula);

        salvarResponsaveis(responsavelSet, matricula);
        salvarDocumentosMatricula(documentos, matricula);

        return matricula;
    }

    private void salvarDocumentosMatricula(Set<DocumentoMatricula> documentoMatriculas, Matricula matricula) {

        if (Objects.nonNull(documentoMatriculas)){
            for (DocumentoMatricula documentoMatricula : documentoMatriculas){
                documentoMatricula.setMatricula(matricula);
                documentoMatriculaService.incluir(documentoMatricula);
            }
        }

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
