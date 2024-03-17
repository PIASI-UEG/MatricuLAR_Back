package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.model.DocumentoMatricula;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkDocumentoMatricula;
import br.ueg.piasi.MatricuLAR.repository.DocumentoMatriculaRepository;
import br.ueg.piasi.MatricuLAR.service.DocumentoMatriculaService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class DocumentoMatriculaServiceImpl extends BaseCrudService<DocumentoMatricula, PkDocumentoMatricula, DocumentoMatriculaRepository>
        implements DocumentoMatriculaService {


    @Override
    protected void prepararParaIncluir(DocumentoMatricula entidade) {

    }

    @Override
    protected void validarDados(DocumentoMatricula entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(DocumentoMatricula entidade) {

    }
}
