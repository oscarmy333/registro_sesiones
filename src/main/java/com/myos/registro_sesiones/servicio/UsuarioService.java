package com.myos.registro_sesiones.servicio;

import com.myos.registro_sesiones.dominio.Usuario;
import com.myos.registro_sesiones.exception.NotFoundException;
import com.myos.registro_sesiones.repositorio.UsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UsuarioService {

    private final UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }


    public Usuario readByEmailWithRoles(String email) throws NotFoundException {
        Optional<Usuario> optionalUser = this.repository.findByEmailWithRoles(email);

        if (optionalUser.isPresent()) {
            // Yes, I know about inlining, but for important objects,
            // I prefer to create separate variable, for debug purposes
            Usuario user = optionalUser.get();
            return user;
        }

        throw new NotFoundException(String.format("There is no user with email = %s", email));
    }
}
