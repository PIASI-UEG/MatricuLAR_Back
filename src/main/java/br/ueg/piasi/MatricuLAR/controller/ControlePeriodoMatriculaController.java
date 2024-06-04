package br.ueg.piasi.MatricuLAR.controller;

import br.ueg.piasi.MatricuLAR.dto.ControlePeriodoMatriculaDTO;
import br.ueg.piasi.MatricuLAR.mapper.ControlePeriodoMatriculaMapperImpl;
import br.ueg.piasi.MatricuLAR.model.ControlePeriodoMatricula;
import br.ueg.piasi.MatricuLAR.service.impl.ControlePeriodoMatriculaServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/${app.api.version}/controle-periodo")
public class ControlePeriodoMatriculaController extends CrudController<ControlePeriodoMatricula,
        ControlePeriodoMatriculaDTO, Long, ControlePeriodoMatriculaMapperImpl, ControlePeriodoMatriculaServiceImpl> {

    @PostMapping("/atualiza-periodo")
    private ResponseEntity<ControlePeriodoMatriculaDTO> atualizaPeriodoMatriculaCompleto(@RequestBody ControlePeriodoMatriculaDTO dto) {
        return ResponseEntity.ok(
                mapper.toDTO(
                        service.atualizaPeriodoMatriculaCompleto(mapper.toModelo(dto))
                )
        );
    }

    @PostMapping("/atualiza-periodo/{aceitandoCadastroMatricula}")
    private ResponseEntity<ControlePeriodoMatriculaDTO> ativaPeriodoMatricula(@PathVariable Boolean aceitandoCadastroMatricula) {
        return ResponseEntity.ok(
                mapper.toDTO(
                        service.atualizaPeriodoMatricula(aceitandoCadastroMatricula)
                )
        );
    }
}
