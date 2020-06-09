package com.scm.scm.Services.impl;

import com.scm.scm.Mapper.CompraMayorista2Mapper;
import com.scm.scm.Mapper.CompraMayoristaMapper;
import com.scm.scm.Model.CompraMayorista;
import com.scm.scm.Services.CompraMayoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

//Clase tipo service que nos permitira realizar nuestros metodos para registrar, cancelar y consultar las compras a mayoristas
@Service("compraMayoristaServiceImpl")
public class CompraMayoristaServiceImpl implements CompraMayoristaService {

    //Bean que nos permite tener acceso a las instrucciones que se le mandaran a nuestra base de datos
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Metodo para registrar compras a clientes mayoristas
    @Override
    public boolean realizarCompraMayorista(CompraMayorista compraMayorista) {
        try {
            String sql = String.format("INSERT into compramayorista (folioComp,fechaComp,cantidad,precio,subTotalComp,iva,totalComp,estadoComp," +
                            "Material_idMaterial,usuario_nomUsuario,mayorista_idMayorista) " +
                            "values('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    compraMayorista.getFolioComp(),compraMayorista.getFechaComp(),compraMayorista.getCantidad(),compraMayorista.getPrecio(),compraMayorista.getSubTotalComp(),compraMayorista.getIva(),compraMayorista.getTotalComp(),"Realizada",
                    compraMayorista.getIdMaterial(),"root",compraMayorista.getIdMayorista());
            jdbcTemplate.execute(sql);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return  false;
        }
    }

    @Override
    public void cancelarCompraMayorista(String folio) {
        try {
            String sql = String.format("UPDATE CompraMayorista SET EstadoComp = 'Cancelado' WHERE  folioComp= '" + folio + "'");
            jdbcTemplate.execute(sql);
        }catch(Exception ex) {

        }
    }

    @Override
    public List<CompraMayorista> consultarCompraMayorista() {
        return jdbcTemplate.query("SELECT folioComp,fechaComp,cantidad,precio,subTotalComp, estadoComp,iva,totalComp, \n" +
                "nomMaterial, nomEmpresa,nomUsuario, idMaterial, idMayorista, nomUsuario  \n" +
                "FROM compramayorista  \n" +
                "JOIN material ON (compramayorista.Material_idMaterial = idMaterial)\n" +
                "JOIN mayorista ON (compramayorista.mayorista_idMayorista = idMayorista) \n" +
                "JOIN usuario ON (compramayorista.usuario_nomUsuario = nomUsuario)", new CompraMayoristaMapper());
    }

    @Override
    public CompraMayorista findByFolio(String folio) {
        Object[] params = new Object[] {folio};
        return jdbcTemplate.queryForObject("Select * from CompraMayorista where folioComp=?",params,new CompraMayorista2Mapper());
    }
}
