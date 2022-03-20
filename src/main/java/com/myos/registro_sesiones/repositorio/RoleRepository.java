package com.myos.registro_sesiones.repositorio;

import com.myos.registro_sesiones.dominio.Role;
import com.myos.registro_sesiones.util.constants.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
