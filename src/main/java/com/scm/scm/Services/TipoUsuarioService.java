package com.scm.scm.Services;

import com.scm.scm.Model.TipoUsuario;
import org.springframework.stereotype.Service;

import java.util.List;

//Interface donde se declararan los metodos para manipular los datos de Usuarios
@Service("tipoUsuarioService")
public interface TipoUsuarioService {

    public List<TipoUsuario> consultarTipoUsuario();

    public TipoUsuario findById(int Id);
}
