package com.myos.registro_sesiones.servicio;

import com.myos.registro_sesiones.dominio.Usuario;
import com.myos.registro_sesiones.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioService userService;

    @Autowired
    public UsuarioDetailsServiceImpl(UsuarioService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Usuario user = this.userService.readByEmailWithRoles(email);
            return user;
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
