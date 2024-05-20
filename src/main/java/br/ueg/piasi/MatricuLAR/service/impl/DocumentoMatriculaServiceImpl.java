package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode;
import br.ueg.piasi.MatricuLAR.model.DocumentoMatricula;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkDocumentoMatricula;
import br.ueg.piasi.MatricuLAR.repository.DocumentoMatriculaRepository;
import br.ueg.piasi.MatricuLAR.service.DocumentoMatriculaService;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.TimeZone;

import static br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode.ERRO_ENCONTRAR_DOCUMENTO_ARQUIVO_NAO_ENCONTRADO;

@Service
public class DocumentoMatriculaServiceImpl extends BaseCrudService<DocumentoMatricula, PkDocumentoMatricula, DocumentoMatriculaRepository>
        implements DocumentoMatriculaService {
    private final Path root = Paths.get("docs");

    @Autowired
    private DocumentoMatriculaRepository repository;

    @Override
    protected void prepararParaIncluir(DocumentoMatricula entidade) {

    }

    @Override
    protected void validarDados(DocumentoMatricula entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(DocumentoMatricula entidade) {

    }

    public void uploadDocumentos(Long idMatricula, TipoDocumento tipoDocumento, MultipartFile documento) {

        try {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            String pastaMatricula = montaPastaMatricula(idMatricula);
            Path pathPastaMatricula = Paths.get("docs/"+pastaMatricula);

            if(!Files.exists(pathPastaMatricula)) {
                Files.createDirectories(pathPastaMatricula);
            }
            String nomeArquivo = montaNomeArquivo(idMatricula, tipoDocumento, documento);

            Path pathCaminhoDoc = pathPastaMatricula.resolve(nomeArquivo);
            Files.copy(documento.getInputStream(), pathCaminhoDoc);

            this.repository.saveAndFlush(
                    DocumentoMatricula.builder()
                            .matricula(Matricula.builder().id(idMatricula).build())
                            .idTipoDocumento(tipoDocumento.getId())
                            .caminhoDocumento(nomeArquivo)
                            .aceito(false)
                            .build()
            );

        }catch (Exception e){
            throw new BusinessException(SistemaMessageCode.ERRO_INCLUIR_DOCUMENTO, e);
        }
    }

    private String montaPastaMatricula(Long idMatricula) {
        return "MAT_" + idMatricula;
    }

    private String montaNomeArquivo(Long idMatricula, TipoDocumento tipoDocumento, MultipartFile documento) {

        StringBuilder sbCaminhoDoc = new StringBuilder();
        sbCaminhoDoc.append(idMatricula)
                .append("_")
                .append(tipoDocumento.getId());

        if (ehContraCheque(tipoDocumento)){
            adicionaMesAnoCaminho(sbCaminhoDoc);
        }
        sbCaminhoDoc.append(".")
                .append(Objects.requireNonNull(documento.getContentType()).split("/")[1]);

        return sbCaminhoDoc.toString();

    }

    private static void adicionaMesAnoCaminho(StringBuilder sbCaminhoDoc) {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        LocalDate localDate = LocalDate.now();
        sbCaminhoDoc.append("_")
                .append(localDate.getMonthValue())
                .append("_")
                .append(localDate.getYear());
    }

    private boolean ehContraCheque(TipoDocumento tipoDocumento) {

        return tipoDocumento.equals(TipoDocumento.CONTRA_CHEQUE1T1)
                || tipoDocumento.equals(TipoDocumento.CONTRA_CHEQUE2T1)
                || tipoDocumento.equals(TipoDocumento.CONTRA_CHEQUE3T1)
                || tipoDocumento.equals(TipoDocumento.CONTRA_CHEQUE1T2)
                || tipoDocumento.equals(TipoDocumento.CONTRA_CHEQUE2T2)
                || tipoDocumento.equals(TipoDocumento.CONTRA_CHEQUE3T2);
    }

    public Resource getDocumentoMatricula(String caminhdoDoc){

        try {
            String caminhoDocComPasta = "MAT_"+caminhdoDoc.charAt(0)+"/"+caminhdoDoc;
            Path arquivo = root.resolve(caminhoDocComPasta);

            Resource resource = new UrlResource(arquivo.toUri());

            if (resource.exists() || resource.isReadable()){
                return resource;
            }
            throw new BusinessException(ERRO_ENCONTRAR_DOCUMENTO_ARQUIVO_NAO_ENCONTRADO);
        }catch (Exception e ){
            throw new BusinessException(ERRO_ENCONTRAR_DOCUMENTO_ARQUIVO_NAO_ENCONTRADO);
        }
    }

    public void atualizaContraChequeMatricula(Long idMatricula, TipoDocumento tipoDocumento, MultipartFile multipartFile) {

        DocumentoMatricula documentoMatricula =
                repository.findByMatricula_IdAndIdTipoDocumento(idMatricula, tipoDocumento.getId())
                        .orElse(null);

        if (Objects.isNull(documentoMatricula)){
            throw new BusinessException(ERRO_ENCONTRAR_DOCUMENTO_ARQUIVO_NAO_ENCONTRADO);
        }

        try{
            Path arquivo = root.resolve(documentoMatricula.getCaminhoDocumento());
            Files.delete(arquivo);
            uploadDocumentos(idMatricula, tipoDocumento, multipartFile);

        }catch (Exception e){
            throw new BusinessException(ERRO_ENCONTRAR_DOCUMENTO_ARQUIVO_NAO_ENCONTRADO);
        }


    }
}
