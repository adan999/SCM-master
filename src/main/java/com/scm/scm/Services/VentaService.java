package com.scm.scm.Services;


import com.scm.scm.Model.CompraMinorista;
import com.scm.scm.Model.Material;
import com.scm.scm.Model.Venta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ventaService")
public interface VentaService {

    public boolean realizarVenta(Venta venta);

    public void cancelarVenta(String folioVenta);

    public List<Venta> consultarVenta();

    public Venta findByFolio(String folioVenta);

    public List<Material> contarMateriales(int idCat);
}
