package com.scm.scm.Services.impl;

import com.scm.scm.Mapper.TipoMaterialMapper;
import com.scm.scm.Model.TipoMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.scm.scm.Services.TipoMaterialService;

import java.util.List;

@Service("TipoMaterialServiceImpl")
public class TipoMaterialServiceImpl implements TipoMaterialService{


    //Bean que nos permite tener acceso a las instrucciones que se le mandaran a nuestra base de datos
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean realizarTipoMaterial(TipoMaterial object) {
        try {
            String sql = String.format("insert into TipoMaterial (CodigoTipo, NomCategoria, UnidadMedida,  Cantidad, usuario_nomUsuario) " +
                            "values('%s', '%s', '%s', '%s', 'root3')",
                    object.getCodigoTipo(), object.getNomCategoria(), object.getUnidadMedida(), object.getCantidad());
            jdbcTemplate.execute(sql);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return  false;
        }
    }

    //Metodo para Obtener todos los datos pertenecientes a la tabla tipoMaterial y mandarlos a una lista de tipo TipoMaterial
    @Override
    public List<TipoMaterial> consultarTipoMaterial() {
        return jdbcTemplate.query("Select * from TipoMaterial",new TipoMaterialMapper());
    }



    //Metodo para Obtener todos los datos pertenecientes a la tabla tipoMaterial en donde el id sea igual al proporcionado y mandarlos a una lista de tipo TipoMaterial
    @Override
    public TipoMaterial findById(int id) {
        Object[] params = new Object[] {id};
        return jdbcTemplate.queryForObject("Select * from TipoMaterial where idMaterialCat=?",params,new TipoMaterialMapper());
    }


    @Override
    public boolean modificarTipoMaterial(String clave, String nombre){
        try {
            String sql = String.format("UPDATE TipoMaterial SET NomCategoria = '" + nombre + "' WHERE codigoTipo = '" + clave + "'");
            jdbcTemplate.execute(sql);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return  false;
        }
    }

    @Override
    public boolean modificarCantidadTipo(int id, double cantidad){

        try {
            String sql = String.format("UPDATE TipoMaterial SET Cantidad = '" + cantidad + "' WHERE idMaterialCat = '" + id + "'");
            jdbcTemplate.execute(sql);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return  false;
        }
    }
}
