package br.ueg.piasi.MatricuLAR.controller;


import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapper;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.service.impl.MatriculaServiceImpl;
import br.ueg.piasi.MatricuLAR.util.TermoDeResponsabilidade;
import br.ueg.prog.webi.api.controller.CrudController;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;

@RestController
@RequestMapping(path = "/api/${app.api.version}/matricula")
public class MatriculaController extends CrudController<Matricula, MatriculaDTO, Long, MatriculaMapper, MatriculaServiceImpl> {

    @Autowired
    private TermoDeResponsabilidade termo;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,path = "/documentos")
    public ResponseEntity<MatriculaDTO> uploadDocumento(@RequestParam Long idMatricula, @RequestParam TipoDocumento tipoDocumento,
                                           @RequestBody MultipartFile multipartFile) throws IOException {

        return ResponseEntity.ok(
                mapper.toDTO(service.uploadDocumento(idMatricula, tipoDocumento, multipartFile)));
    }

    @PostMapping(path = "/termoAssinado")
    public ResponseEntity<MatriculaDTO> uploadTermoAssinado(@RequestParam Long idMatricula,
                                                        @RequestBody String imgAss) throws IOException, JRException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        return ResponseEntity.ok(
                mapper.toDTO(service.uploadTermoAssinado(idMatricula, imgAss)));
    }

    @PostMapping(path = "/termo")
    public ResponseEntity<MatriculaDTO> uploadTermo(@RequestParam Long idMatricula) throws IOException, JRException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        return ResponseEntity.ok(
                mapper.toDTO(service.uploadTermo(idMatricula)));
    }


    @GetMapping(path = "/documento/{caminhodoc}")
    public ResponseEntity<Resource> getDocumentoMatricula(@PathVariable(name = "caminhodoc") String caminhodoc){

        Resource arquivo = service.getDocumentoMatricula(caminhodoc);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getFilename() + "\"").body(arquivo);
    }

    @GetMapping(path = "/termoAssinado/{caminhodoc}")
    public ResponseEntity<Resource> getTermoAssinado(@PathVariable(name = "caminhodoc") String caminhodoc){

        Resource arquivo = service.getDocumentoMatricula("C:\\Users\\Nahta\\IdeaProjects\\PIASI - Associacao Sagrada Familia\\MatricuLAR_Back\\src\\main\\resources\\images\\" + caminhodoc);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getFilename() + "\"").body(arquivo);
    }

    @GetMapping(path = "/termo/{caminhodoc}")
    public ResponseEntity<Resource> getTermo(@PathVariable(name = "caminhodoc") String caminhodoc){

        Resource arquivo = service.getDocumentoMatricula("C:\\Users\\Nahta\\IdeaProjects\\PIASI - Associacao Sagrada Familia\\MatricuLAR_Back\\src\\main\\resources\\images\\" + caminhodoc);
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
}
