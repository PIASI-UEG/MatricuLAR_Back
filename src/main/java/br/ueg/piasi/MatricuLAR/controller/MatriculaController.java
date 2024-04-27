package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaListagemDTO;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapper;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.service.impl.MatriculaServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.api.exception.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/${app.api.version}/matricula")
public class MatriculaController extends CrudController<Matricula, MatriculaDTO, Long, MatriculaMapper, MatriculaServiceImpl> {


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,path = "/documentos")
    public ResponseEntity<MatriculaDTO> uploadDocumento(@RequestParam Long idMatricula, @RequestParam TipoDocumento tipoDocumento,
                                           @RequestBody MultipartFile multipartFile) throws IOException {

        return ResponseEntity.ok(
                mapper.toDTO(service.uploadDocumento(idMatricula, tipoDocumento, multipartFile)));
    }


    @GetMapping(path = "/documento/{caminhodoc}")
    public ResponseEntity<Resource> getDocumentoMatricula(@PathVariable(name = "caminhodoc") String caminhodoc){

        Resource arquivo = service.getDocumentoMatricula(caminhodoc);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getFilename() + "\"").body(arquivo);
    }

    @GetMapping(path = "/termo/{caminhodoc}")
    public ResponseEntity<Resource> getTermo(@PathVariable(name = "caminhodoc") String caminhodoc){

        Resource arquivo = service.getDocumentoMatricula(caminhodoc);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getFilename() + "\"").body(arquivo);
    }

    @PostMapping(path = "valida")
    public ResponseEntity<MatriculaDTO> validaMatricula(@RequestBody MatriculaDTO matriculaDTO){

        Matricula matriculaValida = service.validaMatricula(mapper.toModelo(matriculaDTO));
        return ResponseEntity.ok(mapper.toDTO(matriculaValida));

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/documento/atualiza-contra-cheque")
    public ResponseEntity<MatriculaDTO> atualizaContraChequeMatricula(@RequestParam Long idMatricula, @RequestParam TipoDocumento tipoDocumento,
                                                                      @RequestBody MultipartFile multipartFile){
        return ResponseEntity.ok(
                mapper.toDTO(service.atualizaContraChequeMatricula(idMatricula, tipoDocumento, multipartFile)));
    }

    @GetMapping("/listar-matriculas-status")
    private ResponseEntity<List<MatriculaListagemDTO>> listarMatriculasListagemPorStatus(@RequestParam(name = "statusMatricula")
                                                                        StatusMatricula statusMatricula){

        List<Matricula> matriculas = service.listarMatriculasListagemPorStatus(statusMatricula);
        return ResponseEntity.ok(mapper.toMatriculaListagemDTO(matriculas));
    }


    @GetMapping("/listar-matriculas-status-pagination/{offset}/{pageSize}")
    public ResponseEntity<List<MatriculaListagemDTO>> listAllPageMatriculaListagemDTO(@PathVariable int offset, @PathVariable int pageSize,
                                                                                      @RequestParam (name="statusMatricula") StatusMatricula statusMatricula) {
        return ResponseEntity.ok(
                this.mapper.toMatriculaListagemDTO(
                        this.service.listarMatriculaListaemPage(offset, pageSize, statusMatricula)
                )
        );
    }

    @GetMapping("/listar-matriculas-status-pagination")
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
    public Integer count(@RequestParam StatusMatricula statusMatricula) {
        return this.service.countRowsWithStatus(statusMatricula);
    }

    @PostMapping(path = "/termo/{id}")
    @Operation(
            description = "Gera o termo da matricula",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Caminho do termo",
                    content = {@Content(
                            mediaType = "application/json"
                    )}
            ), @ApiResponse(
                    responseCode = "400",
                    description = "Falha ao gerar termo",
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
    public ResponseEntity<MatriculaDTO> gerarTermo(@PathVariable(name = "id") Long id, @RequestParam(name = "cpfTutor")String cpfTutor) throws JRException, IOException {
        return ResponseEntity.ok(
                mapper.toDTO(service.geraTermo(id, cpfTutor)));
    }
}
