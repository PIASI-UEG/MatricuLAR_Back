package br.ueg.piasi.MatricuLAR.repository;


import br.ueg.piasi.MatricuLAR.model.MatriculaTurma;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkMatriculaTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaTurmaRepository extends JpaRepository<MatriculaTurma, PkMatriculaTurma>,
        JpaSpecificationExecutor<MatriculaTurma> {

}
