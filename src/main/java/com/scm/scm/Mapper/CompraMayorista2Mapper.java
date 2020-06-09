package com.scm.scm.Mapper;

import com.scm.scm.Model.CompraMayorista;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompraMayorista2Mapper implements RowMapper<CompraMayorista> {

    @Override
    public CompraMayorista mapRow(ResultSet rs, int i) throws SQLException {
        CompraMayorista compraMayorista = new CompraMayorista();

        compraMayorista.setFolioComp(rs.getString("folioComp"));
        compraMayorista.setFechaComp(rs.getString("fechaComp"));
        compraMayorista.setCantidad(rs.getDouble("cantidad"));
        compraMayorista.setPrecio(rs.getDouble("precio"));
        compraMayorista.setSubTotalComp(rs.getDouble("subTotalComp"));
        compraMayorista.setIva(rs.getInt("iva"));
        compraMayorista.setEstadoComp(rs.getString("estadoComp"));
        compraMayorista.setTotalComp(rs.getDouble("totalComp"));
        compraMayorista.setIdMaterial(rs.getInt("Material_idMaterial"));
        compraMayorista.setIdMayorista(rs.getInt("Mayorista_idMayorista"));
        compraMayorista.setNomUsuario(rs.getString("usuario_nomUsuario"));

        return compraMayorista;
    }
}
