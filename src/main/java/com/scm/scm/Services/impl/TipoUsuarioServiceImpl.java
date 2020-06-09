package com.scm.scm.Services.impl;

import com.scm.scm.Mapper.TipoUsuarioMapper;
import com.scm.scm.Model.TipoUsuario;
import com.scm.scm.Services.TipoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tipoUsuarioServiceImpl")
public class TipoUsuarioServiceImpl implements TipoUsuarioService {


    //Bean que nos permite tener acceso a las instrucciones que se le mandaran a nuestra base de datos
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Metodo para Obtener todos los datos pertenecientes a la tabla tipoUsuarios y mandarlos a una lista de tipo TipoUsuario
    @Override
    public List<TipoUsuario> consultarTipoUsuario() {
        return jdbcTemplate.query("Select * from tipousuario",new TipoUsuarioMapper());    }

    //Metodo para Obtener todos los datos pertenecientes a la tabla tipoUsuario en donde el id sea igual al proporcionado y mandarlos a una lista de tipo TipoUsuario
    @Override
    public TipoUsuario findById(int Id) {
        Object[] params = new Object[] {Id};
        return jdbcTemplate.queryForObject("Select * from usuario where idTipoU=?",params,new TipoUsuarioMapper());    }
}
