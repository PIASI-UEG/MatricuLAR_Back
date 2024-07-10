package br.ueg.piasi.MatricuLAR.controller;

import br.ueg.piasi.MatricuLAR.dto.AdvertenciaDTO;
import br.ueg.piasi.MatricuLAR.dto.ControlePeriodoMatriculaDTO;
import br.ueg.piasi.MatricuLAR.mapper.ControlePeriodoMatriculaMapperImpl;
import br.ueg.piasi.MatricuLAR.model.ControlePeriodoMatricula;
import br.ueg.piasi.MatricuLAR.service.impl.ControlePeriodoMatriculaServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.api.exception.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/${app.api.version}/controle-periodo")
public class ControlePeriodoMatriculaController extends CrudController<ControlePeriodoMatricula,
        ControlePeriodoMatriculaDTO, Long, ControlePeriodoMatriculaMapperImpl, ControlePeriodoMatriculaServiceImpl> {


    @Operation(
            description = "atualizando periodo matricula",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ControlePeriodoMatriculaDTO.class)
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
    @PostMapping("/atualiza-periodo")
    private ResponseEntity<ControlePeriodoMatriculaDTO> atualizaPeriodoMatriculaCompleto(@RequestBody ControlePeriodoMatriculaDTO dto) {
        return ResponseEntity.ok(
                mapper.toDTO(
                        service.atualizaPeriodoMatriculaCompleto(mapper.toModelo(dto))
                )
        );
    }


    @Operation(
            description = "atualizando periodo matricula",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ControlePeriodoMatriculaDTO.class)
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
    @PostMapping("/atualiza-periodo/{aceitandoCadastroMatricula}")
    private ResponseEntity<ControlePeriodoMatriculaDTO> ativaPeriodoMatricula(@PathVariable Boolean aceitandoCadastroMatricula) {
        return ResponseEntity.ok(
                mapper.toDTO(
                        service.atualizaPeriodoMatricula(aceitandoCadastroMatricula)
                )
        );
    }

    @Operation(
            description = "obter status",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
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
    @PostMapping("/obter-status")
    private ResponseEntity<Boolean> oberStatus() {
        return ResponseEntity.ok(
            service.obterPeloId(1L).getAceitandoCadastroMatricula()
        );
    }
}
