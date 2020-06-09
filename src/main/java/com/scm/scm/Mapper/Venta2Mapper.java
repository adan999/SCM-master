package com.scm.scm.Mapper;

import com.scm.scm.Model.Venta;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Venta2Mapper implements RowMapper<Venta> {

    /*Metodo encargado de tener un mapeo de los datos que se encuentran en nuestro modelo asi como los que
     * pertenecen a la tabla CompraMinorista*/
    @Override
    public Venta mapRow(ResultSet rs, int i) throws SQLException {
        Venta venta = new Venta();


        venta.setFolioVenta(rs.getString("folioVenta"));
        venta.setFechaVenta(rs.getString("fechaVenta"));
        //venta.setNomEmpresa(rs.getString("nomEmpresa"));
        venta.setCantidad(rs.getDouble("cantidad"));
        venta.setPrecio(rs.getDouble("precio"));
        venta.setIva(rs.getInt("iva"));
        venta.setSubTotalVenta(rs.getDouble("subTotalVenta"));
        venta.setTotalVenta(rs.getDouble("totalVenta"));
        //venta.setNomCategoria(rs.getString("nomCategoria"));
        venta.setEstadoVenta(rs.getString("estadoVenta"));
        venta.setIdMaterialCat(rs.getInt("MaterialCategoria_idMaterialCat"));
        venta.setIdMayorista(rs.getInt("mayorista_idMayorista"));
        venta.setNomUsuario(rs.getString("usuario_nomUsuario"));
        venta.setUnidad(rs.getString("unidad"));

        return venta;
    }
}
