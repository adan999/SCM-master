package com.scm.scm.Mapper;

import com.scm.scm.Model.Usuario;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioMapper implements RowMapper<Usuario> {

    @Override
    public Usuario mapRow(ResultSet rs, int i) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setNomUsuario(rs.getString("nomUsuario"));
        usuario.setContrasena(rs.getString("contrasena"));
        usuario.setIdTipoU(rs.getInt("TipoUsuario_idTipoU"));
        usuario.setNomTipoU(rs.getString("nomTipoU"));

        return usuario;
    }

}
