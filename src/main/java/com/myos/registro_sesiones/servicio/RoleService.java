package com.myos.registro_sesiones.servicio;

import com.myos.registro_sesiones.dominio.Role;
import com.myos.registro_sesiones.exception.NotFoundException;
import com.myos.registro_sesiones.repositorio.RoleRepository;
import com.myos.registro_sesiones.util.constants.RoleName;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {

    private final RoleRepository repository;
    private final Map<RoleName, Role> roleByName;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
        this.roleByName = new HashMap<>();
    }

    public void fillDatabaseWithRoles() {
        for (RoleName n : RoleName.values()) {
            Optional<Role> optionalRole = this.repository.findByName(n);
            if (optionalRole.isPresent()) {
                this.roleByName.put(n, optionalRole.get());
            } else {
                Role role = new Role(n);
                role = this.repository.save(role);
                this.roleByName.put(n, role);
            }
        }
    }

    public Role getRole(RoleName name) throws NotFoundException {
        return this.roleByName.get(name);
    }

}
