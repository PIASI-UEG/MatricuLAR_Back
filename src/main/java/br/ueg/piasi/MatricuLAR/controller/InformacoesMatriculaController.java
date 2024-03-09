package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.InformacoesMatriculaDTO;
import br.ueg.piasi.MatricuLAR.mapper.InformacoesMatriculaMapperImpl;
import br.ueg.piasi.MatricuLAR.model.InformacoesMatricula;
import br.ueg.piasi.MatricuLAR.service.impl.InformacoesMatriculaServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/${app.api.version}/infomatricula")
public class InformacoesMatriculaController extends
        CrudController<InformacoesMatricula, InformacoesMatriculaDTO, Long, InformacoesMatriculaMapperImpl, InformacoesMatriculaServiceImpl> {

}
