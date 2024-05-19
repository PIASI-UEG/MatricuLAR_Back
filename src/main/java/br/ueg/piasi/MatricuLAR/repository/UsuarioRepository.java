package br.ueg.piasi.MatricuLAR.repository;


import br.ueg.piasi.MatricuLAR.enums.Cargo;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    @Query(value = "select * from usuario limit :pageSize offset :pagina", nativeQuery = true)
    List<Usuario> findUsuariosWithPagination(@Param("pagina") int offset, @Param("pageSize") int pageSize);

    @Query(value = "select count(*) from usuario ", nativeQuery = true)
    Integer countAll();
    Optional<Usuario> findUsuarioByPessoaCpf(String  pessoaCpf);

    Optional<Usuario> findUsuarioByEmail(String email);

    Usuario findByCargo(Cargo cargo);
}
