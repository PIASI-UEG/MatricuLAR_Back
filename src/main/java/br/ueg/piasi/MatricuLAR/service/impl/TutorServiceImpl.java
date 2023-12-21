package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.Tutor;
import br.ueg.piasi.MatricuLAR.repository.TutorRepository;
import br.ueg.piasi.MatricuLAR.service.TutorService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class TutorServiceImpl extends BaseCrudService<Tutor, String, TutorRepository>
        implements TutorService {

    @Override
    protected void prepararParaIncluir(Tutor entidade) {

    }

    @Override
    protected void validarDados(Tutor entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(Tutor entidade) {

    }
}
