package com.scm.scm.Services;

import com.scm.scm.Model.Mayorista;
import org.springframework.stereotype.Service;

import java.util.List;

//Interface donde se declararan los metodos para manipular los datos de Mayoristas
@Service("mayoristaService")
public interface MayoristaService {

    public boolean registrarMayorista(Mayorista mayorista);

    public boolean modificarMayorista(String rfc, String nombre, String direccion, String numero);

    public List<Mayorista> consultarMayorista();

    public Mayorista findById(int Id);
}
