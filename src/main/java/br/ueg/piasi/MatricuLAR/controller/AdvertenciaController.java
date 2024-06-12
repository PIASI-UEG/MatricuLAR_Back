package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.AdvertenciaDTO;
import br.ueg.piasi.MatricuLAR.dto.TurmaDTO;
import br.ueg.piasi.MatricuLAR.dto.UsuarioDTO;
import br.ueg.piasi.MatricuLAR.mapper.AdvertenciaMapper;
import br.ueg.piasi.MatricuLAR.mapper.UsuarioMapperImpl;
import br.ueg.piasi.MatricuLAR.model.Advertencia;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkAdvertencia;
import br.ueg.piasi.MatricuLAR.service.impl.AdvertenciaServiceImpl;
import br.ueg.piasi.MatricuLAR.service.impl.UsuarioServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.api.exception.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/${app.api.version}/advertencia")
public class AdvertenciaController extends
        CrudController<Advertencia, AdvertenciaDTO, PkAdvertencia, AdvertenciaMapper, AdvertenciaServiceImpl> {

    @Operation(
            description = "Remove alunos da turma",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AdvertenciaDTO.class)
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
    @GetMapping("/obter-advertencia/{id-matricula}/{numero-advertencia}")
    public ResponseEntity<AdvertenciaDTO> obterAdvertencia(@PathVariable("id-matricula") Long idMatricula,
                                                           @PathVariable("numero-advertencia") Long nroAdvertencia) {
        return super.obterPorId(new PkAdvertencia(idMatricula, nroAdvertencia));
    }

    @Operation(
            description = "Remove alunos da turma",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AdvertenciaDTO.class)
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
    @PutMapping("/alterar-advertencia/{id-matricula}/{numero-advertencia}")
    public ResponseEntity<AdvertenciaDTO> alterarAdvertencia(@PathVariable("id-matricula") Long idMatricula,
                                                           @PathVariable("numero-advertencia") Long nroAdvertencia,
                                                             @RequestBody AdvertenciaDTO advertenciaDTO) {
        return super.alterar(advertenciaDTO,new PkAdvertencia(idMatricula, nroAdvertencia));
    }

    @Operation(
            description = "Remove alunos da turma",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AdvertenciaDTO.class)
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
    @DeleteMapping("/remover-advertencia/{id-matricula}/{numero-advertencia}")
    public ResponseEntity<AdvertenciaDTO> removerAdvertencia(@PathVariable("id-matricula") Long idMatricula,
                                                             @PathVariable("numero-advertencia") Long nroAdvertencia) {
        return super.remover(new PkAdvertencia(idMatricula, nroAdvertencia));
    }
}
