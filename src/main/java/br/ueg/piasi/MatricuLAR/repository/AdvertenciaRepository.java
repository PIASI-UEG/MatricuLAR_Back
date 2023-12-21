package br.ueg.piasi.MatricuLAR.repository;



import br.ueg.piasi.MatricuLAR.model.Advertencia;
import br.ueg.piasi.MatricuLAR.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertenciaRepository extends JpaRepository<Advertencia, Long> {

}
