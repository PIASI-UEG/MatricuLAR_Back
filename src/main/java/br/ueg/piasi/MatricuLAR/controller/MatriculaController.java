package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapper;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.service.impl.MatriculaServiceImpl;
import br.ueg.piasi.MatricuLAR.util.TermoDeResponsabilidade;
import br.ueg.prog.webi.api.controller.CrudController;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.core.JsonGenerator;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping(path = "/api/${app.api.version}/matricula")
public class MatriculaController extends CrudController<Matricula, MatriculaDTO, Long, MatriculaMapper, MatriculaServiceImpl> {


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,path = "/documentos")
    public ResponseEntity<MatriculaDTO> uploadDocumento(@RequestParam Long idMatricula, @RequestParam TipoDocumento tipoDocumento,
                                           @RequestBody MultipartFile multipartFile) throws IOException {

        return ResponseEntity.ok(
                mapper.toDTO(service.uploadDocumento(idMatricula, tipoDocumento, multipartFile)));
    }

    @PostMapping(path = "/termo")
    @Operation(
            description = "Listagem Geral",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "PDF",
                    content = {@Content(
                            mediaType = "application/pdf",
                            array = @ArraySchema
                    )}
            )})
    public ResponseEntity<MatriculaDTO> uploadTermo(@RequestParam String cpfCrianca, @RequestParam String chavePub) throws IOException, JRException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidKeySpecException {

        System.out.println("chaves chave:" + chavePub);
        return ResponseEntity.ok(mapper.toDTO(service.uploadTermo(cpfCrianca, chavePub)));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/validarTermo")
    public ResponseEntity<MatriculaDTO> uploadTermoValidar(@RequestParam String cpfCrianca, @RequestBody MultipartFile multipartFile) throws IOException, JRException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidKeySpecException {

        return ResponseEntity.ok(mapper.toDTO(service.uploadTermoValidar(cpfCrianca, multipartFile)));
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

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,path = "/documentos")
//    public ResponseEntity<String> uploadTermoAssinadoFront(@RequestParam PublicKey publicKey, @RequestBody MultipartFile multipartFile) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
//
//        return ResponseEntity.ok(
//                service.uploadTermoAssinadoFront(multipartFile, publicKey));
//    }


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

//    @PostMapping(path ="/termo/{id}")
//    public ResponseEntity<List<AssinaturaDTO>> gerarTermoBack(@PathVariable Long id){
//        System.out.println("entrou no gerar termo");
//        return ResponseEntity.ok(termo.gerarTermoSemAss("",id));
//    }

    @GetMapping(path = "/termo")
    public ResponseEntity<Resource> geraTermo(@RequestParam String cpfCrianca) throws IOException, JRException {

        Resource arquivo = service.geraTermo(cpfCrianca);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getFilename() + "\"").body(arquivo);
    }
}
