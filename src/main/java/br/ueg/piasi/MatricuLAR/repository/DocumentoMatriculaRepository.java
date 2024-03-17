package br.ueg.piasi.MatricuLAR.repository;

import br.ueg.piasi.MatricuLAR.model.DocumentoMatricula;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkDocumentoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoMatriculaRepository extends JpaRepository<DocumentoMatricula, PkDocumentoMatricula> {

}
