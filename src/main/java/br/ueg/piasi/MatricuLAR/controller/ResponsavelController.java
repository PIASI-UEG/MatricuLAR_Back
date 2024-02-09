package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.ResponsavelDTO;
import br.ueg.piasi.MatricuLAR.mapper.ResponsavelMapper;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkResponsavel;
import br.ueg.piasi.MatricuLAR.service.impl.ResponsavelServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/${app.api.version}/responsavel")
public class ResponsavelController extends
        CrudController<Responsavel, ResponsavelDTO, PkResponsavel, ResponsavelMapper, ResponsavelServiceImpl> {

}
