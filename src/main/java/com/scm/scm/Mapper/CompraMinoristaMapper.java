package com.scm.scm.Mapper;

import com.scm.scm.Model.CompraMinorista;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompraMinoristaMapper implements RowMapper<CompraMinorista> {

    /*Metodo encargado de tener un mapeo de los datos que se encuentran en nuestro modelo asi como los que
     * pertenecen a la tabla CompraMinorista*/
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
        compraMinorista.setNomMaterial(rs.getString("nomMaterial"));
        compraMinorista.setEstadoComp(rs.getString("estadoComp"));
        compraMinorista.setIdMaterial(rs.getInt("idMaterial"));
        compraMinorista.setNomUsuario(rs.getString("nomUsuario"));

        return compraMinorista;
    }
}
