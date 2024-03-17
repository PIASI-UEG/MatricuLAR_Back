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
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DocumentoMatriculaServiceImpl extends BaseCrudService<DocumentoMatricula, PkDocumentoMatricula, DocumentoMatriculaRepository>
        implements DocumentoMatriculaService {
    private final Path root = Paths.get("docs");

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

            String caminhoDoc = tipoDocumento.getId() + idMatricula + "." +documento.getContentType().split("/")[1];
            Path pathCaminhoDoc = this.root.resolve(caminhoDoc);
            Files.copy(documento.getInputStream(), pathCaminhoDoc);

            this.repository.save(
                    DocumentoMatricula.builder()
                            .matricula(Matricula.builder().id(idMatricula).build())
                            .idTipoDocumento(tipoDocumento.getId())
                            .caminhoDocumento(caminhoDoc)
                            .aceito(false)
                            .build()
            );

        }catch (Exception e){
            throw new BusinessException(SistemaMessageCode.ERRO_INCLUIR_DOCUMENTO, e);
        }
    }

    public Resource carregaDocumentoPeloCaminho(String caminhdoDoc){

        try {
            Path arquivo = root.resolve(caminhdoDoc);

            Resource resource = new UrlResource(arquivo.toUri());

            if (resource.exists() || resource.isReadable()){
                return resource;
            }

        }catch (Exception e ){
            throw new BusinessException(SistemaMessageCode.ERRO_ENCONTRAR_DOCUMENTO_ARQUIVO_NAO_ENCONTRADO);
        }
        return null;
    }
}
