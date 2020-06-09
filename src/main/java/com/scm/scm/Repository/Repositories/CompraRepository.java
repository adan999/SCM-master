package com.scm.scm.Repository.Repositories;

import com.scm.scm.Configuration.Conexion;
import com.scm.scm.Mapper.CompraMapper;
import com.scm.scm.Model.Compra;
import com.scm.scm.Repository.CompraRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

@Repository
public class CompraRepository implements CompraRep {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(Compra object) {
        try {
            String sql = String.format("insert into Compra (NombreProd, Cantidad, Estado, Fecha, Precio, Total, Unidad) " +
                    "values('%s', '%s', 'Realizada', '%s', '%s', '%s', '%s')",
                    object.getNombreProd(),object.getCantidad(), object.getFecha(), object.getPrecio(), object.getTotal(), object.getUnidad());
                jdbcTemplate.execute(sql);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return  false;
        }
    }

    @Override
    public boolean update(Compra object) {
        return false;
    }


    @Override
    public List<Compra> findAll() {
        return jdbcTemplate.query("Select * from Compra",new CompraMapper());
    }

    @Override
    public Compra findById(int Id) {
        Object[] params = new Object[] {Id};
        return jdbcTemplate.queryForObject("Select * from Compra where idCompras=?",params,new CompraMapper());
    }
}
