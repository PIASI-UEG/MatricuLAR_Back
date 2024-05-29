package br.ueg.piasi.MatricuLAR.repository;

import br.ueg.piasi.MatricuLAR.model.ControlePeriodoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlePeriodoMatriculaRepository extends JpaRepository<ControlePeriodoMatricula, Long> {
}