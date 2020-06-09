package com.scm.scm.Mapper;

import com.scm.scm.Model.TipoMaterial;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoMaterialMapper implements RowMapper<TipoMaterial> {

    /*Metodo encargado de tener un mapeo de los datos que se encuentran en nuestro modelo asi como los que
     * pertenecen a la tabla tipoMaterial*/
    @Override
    public TipoMaterial mapRow(ResultSet rs, int i) throws SQLException {
        TipoMaterial tipoMaterial = new TipoMaterial();

        tipoMaterial.setIdMaterialCat(rs.getInt("idMaterialCat"));
        tipoMaterial.setCodigoTipo(rs.getString("codigoTipo"));
        tipoMaterial.setNomCategoria(rs.getString("NomCategoria"));
        tipoMaterial.setUnidadMedida(rs.getString("UnidadMedida"));
        tipoMaterial.setCantidad(rs.getDouble("Cantidad"));


        return tipoMaterial;
    }
}
