package br.ueg.piasi.MatricuLAR.repository;


import br.ueg.piasi.MatricuLAR.model.MatriculaNecessidade;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkMatriculaNecessidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaNecessidadeRepository extends JpaRepository<MatriculaNecessidade, PkMatriculaNecessidade>,
        JpaSpecificationExecutor<MatriculaNecessidade> {

}
