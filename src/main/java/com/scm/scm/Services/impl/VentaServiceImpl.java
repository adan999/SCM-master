package com.scm.scm.Services.impl;


import com.scm.scm.Mapper.*;
import com.scm.scm.Model.CompraMinorista;
import com.scm.scm.Model.Material;
import com.scm.scm.Model.Venta;
import com.scm.scm.Services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

@Service("VentaServiceImpl")
public class VentaServiceImpl implements VentaService {

    //Bean que nos permite tener acceso a las instrucciones que se le mandaran a nuestra base de datos
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Metodo para registrar ventas
    @Override
    public boolean realizarVenta(Venta object) {
        try {

            String sql = String.format("insert into Venta (FolioVenta, FechaVenta, SubTotalVenta, Iva, TotalVenta, EstadoVenta, MaterialCategoria_idMaterialCat, Usuario_nomUsuario, Mayorista_idMayorista, Cantidad, Precio, Unidad) " +
                            "values('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    object.getFolioVenta(), object.getFechaVenta(), object.getSubTotalVenta(), object.getIva(), object.getTotalVenta(), object.getEstadoVenta() ,object.getIdMaterialCat(), object.getNomUsuario(), object.getIdMayorista(),object.getCantidad(), object.getPrecio(), object.getUnidad());
            jdbcTemplate.execute(sql);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return  false;
        }

    }

    //Metodo para Obtener todos los datos pertenecientes a la tabla Compras de minoristas y mandarlos a una lista de tipo CompraMinorista
    @Override
    public List<Venta> consultarVenta() {
        return jdbcTemplate.query("Select folioVenta, fechaVenta, nomEmpresa," +
                " nomCategoria, subTotalVenta, venta.cantidad, precio, iva, unidad, totalVenta, estadoVenta, idMaterialCat, idMayorista, nomUsuario from venta join TipoMaterial " +
                " ON (venta.MaterialCategoria_idMaterialCat = idMaterialCat) join usuario ON (venta.Usuario_nomUsuario = nomUsuario) join Mayorista ON(venta.mayorista_idMayorista = idMayorista)", new VentaMapper());
    }

    //Metodo que recibe un folio string para modificar el estado de la compra
    @Override
    public void cancelarVenta(String folioVenta)
    {
        try {
            String sql = String.format("UPDATE Venta SET EstadoVenta = 'Cancelado' WHERE  folioVenta= '" + folioVenta + "'");
            jdbcTemplate.execute(sql);
        }catch(Exception ex) {

        }
    }

    //Metodo para Obtener todos los datos pertenecientes a la tabla compra en donde el id sea igual al proporcionado y mandarlos a una lista de tipo compra
    @Override
    public Venta findByFolio(String folioVenta) {
        Object[] params = new Object[] {folioVenta};
        return jdbcTemplate.queryForObject("Select * from Venta where folioVenta=?",params,new Venta2Mapper());
    }

    //Metodo para contar la cantidad de materiales que sean mayores a la cantidad que se restara
    @Override
    public List<Material> contarMateriales(int idCat){
        return jdbcTemplate.query("Select idMaterial, codigoMaterial, nomMaterial, material.unidadMedida," +
                "cantidadActual, idMaterialCat, nomCategoria from Material join TipoMaterial" +
                " ON (material.MaterialCategoria_idMaterialCat = idMaterialCat) WHERE idMaterialCat ="+ idCat, new MaterialMapper());

    }
}
