package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.UsuarioDTO;
import br.ueg.piasi.MatricuLAR.mapper.UsuarioMapperImpl;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Usuario;
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
@RequestMapping(path = "/api/${app.api.version}/usuario")
public class UsuarioController extends CrudController<Usuario, UsuarioDTO, Long, UsuarioMapperImpl, UsuarioServiceImpl> {

    @Override
    @PostMapping("/singup")
    public ResponseEntity<UsuarioDTO> incluir(UsuarioDTO usuarioDTO) {

        Usuario usuarioParaIncluir = mapper.toModelo(usuarioDTO);
        UsuarioDTO novoUser = mapper.toDTO(service.incluir(usuarioParaIncluir));
        return ResponseEntity.ok(novoUser);

    }

    @GetMapping("/sort/{field}")
    @Operation(
            description = "Reliza busca ordenada de acordo com o campo",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema
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
    public ResponseEntity<List<UsuarioDTO>> listAllWithSort(@PathVariable String field) {
        return ResponseEntity.ok(this.mapper.toDTO(this.service.findUsuarioWithSortAsc(field)));
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    @Operation(
            description = "Realiza busca paginada de acordo com o tamanho da pagina e a pagina",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema
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
    public ResponseEntity<List<UsuarioDTO>> listUsuariosWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        return ResponseEntity.ok(this.mapper.toDTO(this.service.findUsuarioWithPagination(offset, pageSize)));
    }

    @GetMapping("/pagination")
    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema
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
    public Integer count() {
        return this.service.countRows();
    }

}
