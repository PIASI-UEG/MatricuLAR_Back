package br.ueg.piasi.MatricuLAR.repository;


import br.ueg.piasi.MatricuLAR.dto.MatriculaListagemDTO;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long>, JpaSpecificationExecutor<Matricula> {

    @Query("select matricula from Matricula matricula left join fetch Turma turma on matricula.turma.id = turma.id where matricula.status = :status")
    Optional<List<Matricula>> findByStatus(@NonNull @Param("status") StatusMatricula status);


}
