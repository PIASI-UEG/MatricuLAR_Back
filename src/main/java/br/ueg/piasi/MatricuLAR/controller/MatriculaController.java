package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaListagemDTO;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapper;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.service.impl.MatriculaServiceImpl;
import br.ueg.prog.webi.api.controller.CrudController;
import org.springframework.core.io.Resource;
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
    private ResponseEntity<List<MatriculaListagemDTO>> listarMatriculasListagemPorStatus(@RequestParam(required = true, name = "statusMatricula")
                                                                        StatusMatricula statusMatricula){

        List<Matricula> matriculas = service.listarMatriculasListagemPorStatus(statusMatricula);


        return ResponseEntity.ok(mapper.toMatriculaListagemDTO(matriculas));
    }
}
