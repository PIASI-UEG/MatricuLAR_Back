package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.ResponsavelDTO;
import br.ueg.piasi.MatricuLAR.dto.TurmaDTO;
import br.ueg.piasi.MatricuLAR.mapper.ResponsavelMapper;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkResponsavel;
import br.ueg.piasi.MatricuLAR.service.impl.ResponsavelServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.api.exception.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/${app.api.version}/responsavel")
public class ResponsavelController extends
        CrudController<Responsavel, ResponsavelDTO, PkResponsavel, ResponsavelMapper, ResponsavelServiceImpl> {


    @PostMapping("/incluir-responsavel")
    @Operation(
            description = "Adiciona responsavel",
            responses = {@ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponsavelDTO.class)
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
    public ResponseEntity<ResponsavelDTO> incluirResponsavel(@RequestBody ResponsavelDTO responsavelDTO) {

        return ResponseEntity.ok(mapper.toDTO(
                service.incluirResponsavel(mapper.toModelo(responsavelDTO))));
    }

    @DeleteMapping("/remover-responsavel/{id-matricula}/{cpf-responsavel}")
    @Operation(
            description = "Remove responsavel",
            responses = {@ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponsavelDTO.class)
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
    public ResponseEntity<ResponsavelDTO> removerResponsavel(@PathVariable("id-matricula") Long idMatricula,
                                                             @PathVariable("cpf-responsavel") String cpfResponsavel) {

        return ResponseEntity.ok(mapper.toDTO(
                service.excluir(new PkResponsavel(idMatricula, cpfResponsavel))));
    }
}
