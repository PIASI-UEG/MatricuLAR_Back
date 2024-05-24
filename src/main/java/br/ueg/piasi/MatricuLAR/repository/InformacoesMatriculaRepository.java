package br.ueg.piasi.MatricuLAR.repository;

import br.ueg.piasi.MatricuLAR.model.InformacoesMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformacoesMatriculaRepository extends JpaRepository<InformacoesMatricula, Long> {

}
