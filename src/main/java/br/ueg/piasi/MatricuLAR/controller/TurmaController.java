package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.TurmaDTO;
import br.ueg.piasi.MatricuLAR.mapper.TurmaMapperImpl;
import br.ueg.piasi.MatricuLAR.model.Turma;
import br.ueg.piasi.MatricuLAR.service.impl.TurmaServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.api.exception.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/${app.api.version}/turma")
public class TurmaController
        extends CrudController<Turma, TurmaDTO, Long, TurmaMapperImpl,
        TurmaServiceImpl> {


    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TurmaDTO.class)
                    )}
            ), @ApiResponse(
                    responseCode = "400",
                    description = "falha ao realizar a busca",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = MessageResponse.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = MessageResponse.class
                            )
                    )}
            )}
    )
    @PostMapping("/adiciona-alunos")
    public ResponseEntity<TurmaDTO> adicionaAlunos(@RequestParam("idTurma") Long idTurma, @RequestBody() List<Long> idAlunos) {

        return ResponseEntity.ok(
                mapper.toDTO(
                        service.adicionaAlunosTurma(idTurma, idAlunos)));
    }

    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TurmaDTO.class)
                    )}
            ), @ApiResponse(
                    responseCode = "400",
                    description = "falha ao realizar a busca",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = MessageResponse.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = MessageResponse.class
                            )
                    )}
            )}
    )
    @PostMapping("/adiciona-um-aluno")
    public ResponseEntity<TurmaDTO> adicionaUmAluno(@RequestParam("idTurma") Long idTurma, @RequestParam("idAluno") Long idAlunos) {

        return ResponseEntity.ok(
                mapper.toDTO(
                        service.adicionarAlunoTurma(idTurma, idAlunos)));
    }

    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Long.class)
                    )}
            ), @ApiResponse(
                    responseCode = "400",
                    description = "falha ao realizar a busca",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = MessageResponse.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = MessageResponse.class
                            )
                    )}
            )}
    )
    @GetMapping(path = "/quantidade-total")
    private ResponseEntity<Long> quantidadeTotal(){
        return ResponseEntity.ok(service.quantidadeTotal());
    }


    @PostMapping("/remove-alunos/{idTurma}")
    @Operation(
            description = "Remove alunos da turma",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TurmaDTO.class)
                    )}
            ), @ApiResponse(
                    responseCode = "400",
                    description = "falha ao realizar a busca",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = MessageResponse.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = MessageResponse.class
                            )
                    )}
            )}
    )
    private ResponseEntity<TurmaDTO> removeAlunosTurma(@PathVariable Long idTurma, @RequestBody List<Long> idAlunos){
        return ResponseEntity.ok(
                mapper.toDTO(
                        service.removeAlunosTurma(idTurma, idAlunos)
                )
        );
    }
}
