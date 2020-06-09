package com.scm.scm.Services.impl;

import com.scm.scm.Mapper.MaterialMapper;
import com.scm.scm.Model.Material;
import com.scm.scm.Services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MaterialServiceImpl")
public class MaterialServiceImpl implements MaterialService {

    //Bean que nos permite tener acceso a las instrucciones que se le mandaran a nuestra base de datos
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean realizarMaterial(Material object) {
        try {
            String sql = String.format("insert into Material (CodigoMaterial, NomMaterial, UnidadMedida, CantidadActual, MaterialCategoria_idMaterialCat, usuario_nomUsuario) " +
                            "values('%s', '%s', '%s', '%s', '%s', 'root3')",
                     object.getCodigoMaterial(), object.getNomMaterial(), object.getUnidadMedida(), object.getCantidadActual(), object.getIdMaterialCat());
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Metodo para Obtener todos los datos pertenecientes a la tabla tipoMaterial y mandarlos a una lista de tipo TipoMaterial
    @Override
    public List<Material> consultarMaterial() {
        return jdbcTemplate.query("Select idMaterial, codigoMaterial, nomMaterial, material.unidadMedida," +
                "cantidadActual, idMaterialCat, nomCategoria from Material join TipoMaterial" +
                " ON (material.MaterialCategoria_idMaterialCat = idMaterialCat)", new MaterialMapper());
    }


    //Metodo para Obtener todos los datos pertenecientes a la tabla tipoMaterial en donde el id sea igual al proporcionado y mandarlos a una lista de tipo TipoMaterial
    @Override
    public Material findById(int Id) {
        Object[] params = new Object[] {Id};
        return jdbcTemplate.queryForObject("Select idMaterial, codigoMaterial, nomMaterial, material.unidadMedida," +
                " cantidadActual, idMaterialCat, nomCategoria from Material join TipoMaterial" +
                " ON (material.MaterialCategoria_idMaterialCat = idMaterialCat) WHERE idMaterial = ?",params,new MaterialMapper());
    }


    @Override
    public boolean modificarMaterial(String clave, String nombre) {
        try {
            String sql = String.format("UPDATE Material SET NomMaterial = '" + nombre + "' WHERE codigoMaterial = '" + clave + "'");
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean entradasMaterial(int id, double cantidad){
        try{
            String sql = String.format("UPDATE Material SET CantidadActual = '" + cantidad + "' WHERE idMaterial = '" + id + "'");
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }
}
