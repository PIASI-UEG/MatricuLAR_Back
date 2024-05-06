package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.TurmaDTO;
import br.ueg.piasi.MatricuLAR.mapper.TurmaMapperImpl;
import br.ueg.piasi.MatricuLAR.model.Turma;
import br.ueg.piasi.MatricuLAR.service.impl.TurmaServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/${app.api.version}/turma")
public class TurmaController
        extends CrudController<Turma, TurmaDTO, Long, TurmaMapperImpl,
        TurmaServiceImpl> {


    @PostMapping("/adicionaAlunos")
    public ResponseEntity<TurmaDTO> adicionaAlunos(@RequestParam("idTurma") Long idTurma, @RequestBody() List<Long> idAlunos) {

        return ResponseEntity.ok(
                mapper.toDTO(
                        service.adicionaAlunosTurma(idTurma, idAlunos)));
    }
}
