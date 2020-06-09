package com.scm.scm.Mapper;

import com.scm.scm.Model.CompraMayorista;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompraMayoristaMapper implements RowMapper<CompraMayorista> {
    @Override
    public CompraMayorista mapRow(ResultSet rs, int rowNum) throws SQLException {
        CompraMayorista compraMayorista = new CompraMayorista();

        compraMayorista.setFolioComp(rs.getString("folioComp"));
        compraMayorista.setFechaComp(rs.getString("fechaComp"));
        compraMayorista.setCantidad(rs.getDouble("cantidad"));
        compraMayorista.setPrecio(rs.getDouble("precio"));
        compraMayorista.setSubTotalComp(rs.getDouble("subTotalComp"));
        compraMayorista.setIva(rs.getInt("iva"));
        compraMayorista.setEstadoComp(rs.getString("estadoComp"));
        compraMayorista.setTotalComp(rs.getDouble("totalComp"));
        compraMayorista.setIdMaterial(rs.getInt("idMaterial"));
        compraMayorista.setIdMayorista(rs.getInt("idMayorista"));
        compraMayorista.setNomUsuario(rs.getString("nomUsuario"));
        compraMayorista.setNomEmpresa(rs.getString("nomEmpresa"));
        compraMayorista.setNomMaterial(rs.getString("nomMaterial"));

        return compraMayorista;
    }
}
