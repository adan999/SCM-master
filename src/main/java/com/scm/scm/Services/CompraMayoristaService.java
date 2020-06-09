package com.scm.scm.Services;

import com.scm.scm.Model.CompraMayorista;
import org.springframework.stereotype.Service;

import java.util.List;

//Interface donde se declararan los metodos para manipular los datos de ComprasMayoristas
@Service("compraMayoristaService")
public interface CompraMayoristaService {
    public boolean realizarCompraMayorista(CompraMayorista compraMayorista);

    public void cancelarCompraMayorista(String folio);

    public List<CompraMayorista> consultarCompraMayorista();

    public CompraMayorista findByFolio(String folio);
}
