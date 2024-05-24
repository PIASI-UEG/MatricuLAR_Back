package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.NecessidadeEspecialDTO;
import br.ueg.piasi.MatricuLAR.mapper.NecessidadeEspecialMapperImpl;
import br.ueg.piasi.MatricuLAR.model.NecessidadeEspecial;
import br.ueg.piasi.MatricuLAR.service.impl.NecessidadeEspecialServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.api.exception.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/${app.api.version}/necessidade_esp")
public class NecessidadeEspecialController
        extends CrudController<NecessidadeEspecial, NecessidadeEspecialDTO, Long, NecessidadeEspecialMapperImpl,
        NecessidadeEspecialServiceImpl> {

    @GetMapping()
    @Override
    @Operation(description = "Listagem Geral", responses = {
            @ApiResponse(responseCode = "200", description = "Listagem geral",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = NecessidadeEspecialDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    public ResponseEntity<List<NecessidadeEspecialDTO>> listAll() {
        return super.listAll();
    }
}
