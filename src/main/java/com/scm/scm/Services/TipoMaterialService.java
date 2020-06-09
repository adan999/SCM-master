package com.scm.scm.Services;

import com.scm.scm.Model.TipoMaterial;
import org.springframework.stereotype.Service;

import java.util.List;

//Interface de Repositorio Generico,
// ya que en la mayoria de los modelos se utilizaran los mismo metodos
@Service("tipoMaterialService")
public interface TipoMaterialService {

    public List<TipoMaterial> consultarTipoMaterial();

    public TipoMaterial findById(int id);

    public boolean realizarTipoMaterial(TipoMaterial tipoMaterial);

    public boolean modificarTipoMaterial(String clave, String nombre);

    public boolean modificarCantidadTipo(int id, double cantidad);

}
