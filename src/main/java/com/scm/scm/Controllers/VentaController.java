package com.scm.scm.Controllers;


import com.scm.scm.Constant.ViewConstant;
import com.scm.scm.Model.*;
import com.scm.scm.Services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Notacion declarada para que el sistema sepa que esta clase pertenece al componente Controller
@Controller
@RequestMapping(path = "/venta")
public class VentaController {


    //Bean que nos permitira tener acceso a los elementos de la tabla compra y sus metodos correspondientes
    @Autowired
    private VentaServiceImpl ventaServiceImpl;

    //Bean que nos permitira tener acceso a los elementos de la tabla compra y sus metodos correspondientes
    @Autowired
    private MaterialServiceImpl materialServiceImpl;

    @Autowired
    private TipoMaterialServiceImpl tipoMaterialServiceImpl;

    @Autowired
    private MayoristaServiceImpl mayoristaServiceImpl;

    public static final double IVA = 1.08;

    //Al entrar en esta ruta se obtendran los datos de la lista y se pasaran a la vista proporcionada
    @GetMapping(path = "/consultar")
    public String consultarVenta(Model model){
        try {
            model.addAttribute("venta", ventaServiceImpl.consultarVenta());
            model.addAttribute("ventas",new Venta());

            model.addAttribute("tipoMaterial", tipoMaterialServiceImpl.consultarTipoMaterial());
            model.addAttribute("tipoMateriales",new TipoMaterial());

            model.addAttribute("mayorista", mayoristaServiceImpl.consultarMayorista());
            model.addAttribute("mayoristas", new Mayorista());
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return ViewConstant.VENTAM;
    }

    /*Metodo utilizado para entrar en la vista del formulario*/
    @GetMapping(path = "/realizar")
    public ModelAndView getForm(){

        return new ModelAndView(ViewConstant.VENTAM).addObject("venta", new Venta());
    }

    /*Metodo que permite agregar regisitros, en donde se utiliza @PostMapping
     * para solamente realizar la accion y redirigir a la pagina proporcionada*/
    @PostMapping(path = "/addVenta")
    public String realizarVenta(Venta venta, Model model, RedirectAttributes redirectAttr){


        //Se multiplica la cantidad de material por su precio unitario y el 8% IVA y se le asigna al total del objeto de Compra
        double subTotal = venta.getPrecio()*venta.getCantidad();
        venta.setSubTotalVenta(subTotal);

        double total = subTotal * IVA;
        //compraMinorista.setTotalComp(Float.parseFloat(String.format("%.2f",subTotal)));

        venta.setTotalVenta(total);


        venta.setUnidad("Kilogramos");
        TipoMaterial tipoMaterial = tipoMaterialServiceImpl.findById(venta.getIdMaterialCat());

        venta.setIva(8);

        venta.setNomUsuario("root");

        venta.setEstadoVenta("Realizada");

       // TipoMaterial tipoMaterial = tipoMaterialServiceImpl.findById(material.getIdMaterialCat());
        //double cantMatCat = tipoMaterial.getCantidad();
        //double cantCatNew = cantMatCat + compraMinorista.getCantidad();

        //tipoMaterialServiceImpl.modificarCantidadTipo(tipoMaterial.getIdMaterialCat(), cantCatNew);

        //double cantMat = material.getCantidadActual();
        //double cantNew = cantMat + compraMinorista.getCantidad();

        //materialServiceImpl.entradasMaterial(compraMinorista.getIdMaterial(), cantNew);



        //Se obtiene la fecha actual del sistema y se le asigna a la fecha del objeto de compra
        Date fecha = new Date();
        String fechaActual = fecha.getDate()+"/"+(fecha.getMonth()+1)+"/"+(fecha.getYear()+1900);
        venta.setFechaVenta(fechaActual);

        //Se modifica la cantidad del tipo de material
        double cantMatCat = tipoMaterial.getCantidad();
        double cantCatNew = cantMatCat - venta.getCantidad();
        if(cantCatNew < 0){

        }else {
            //Se Manda el objeto al metodo  save() donde nos regresara un resultado booleano, true si se registro y false en el caso contrario
            if (ventaServiceImpl.realizarVenta(venta)) {
                System.out.println("Entro");



                tipoMaterialServiceImpl.modificarCantidadTipo(tipoMaterial.getIdMaterialCat(), cantCatNew);

                List<Material> materiales = ventaServiceImpl.contarMateriales(venta.getIdMaterialCat());




                double cantNewRest = venta.getCantidad();
                for( int i = 0; i < materiales.size(); i++){

                    if(materiales.get(i).getCantidadActual() > cantNewRest){
                        cantNewRest = materiales.get(i).getCantidadActual() - venta.getCantidad();

                        materialServiceImpl.entradasMaterial(materiales.get(i).getIdMaterial(), cantNewRest);

                        break;
                    }else{
                        double cant = cantNewRest - materiales.get(i).getCantidadActual();
                        cantNewRest = cant;
                        materialServiceImpl.entradasMaterial(materiales.get(i).getIdMaterial(), 0);
                    }
                }


                //Material material = materialServiceImpl.findById(tipoMaterial.getIdMaterialCat());
                //Se modifica la cantidad en los materiales
                //double cantMat = material.getCantidadActual();
                //double cantNew = cantMat + compraMinorista.getCantidad();
                //materialServiceImpl.entradasMaterial(compraMinorista.getIdMaterial(), cantNew);

                redirectAttr
                        .addFlashAttribute("mensaje", "Registrado correctamente")
                        .addFlashAttribute("clase", "success");
            } else {
                System.out.println("No entro");
                redirectAttr
                        .addFlashAttribute("mensajex", "No se realizó el registro")
                        .addFlashAttribute("clasex", "unsuccessfully");
            }
        }
        return "redirect:/venta/consultar";
    }

    /*Metodo utilizado para cambiar el estado de una compra de "Realizada" a "Cancelada" y despues redirecciona a
     * la misma página de consultar para obtener los datos actualizados*/
    @GetMapping(path = "/cancelarVenta")
    public String cancelCompra(@RequestParam(name = "folioVenta", required = true)String folioVenta, RedirectAttributes redirectAttr){

        Venta venta = ventaServiceImpl.findByFolio(folioVenta);
        if(venta.getEstadoVenta().compareToIgnoreCase("Realizada")==0) {

            //Se obtiene la cantidad de la venta
            double cantidadMat = venta.getCantidad();

            //Se obtiene la cantidad actual del material
            TipoMaterial tipoMaterial = tipoMaterialServiceImpl.findById(venta.getIdMaterialCat());
            double cantidadActualMat = tipoMaterial.getCantidad();
            double cantMatNew = cantidadActualMat + cantidadMat;

            //Se modifica la cantidad actual del tipo de material
            tipoMaterialServiceImpl.modificarCantidadTipo(venta.getIdMaterialCat(), cantMatNew);

            List<Material> materiales = ventaServiceImpl.contarMateriales(venta.getIdMaterialCat());

            double newCantidad = cantidadMat / materiales.size();

            for(int i = 0; i < materiales.size(); i ++){


                double cantidadSumada = materiales.get(i).getCantidadActual() + newCantidad;
                materialServiceImpl.entradasMaterial(materiales.get(i).getIdMaterial(), cantidadSumada);

            }
            //Se obtiene la cantidad actual del tipo de material
            //TipoMaterial tipoMaterial = tipoMaterialServiceImpl.findById(material.getIdMaterialCat());
            //double cantidadActualTip = tipoMaterial.getCantidad();

            //Se modifica la cantidad actual del tipo de material
            //double cantidadTipNew = cantidadActualTip - cantidadMat;
            //tipoMaterialServiceImpl.modificarCantidadTipo(material.getIdMaterialCat(), cantidadTipNew);


            ventaServiceImpl.cancelarVenta(folioVenta);
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
        return "redirect:/venta/consultar";
    }

}
