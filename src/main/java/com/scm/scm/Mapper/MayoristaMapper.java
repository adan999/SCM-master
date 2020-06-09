package com.scm.scm.Mapper;

import com.scm.scm.Model.Mayorista;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MayoristaMapper implements RowMapper<Mayorista> {

    /*Metodo encargado de tener un mapeo de los datos que se encuentran en nuestro modelo asi como los que
    * pertenecen a la tabla Mayorista*/
    @Override
    public Mayorista mapRow(ResultSet rs, int i) throws SQLException {
        Mayorista mayorista = new Mayorista();

        mayorista.setIdMayorista(rs.getInt("idMayorista"));
        mayorista.setRfc(rs.getString("rfc"));
        mayorista.setNomEmpresa(rs.getString("nomEmpresa"));
        mayorista.setDir(rs.getString("dir"));
        mayorista.setNumTel(rs.getString("numTel"));

        return mayorista;
    }
}
