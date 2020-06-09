package com.scm.scm.Mapper;

import com.scm.scm.Model.Material;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialMapper implements RowMapper<Material> {

    @Override
    public Material mapRow(ResultSet rs, int i) throws SQLException {
        Material material = new Material();

        material.setIdMaterial(rs.getInt("idMaterial"));
        material.setCodigoMaterial(rs.getString("codigoMaterial"));
        material.setNomMaterial(rs.getString("nomMaterial"));
        material.setUnidadMedida(rs.getString("material.unidadMedida"));
        material.setCantidadActual(rs.getDouble("cantidadActual"));
        material.setIdMaterialCat(rs.getInt("idMaterialCat"));
        material.setNomCategoria(rs.getString("nomCategoria"));
        //material.setIdUsuario(rs.getInt("idUsuario"));


        return material;
    }
}
