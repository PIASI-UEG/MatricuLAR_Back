package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.DocumentoMatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaListagemDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaVisualizarDTO;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapper;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.service.impl.MatriculaServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import br.ueg.prog.webi.api.dto.SearchFieldValue;
import br.ueg.prog.webi.api.exception.ApiMessageCode;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.exception.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/${app.api.version}/matricula")
public class MatriculaController extends CrudController<Matricula, MatriculaDTO, Long, MatriculaMapper, MatriculaServiceImpl> {


    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MatriculaDTO.class)
                    )}
            ), @ApiResponse(
                    responseCode = "400",
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
    @PostMapping(path = "/inclusao-com-docs",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MatriculaDTO> incluirComDocumentos(@RequestPart("dto") MatriculaDTO dto,
                                                             @RequestPart("files") List<MultipartFile> files) {
        service.validaPeriodoMatricula();
        Matricula matricula = mapper.toModelo(dto);
        return ResponseEntity.ok(
                mapper.toDTO(
                        service.incluirComDocumentos(matricula, files)
                )
        );
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/documento")
    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MatriculaDTO.class)
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
    public ResponseEntity<MatriculaDTO> uploadDocumento(@RequestParam Long idMatricula, @RequestParam TipoDocumento tipoDocumento,
                                                        @RequestBody MultipartFile multipartFile) {
        return ResponseEntity.ok(
                mapper.toDTO(service.uploadDocumento(idMatricula, tipoDocumento, multipartFile)));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/documentos")
    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MatriculaDTO.class)
                    )}
            ), @ApiResponse(
                    responseCode = "400",
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
    public ResponseEntity<MatriculaDTO> uploadDocumentos(@RequestParam Long idMatricula,
                                                         @RequestBody MultipartFile[] multipartFile) {
        return ResponseEntity.ok(
                mapper.toDTO(service.uploadDocumentos(idMatricula, multipartFile, null)));
    }


    @PostMapping(path = "/obter-documento")
    @Operation(
            description = "Busca a quantidade de registros",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Arquivo do documento",
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = Resource.class
                                    )
                            )}
                    ),
                    @ApiResponse(
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
    public ResponseEntity<Resource> obterDocumentoMatricula(@RequestBody DocumentoMatriculaDTO documentoMatriculaDTO) {
        Resource arquivo = service.getDocumentoMatricula(documentoMatriculaDTO);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + arquivo.getFilename() + "\"").body(arquivo);
    }


    @GetMapping(path = "/termo/{caminhodoc}")
    public ResponseEntity<Resource> getTermo(@PathVariable(name = "caminhodoc") String caminhodoc) {
        Resource arquivo = service.getTermo(caminhodoc);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getFilename() + "\"").body(arquivo);
    }

    @PostMapping(path = "valida")
    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MatriculaDTO.class)
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
    public ResponseEntity<MatriculaDTO> validaMatricula(@RequestBody MatriculaDTO matriculaDTO) {

        Matricula matriculaValida = service.validaMatricula(mapper.toModelo(matriculaDTO));
        return ResponseEntity.ok(mapper.toDTO(matriculaValida));

    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/documento/atualiza-documento")
    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MatriculaDTO.class)
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
    public ResponseEntity<MatriculaDTO> atualizaDocumentoMatricula(@RequestParam Long idMatricula, @RequestParam TipoDocumento tipoDocumento,
                                                                   @RequestBody MultipartFile multipartFile) {
        return ResponseEntity.ok(
                mapper.toDTO(service.atualizaDocumentoMatricula(idMatricula, tipoDocumento, multipartFile)));
    }

    @GetMapping("/listar-matriculas-status")
    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MatriculaListagemDTO.class)
                            )
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
    private ResponseEntity<List<MatriculaListagemDTO>> listarMatriculasListagemPorStatus(@RequestParam(name = "statusMatricula")
                                                                                         StatusMatricula statusMatricula) {
        List<Matricula> matriculas = service.listarMatriculasListagemPorStatus(statusMatricula);
        return ResponseEntity.ok(mapper.toMatriculaListagemDTO(matriculas));
    }


    @GetMapping("/listar-matriculas-status-pagination/{offset}/{pageSize}")
    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MatriculaListagemDTO.class)
                            )
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
    public ResponseEntity<List<MatriculaListagemDTO>> listAllPageMatriculaListagemDTO(@PathVariable int offset, @PathVariable int pageSize,
                                                                                      @RequestParam(name = "statusMatricula") StatusMatricula statusMatricula) {
        return ResponseEntity.ok(
                this.mapper.toMatriculaListagemDTO(
                        this.service.listarMatriculaListagemPage(offset, pageSize, statusMatricula)
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
                            schema = @Schema(implementation = Integer.class)
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

    @GetMapping("/matricula-visualiza")
    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MatriculaVisualizarDTO.class)
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
    public ResponseEntity<MatriculaVisualizarDTO> getMatriculaVisualizar(@RequestParam(value = "IdMatricula")
                                                                         Long idMatricula) {
        return ResponseEntity.ok(mapper.toMatriculaVisualizarDTO(
                        service.obterPeloId(idMatricula)
                )
        );

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
    public ResponseEntity<MatriculaDTO> gerarTermo(@PathVariable(name = "id") Long id, @RequestParam(name = "nomeTutor") String nomeTutor) throws JRException, IOException {
        return ResponseEntity.ok(
                mapper.toDTO(service.geraTermo(id, nomeTutor)));
    }

    @PostMapping(path = "/dados/{id}")
    @Operation(
            description = "Gera o pdf com os dados da matricula do aluno",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Caminho do termo",
                    content = {@Content(
                            mediaType = "application/json"
                    )}
            ), @ApiResponse(
                    responseCode = "400",
                    description = "Falha ao gerar pdf",
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
    public ResponseEntity<MatriculaDTO> gerarPdfDados(@PathVariable(name = "id") Long id) throws JRException, IOException {
        return ResponseEntity.ok(
                mapper.toDTO(service.geraPdfDadosMatricula(id)));
    }


    @GetMapping("/listar-por-turma")
    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MatriculaListagemDTO.class)
                            )
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
    private ResponseEntity<List<MatriculaListagemDTO>> listarAlunosPorTurma(@RequestParam("idTurma") Long idTurma) {

        return ResponseEntity.ok(mapper.toMatriculaListagemDTO(
                        service.listarAlunosPorTurma(idTurma)
                )
        );
    }


    @Operation(
            description = "Busca a quantidade de registros",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Long.class))}
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
    @GetMapping(path = "quantidade-total")
    private ResponseEntity<Long> quantidadeTotalMatriculas() {
        return ResponseEntity.ok(service.quantidadeTotalMatriculas());
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
    @GetMapping(path = "/quantidade-status")
    private ResponseEntity<Long> quantidadeMatriculasPorStatus(@RequestParam StatusMatricula statusMatricula) {
        return ResponseEntity.ok(service.quantidadeMatriculasPorStatus(statusMatricula));
    }

    @Operation(
            description = "Listagem dos campos de busca",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem geral",
                    content = {@Content(
                            mediaType = "application/json"
                    )}
            ), @ApiResponse(
                    responseCode = "400",
                    description = "Modelo não parametrizado para pesquisa",
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
    @PostMapping(path = "/search-fields-listagem-page")
    public ResponseEntity<Page<MatriculaListagemDTO>> searchFieldsActionPageMatriculaListagemDto(
            @RequestBody List<SearchFieldValue> searchFieldValues,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "5", required = false) Integer size,
            @RequestParam(name = "sort", defaultValue = "", required = false) List<String> sort) {
        return ResponseEntity.ok(service.getPageListagemDTO(searchFieldValues, page, size, sort));
    }

    @PostMapping(path = "search-fields-listagem")
    @Operation(
            description = "Realiza a busca pelos valores dos campos informados",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Listagem do resultado",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MatriculaListagemDTO.class)
                            ))}
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
    public ResponseEntity<List<MatriculaListagemDTO>> searchFieldsActionMatriculaListagemDto(
            @RequestBody List<SearchFieldValue> searchFieldValues) {
        List<Matricula> matriculas = service.searchFieldValues(searchFieldValues);
        if(matriculas.isEmpty()){
            throw new BusinessException(ApiMessageCode.SEARCH_FIELDS_RESULT_NONE);
        }
        return ResponseEntity.ok(mapper.toMatriculaListagemDTO(matriculas));
    }
}
