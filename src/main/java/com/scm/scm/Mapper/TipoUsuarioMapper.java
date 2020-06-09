package com.scm.scm.Mapper;

import com.scm.scm.Model.TipoUsuario;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TipoUsuarioMapper implements RowMapper<TipoUsuario> {

    /*Metodo encargado de tener un mapeo de los datos que se encuentran en nuestro modelo asi como los que
     * pertenecen a la tabla tipoUsuario*/
    @Override
    public TipoUsuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        TipoUsuario tipoUsuario = new TipoUsuario();

        tipoUsuario.setIdTipoU(rs.getInt("idTipoU"));
        tipoUsuario.setNomTipoU(rs.getString("nomTipoU"));

        return tipoUsuario;
    }
}
