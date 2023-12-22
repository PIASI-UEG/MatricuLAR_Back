package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.NecessidadeEspecialDTO;
import br.ueg.piasi.MatricuLAR.dto.TutorDTO;
import br.ueg.piasi.MatricuLAR.mapper.NecessidadeEspecialMapperImpl;
import br.ueg.piasi.MatricuLAR.mapper.TutorMapperImpl;
import br.ueg.piasi.MatricuLAR.model.NecessidadeEspecial;
import br.ueg.piasi.MatricuLAR.model.Tutor;
import br.ueg.piasi.MatricuLAR.service.impl.NecessidadeEspecialServiceImpl;
import br.ueg.piasi.MatricuLAR.service.impl.TutorServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/${app.api.version}/necessidade_esp")
public class NecessidadeEspecialController
        extends CrudController<NecessidadeEspecial, NecessidadeEspecialDTO, Long, NecessidadeEspecialMapperImpl,
        NecessidadeEspecialServiceImpl> {


}
