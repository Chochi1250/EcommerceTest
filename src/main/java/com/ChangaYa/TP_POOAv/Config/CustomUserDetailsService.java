package com.ChangaYa.TP_POOAv.Config;

import com.ChangaYa.TP_POOAv.model.*;
import com.ChangaYa.TP_POOAv.repository.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;


    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
    Usuario user = usuarioRepository.findByNombreUsuario(nombreUsuario);
    if (user != null) {
        String roleWithPrefix = user.getRole().startsWith("ROLE_") ? user.getRole() : "ROLE_" + user.getRole();
        
        return new User(
                user.getNombreUsuario(),
                user.getContrasena(),
                Collections.singletonList(new SimpleGrantedAuthority(roleWithPrefix))
        );
    } else {
        throw new UsernameNotFoundException("Invalid username or password");
    }
}

}

