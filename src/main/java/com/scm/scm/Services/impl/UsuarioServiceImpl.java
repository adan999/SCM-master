package com.scm.scm.Services.impl;

import com.scm.scm.Mapper.UsuarioMapper;
import com.scm.scm.Model.Usuario;
import com.scm.scm.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("usuarioServiceImpl")
public class UsuarioServiceImpl implements UsuarioService {

    //Bean que nos permite tener acceso a las instrucciones que se le mandaran a nuestra base de datos
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        try {
            String sql = String.format("INSERT into usuario (nomUsuario,contrasena,TipoUsuario_idTipoU) " +
                            "values('%s', '%s', '%s')",
                    usuario.getNomUsuario(), bCryptPasswordEncoder.encode(usuario.getContrasena()), usuario.getIdTipoU());
            jdbcTemplate.execute(sql);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return  false;
        }
    }

    @Override
    public boolean modificarUsuario(Usuario usuario) {
        try {
            String sql = String.format("UPDATE usuario SET contrasena = '" +usuario.getContrasena()+"', TipoUsuario_idTipoU = '"+usuario.getIdTipoU()+"' WHERE nomUsuario = '" + usuario.getNomUsuario() + "'");
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Usuario> consultarUsuario() {
        return jdbcTemplate.query("SELECT nomUsuario, contrasena, TipoUsuario_idTipoU, tipousuario.nomTipoU" +
                " FROM usuario JOIN tipousuario ON TipoUsuario_idTipoU = tipousuario.idTipoU;", new UsuarioMapper());    }

    @Override
    public Usuario findByUsername(String username) {
        //Object[] params = new Object[] {username};
        return jdbcTemplate.queryForObject("SELECT nomUsuario, contrasena, TipoUsuario_idTipoU, " +
                "tipousuario.nomTipoU FROM usuario JOIN tipousuario " +
                "ON (TipoUsuario_idTipoU = tipousuario.idTipoU) " +
                "WHERE nomUsuario = ?",new Object[] {username},new UsuarioMapper());
    }
}
