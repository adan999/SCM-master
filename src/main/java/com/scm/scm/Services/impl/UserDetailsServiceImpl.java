package com.scm.scm.Services.impl;

import com.scm.scm.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

//Clase utilizada para declarar el nombre de los permisos que tienen los usuarios para acceder al sistema
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier("usuarioServiceImpl")
    private UsuarioServiceImpl usuarioServiceImpl;

    //Metodo para asignar el nombre de los permisos que existiran en el sistema
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioServiceImpl.findByUsername(username);
        if(usuario==null) throw new UsernameNotFoundException(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(usuario.getNomTipoU()));

        return new org.springframework.security.core.userdetails.User(usuario.getNomUsuario(),usuario.getContrasena(), true,true, true,true,grantedAuthorities);
    }
}
