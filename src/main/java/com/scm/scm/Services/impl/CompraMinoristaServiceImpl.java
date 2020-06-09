package com.scm.scm.Services.impl;

import com.scm.scm.Mapper.CompraMinorista2Mapper;
import com.scm.scm.Mapper.CompraMinoristaMapper;
import com.scm.scm.Model.CompraMinorista;
import com.scm.scm.Services.CompraMinoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

//Clase tipo service que nos permitira realizar nuestros metodos para registrar, cancelar y consultar las compras a minoristas
@Service("CompraMinoristaServiceImpl")
public class CompraMinoristaServiceImpl implements CompraMinoristaService {


    //Bean que nos permite tener acceso a las instrucciones que se le mandaran a nuestra base de datos
    @Autowired
    private JdbcTemplate jdbcTemplate;



    //object.getCantidad()
    //Metodo para registrar compras a clientes minoristas
    @Override
    public boolean realizarCompraMinorista(CompraMinorista object) {
        try {
            System.out.println(object.getIdMaterial());
            String sql = String.format("insert into CompraMinorista (FolioSegura, FechaComp, NomCliente, DirCliente, Cantidad, Precio, SubTotalComp, Iva, TotalComp, EstadoComp, Material_idMaterial, usuario_nomUsuario) " +
                            "values('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', 'root')",
                    object.getFolioSegura(), object.getFechaComp(), object.getNomCliente(), object.getDirCliente(), object.getCantidad(), object.getPrecio() ,object.getSubTotalComp(), object.getIva(), object.getTotalComp(),object.getEstadoComp(), object.getIdMaterial());
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
    public List<CompraMinorista> consultarCompraMinorista() {
        return jdbcTemplate.query("Select folioSegura, fechaComp, nomCliente," +
                " dirCliente, subTotalComp, cantidad, precio, iva, totalComp, estadoComp, idMaterial, nomMaterial, nomUsuario from CompraMinorista join Material " +
                " ON (compraMinorista.Material_idMaterial = idMaterial) join usuario ON(compraMinorista.usuario_nomUsuario = nomUsuario)", new CompraMinoristaMapper());
    }

    //Metodo que recibe un folio string para modificar el estado de la compra
    @Override
    public void cancelarCompraMinorista(String folio)
    {
        try {
            String sql = String.format("UPDATE CompraMinorista SET EstadoComp = 'Cancelado' WHERE  folioSegura= '" + folio + "'");
            jdbcTemplate.execute(sql);
        }catch(Exception ex) {

        }
    }

    //Metodo para Obtener todos los datos pertenecientes a la tabla compra en donde el id sea igual al proporcionado y mandarlos a una lista de tipo compra
    @Override
    public CompraMinorista findByFolio(String folio) {
        Object[] params = new Object[] {folio};
        return jdbcTemplate.queryForObject("Select * from CompraMinorista where folioSegura=?",params,new CompraMinorista2Mapper());
    }
}
