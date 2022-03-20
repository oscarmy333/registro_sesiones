package com.myos.registro_sesiones.repositorio;

import com.myos.registro_sesiones.dominio.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u join FETCH u.roles where u.email=:p_email")
    Optional<Usuario> findByEmailWithRoles(@Param("p_email") String email);
}
