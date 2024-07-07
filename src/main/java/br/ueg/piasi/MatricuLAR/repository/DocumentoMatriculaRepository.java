package br.ueg.piasi.MatricuLAR.repository;

import br.ueg.piasi.MatricuLAR.model.DocumentoMatricula;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkDocumentoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DocumentoMatriculaRepository extends JpaRepository<DocumentoMatricula, PkDocumentoMatricula> {

    Optional<DocumentoMatricula> findByMatricula_IdAndIdTipoDocumento(@NonNull Long id, @NonNull String idTipoDocumento);

    Set<DocumentoMatricula> getTodosDocumentosByMatricula_Id(@NonNull Long id);
}
