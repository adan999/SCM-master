package com.scm.scm.Services.impl;

import com.scm.scm.Mapper.MayoristaMapper;
import com.scm.scm.Model.Mayorista;
import com.scm.scm.Services.MayoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

//Clase tipo service que nos permitira realizar nuestros metodos para registrar, modificar y consultar los mayoristas
@Service("mayoristaServiceImpl")
public class MayoristaServiceImpl implements MayoristaService {

    //Bean que nos permite tener acceso a las instrucciones que se le mandaran a nuestra base de datos
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean registrarMayorista(Mayorista mayorista) {
        try {
            String sql = String.format("insert into mayorista (rfc, nomEmpresa, dir, numTel,usuario_nomUsuario) " +
                            "values('%s', '%s', '%s', '%s','root')",
                    mayorista.getRfc(), mayorista.getNomEmpresa(), mayorista.getDir(), mayorista.getNumTel());
            jdbcTemplate.execute(sql);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return  false;
        }
    }

    @Override
    public boolean modificarMayorista(String rfc, String nombre, String direccion, String numero) {
        try {
            String sql = String.format("UPDATE mayorista SET rfc = '" + rfc + "'," +
                    "nomEmpresa = '" +nombre+"', dir = '" +direccion+
                    "', numTel = '"+numero+"' WHERE rfc = '" + rfc + "'");
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Mayorista> consultarMayorista() {
        return jdbcTemplate.query("Select * from mayorista",new MayoristaMapper());
    }

    @Override
    public Mayorista findById(int Id) {
        Object[] params = new Object[] {Id};
        return jdbcTemplate.queryForObject("Select * from mayorista where idMayorista=?",params,new MayoristaMapper());
    }
}
