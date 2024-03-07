package br.ueg.piasi.MatricuLAR.repository;


import br.ueg.piasi.MatricuLAR.model.NecessidadeEspecial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NecessidadeEspecialRepository extends JpaRepository<NecessidadeEspecial, Long>, JpaSpecificationExecutor<NecessidadeEspecial> {

}
