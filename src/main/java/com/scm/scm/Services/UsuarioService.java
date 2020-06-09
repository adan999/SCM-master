package com.scm.scm.Services;

import com.scm.scm.Model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

//Interface donde se declararan los metodos para manipular los datos de Usuarios
@Service("usuarioService")
public interface UsuarioService {

    public boolean registrarUsuario(Usuario usuario);

    public boolean modificarUsuario(Usuario usuario);

    public List<Usuario> consultarUsuario();

    public Usuario findByUsername(String username);
}
