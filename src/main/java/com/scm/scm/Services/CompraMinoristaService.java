package com.scm.scm.Services;

import com.scm.scm.Model.CompraMinorista;
import org.springframework.stereotype.Service;

import java.util.List;

//Interface donde se declararan los metodos para manipular los datos de ComprasMinoristas
@Service("compraMinoristaService")
public interface CompraMinoristaService {

    public boolean realizarCompraMinorista(CompraMinorista compraMinorista);

    public void cancelarCompraMinorista(String folioSegura);

    public List<CompraMinorista> consultarCompraMinorista();

    public CompraMinorista findByFolio(String folio);
}
