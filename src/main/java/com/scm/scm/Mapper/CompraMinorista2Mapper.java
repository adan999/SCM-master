package com.scm.scm.Mapper;

import com.scm.scm.Model.CompraMinorista;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CompraMinorista2Mapper  implements RowMapper<CompraMinorista> {


    @Override
    public CompraMinorista mapRow(ResultSet rs, int i) throws SQLException {
        CompraMinorista compraMinorista = new CompraMinorista();


        compraMinorista.setFolioSegura(rs.getString("folioSegura"));
        compraMinorista.setFechaComp(rs.getString("fechaComp"));
        compraMinorista.setNomCliente(rs.getString("nomCliente"));
        compraMinorista.setDirCliente(rs.getString("dirCliente"));
        compraMinorista.setCantidad(rs.getDouble("cantidad"));
        compraMinorista.setPrecio(rs.getDouble("precio"));
        compraMinorista.setIva(rs.getInt("iva"));
        compraMinorista.setSubTotalComp(rs.getDouble("subTotalComp"));
        compraMinorista.setTotalComp(rs.getDouble("totalComp"));
        compraMinorista.setEstadoComp(rs.getString("estadoComp"));
        compraMinorista.setIdMaterial(rs.getInt("Material_idMaterial"));
        compraMinorista.setNomUsuario(rs.getString("usuario_nomUsuario"));

        return compraMinorista;
    }
}
