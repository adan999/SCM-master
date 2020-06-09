package com.scm.scm.Controllers;


import com.scm.scm.Constant.ViewConstant;
import com.scm.scm.Model.CompraMinorista;
import com.scm.scm.Model.Material;
import com.scm.scm.Model.TipoMaterial;
import com.scm.scm.Services.impl.CompraMinoristaServiceImpl;
import com.scm.scm.Services.impl.MaterialServiceImpl;
import com.scm.scm.Services.impl.TipoMaterialServiceImpl;
import com.scm.scm.Services.impl.VentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

//Notacion declarada para que el sistema sepa que esta clase pertenece al componente Controller
@Controller
@RequestMapping(path = "/compraMinorista")
public class CompraMinoristaController {

    //Bean que nos permitira tener acceso a los elementos de la tabla compra y sus metodos correspondientes
    @Autowired
    private VentaServiceImpl ventaServiceImpl;

    //Bean que nos permitira tener acceso a los elementos de la tabla compra y sus metodos correspondientes
    @Autowired
    private CompraMinoristaServiceImpl compraMinoristaServiceImpl;

    //Bean que nos permitira tener acceso a los elementos de la tabla compra y sus metodos correspondientes
    @Autowired
    private MaterialServiceImpl materialServiceImpl;

    @Autowired
    private TipoMaterialServiceImpl tipoMaterialServiceImpl;

    public static final double IVA = 1.08;

    //Al entrar en esta ruta se obtendran los datos de la lista y se pasaran a la vista proporcionada
    @GetMapping(path = "/consultar")
    public String consultarCompraMinorista(Model model){
        try {
            model.addAttribute("compraMinorista", compraMinoristaServiceImpl.consultarCompraMinorista());
            model.addAttribute("comprasMinorista",new CompraMinorista());

            model.addAttribute("material", materialServiceImpl.consultarMaterial());
            model.addAttribute("materiales",new Material());

        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return ViewConstant.MANTCOMPRASMINO;
    }

    /*Metodo utilizado para entrar en la vista del formulario*/
    @GetMapping(path = "/realizar")
    public ModelAndView getForm(){

        return new ModelAndView(ViewConstant.MANTCOMPRASMINO).addObject("compraMinorista", new CompraMinorista());
    }

    /*Metodo que permite agregar regisitros, en donde se utiliza @PostMapping
     * para solamente realizar la accion y redirigir a la pagina proporcionada*/
    @PostMapping(path = "/addCompraMinorista")
    public String realizarCompraMinorista(CompraMinorista compraMinorista, Model model, RedirectAttributes redirectAttr){


        //Se multiplica la cantidad de material por su precio unitario y el 8% IVA y se le asigna al total del objeto de Compra
        double subTotal = compraMinorista.getPrecio()*compraMinorista.getCantidad();
        compraMinorista.setSubTotalComp(subTotal);

        double total = subTotal * IVA;
        //compraMinorista.setTotalComp(Float.parseFloat(String.format("%.2f",subTotal)));

        compraMinorista.setTotalComp(total);


        Material material = materialServiceImpl.findById(compraMinorista.getIdMaterial());

        compraMinorista.setIva(8);
        compraMinorista.setNomMaterial(material.getNomMaterial());

        compraMinorista.setEstadoComp("Realizada");

        TipoMaterial tipoMaterial = tipoMaterialServiceImpl.findById(material.getIdMaterialCat());
        //double cantMatCat = tipoMaterial.getCantidad();
        //double cantCatNew = cantMatCat + compraMinorista.getCantidad();

        //tipoMaterialServiceImpl.modificarCantidadTipo(tipoMaterial.getIdMaterialCat(), cantCatNew);

        //double cantMat = material.getCantidadActual();
        //double cantNew = cantMat + compraMinorista.getCantidad();

        //materialServiceImpl.entradasMaterial(compraMinorista.getIdMaterial(), cantNew);



        //Se obtiene la fecha actual del sistema y se le asigna a la fecha del objeto de compra
        Date fecha = new Date();
        String fechaActual = fecha.getDate()+"/"+(fecha.getMonth()+1)+"/"+(fecha.getYear()+1900);
        compraMinorista.setFechaComp(fechaActual);

        //Se Manda el objeto al metodo  save() donde nos regresara un resultado booleano, true si se registro y false en el caso contrario
        if(compraMinoristaServiceImpl.realizarCompraMinorista(compraMinorista)){
            System.out.println("Entro");


            //Se modifica la cantidad del tipo de material
            double cantMatCat = tipoMaterial.getCantidad();
            double cantCatNew = cantMatCat + compraMinorista.getCantidad();
            tipoMaterialServiceImpl.modificarCantidadTipo(tipoMaterial.getIdMaterialCat(), cantCatNew);

            //Se modifica la cantidad en los materiales
            double cantMat = material.getCantidadActual();
            double cantNew = cantMat + compraMinorista.getCantidad();
            materialServiceImpl.entradasMaterial(compraMinorista.getIdMaterial(), cantNew);

            redirectAttr
                    .addFlashAttribute("mensaje", "Registrado correctamente")
                    .addFlashAttribute("clase", "success");
        }
        else{
            System.out.println("No entro");
            redirectAttr.addFlashAttribute("mensajex", "No se realizó el registro")
            .addFlashAttribute("clasex", "unsuccessfully");
        }

        return "redirect:/compraMinorista/consultar";
    }

    /*Metodo utilizado para cambiar el estado de una compra de "Realizada" a "Cancelada" y despues redirecciona a
     * la misma página de consultar para obtener los datos actualizados*/
    @GetMapping(path = "/cancelarCompra")
    public String cancelCompra(@RequestParam(name = "folioSegura", required = true)String folioSegura,RedirectAttributes redirectAttr ){

        CompraMinorista compraMinorista = compraMinoristaServiceImpl.findByFolio(folioSegura);
        if(compraMinorista.getEstadoComp().compareToIgnoreCase("Realizada")==0) {

            //Se obtiene la cantidad de la compra
            double cantidadMat = compraMinorista.getCantidad();

            //Se obtiene la cantidad actual del material
            Material material = materialServiceImpl.findById(compraMinorista.getIdMaterial());
            double cantidadActualMat = material.getCantidadActual();
            double cantMatNew = cantidadActualMat - cantidadMat;

            //Se modifica la cantidad actual del material
            materialServiceImpl.entradasMaterial(compraMinorista.getIdMaterial(), cantMatNew);

            //Se obtiene la cantidad actual del tipo de material
            TipoMaterial tipoMaterial = tipoMaterialServiceImpl.findById(material.getIdMaterialCat());
            double cantidadActualTip = tipoMaterial.getCantidad();

            //Se modifica la cantidad actual del tipo de material
            double cantidadTipNew = cantidadActualTip - cantidadMat;
            tipoMaterialServiceImpl.modificarCantidadTipo(material.getIdMaterialCat(), cantidadTipNew);


            compraMinoristaServiceImpl.cancelarCompraMinorista(folioSegura);
            //return "redirect:/compraMinorista/consultar";
            redirectAttr
                    .addFlashAttribute("mensaje", "Cancelación exitosa")
                    .addFlashAttribute("clase", "success");

        }
        else{

            redirectAttr
                    .addFlashAttribute("mensajex", "Cancelación fallida")
                    .addFlashAttribute("clasex", "unsuccessfully");

        }
        return "redirect:/compraMinorista/consultar";
    }

    //Consultas específicas
    @GetMapping("/consultar/{folioComp}")
    public String findCompraMin(Model model,@PathVariable("folioComp")String folioComp){
        CompraMinorista compraMinorista = compraMinoristaServiceImpl.findByFolio(folioComp);

        if(compraMinorista != null) {

            compraMinorista.setFolioSegura(compraMinorista.getFolioSegura());
            compraMinorista.setFechaComp(compraMinorista.getFechaComp());
            compraMinorista.setNomCliente(compraMinorista.getNomCliente());
            compraMinorista.setDirCliente(compraMinorista.getDirCliente());
            compraMinorista.setCantidad(compraMinorista.getCantidad());
            compraMinorista.setPrecio(compraMinorista.getPrecio());
            compraMinorista.setIva(compraMinorista.getIva());
            compraMinorista.setSubTotalComp(compraMinorista.getSubTotalComp());
            compraMinorista.setTotalComp(compraMinorista.getTotalComp());
            compraMinorista.setNomMaterial(compraMinorista.getNomMaterial());
            compraMinorista.setEstadoComp(compraMinorista.getEstadoComp());
            compraMinorista.setIdMaterial(compraMinorista.getIdMaterial());
            compraMinorista.setNomUsuario(compraMinorista.getNomUsuario());


        }

        model.addAttribute("compraMinorista", compraMinorista);

        return ViewConstant.MANTCOMPRASMINO;
    }




}
