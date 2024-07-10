package br.ueg.piasi.MatricuLAR.repository;


import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long>, JpaSpecificationExecutor<Matricula> {
    Optional<List<Matricula>> findByTurma_Id(@NonNull Long id);

    @Query("select matricula from Matricula matricula left join fetch Turma turma on matricula.turma.id = turma.id where matricula.status = :status")
    Optional<List<Matricula>> findByStatusFetchTurma(@NonNull @Param("status") StatusMatricula status);

    @Query(value = "select matricula.* from matricula " +
            "left join turma on matricula.turma = turma.id" +
            " where matricula.status = :statusMatricula " +
            "limit :pageSize offset :pagina", nativeQuery = true)
    Optional<List<Matricula>> findByStatusFetchTurmaPage(@Param("pagina") int offset, @Param("pageSize") int pageSize, String statusMatricula);

    @Query(value = "select count(*) from matricula where matricula.status = :status", nativeQuery = true)
    Integer countAllWithStatus(@NonNull @Param("status") String status);

    long countByStatus(StatusMatricula status);

    @Query("select (count(m) > 0) from Matricula m where upper(m.pessoa.cpf) = upper(?1)")
    boolean existByAlunoCPF(@NonNull String cpf);

    Optional<Matricula> findByPessoa_CpfIgnoreCase(@NonNull String cpf);

    List<Matricula> findAllByStatusByStatus(@NonNull StatusMatricula status);
}
