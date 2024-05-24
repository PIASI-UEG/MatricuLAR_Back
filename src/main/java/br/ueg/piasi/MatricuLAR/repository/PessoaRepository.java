package br.ueg.piasi.MatricuLAR.repository;



import br.ueg.piasi.MatricuLAR.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String>, JpaSpecificationExecutor<Pessoa> {

}
