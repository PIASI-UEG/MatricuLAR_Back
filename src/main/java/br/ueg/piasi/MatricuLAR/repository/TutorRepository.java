package br.ueg.piasi.MatricuLAR.repository;


import br.ueg.piasi.MatricuLAR.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, String>, JpaSpecificationExecutor<Tutor> {

}
