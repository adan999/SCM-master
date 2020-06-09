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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

//Notacion declarada para que el sistema sepa que esta clase pertenece al componente Controller
@Controller
@RequestMapping(path = "/compraMayorista")
public class CompraMayoristaController {

    //Bean que nos permitira tener acceso a los elementos de la tabla Usuario y sus metodos correspondientes
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    //Bean que nos permitira tener acceso a los elementos de la tabla Material y sus metodos correspondientes
    @Autowired
    private MaterialServiceImpl materialServiceImpl;

    //Bean que nos permitira tener acceso a los elementos de la tabla CompraMayorista y sus metodos correspondientes
    @Autowired
    private CompraMayoristaServiceImpl compraMayoristaServiceImpl;

    //Bean que nos permitira tener acceso a los elementos de la tabla Material y sus metodos correspondientes
    @Autowired
    private MayoristaServiceImpl mayoristaServiceImpl;

    @Autowired
    private TipoMaterialServiceImpl tipoMaterialServiceImpl;

    public static final double IVA = 1.08;

    //Al entrar en esta ruta se obtendran los datos de la lista y se pasaran a la vista proporcionada
    @GetMapping(path = "/consultar")
    public String consultarTipoMaterial(Model model){
        try {
            model.addAttribute("material", materialServiceImpl.consultarMaterial());
            model.addAttribute("materiales", new Material());

            model.addAttribute("compraMayo",compraMayoristaServiceImpl.consultarCompraMayorista());
            model.addAttribute("comprasMayo",new CompraMayorista());

            model.addAttribute("mayorista",mayoristaServiceImpl.consultarMayorista());
            model.addAttribute("mayoristas",new Mayorista());
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return ViewConstant.MANTCOMPRASMAYO;
    }

    //*Metodo que permite agregar regisitros, en donde se utiliza @PostMapping 
    // para solamente realizar la accion y redirigir a la pagina proporcionada*/
    @PostMapping(path = "/addCompraMayorista")
    public String realizarCompraMayorista(CompraMayorista compraMayorista, Model model, RedirectAttributes redirectAttr){


        //Se multiplica la cantidad de material por su precio unitario y el 8% IVA y se le asigna al total del objeto de Compra
        double subTotal = compraMayorista.getPrecio()*compraMayorista.getCantidad();
        compraMayorista.setSubTotalComp(subTotal);

        double total = subTotal * IVA;
        //compraMayorista.setTotalComp(Float.parseFloat(String.format("%.2f",subTotal)));

        compraMayorista.setTotalComp(total);


        Material material = materialServiceImpl.findById(compraMayorista.getIdMaterial());

        compraMayorista.setIva(8);
        compraMayorista.setNomMaterial(material.getNomMaterial());

        compraMayorista.setEstadoComp("Realizada");

        TipoMaterial tipoMaterial = tipoMaterialServiceImpl.findById(material.getIdMaterialCat());

        //Se obtiene la fecha actual del sistema y se le asigna a la fecha del objeto de compra
        Date fecha = new Date();
        String fechaActual = fecha.getDate()+"/"+(fecha.getMonth()+1)+"/"+(fecha.getYear()+1900);
        compraMayorista.setFechaComp(fechaActual);

        //Se Manda el objeto al metodo  save() donde nos regresara un resultado booleano, true si se registro y false en el caso contrario
        if(compraMayoristaServiceImpl.realizarCompraMayorista(compraMayorista)){
            System.out.println("Entro");

            //Se modifica la cantidad del tipo de material
            double cantMatCat = tipoMaterial.getCantidad();
            double cantCatNew = cantMatCat + compraMayorista.getCantidad();
            tipoMaterialServiceImpl.modificarCantidadTipo(tipoMaterial.getIdMaterialCat(), cantCatNew);

            //Se modifica la cantidad en los materiales
            double cantMat = material.getCantidadActual();
            double cantNew = cantMat + compraMayorista.getCantidad();
            materialServiceImpl.entradasMaterial(compraMayorista.getIdMaterial(), cantNew);
            redirectAttr
                    .addFlashAttribute("mensaje", "Registrado exitosamente")
                    .addFlashAttribute("clase", "success");

        }
        else{
            System.out.println("No entro");
            redirectAttr
                    .addFlashAttribute("mensajex", "No se realizó el registro")
                    .addFlashAttribute("clasex", "unsuccessfully");
        }

        return "redirect:/compraMayorista/consultar";
    }

    /*Metodo utilizado para modificar los datos de los usuarios y despues redirecciona a
     * la misma página de consultar para obtener los datos actualizados*/
    @GetMapping(path = "/cancelarCompra")
    public String cancelCompra(@RequestParam(name = "folioComp", required = true)String folioComp, RedirectAttributes redirectAttr){

        System.out.println(folioComp);

        CompraMayorista compraMayorista = compraMayoristaServiceImpl.findByFolio(folioComp);
        if(compraMayorista.getEstadoComp().compareToIgnoreCase("Realizada")==0) {

            //Se obtiene la cantidad de la compra
            double cantidadMat = compraMayorista.getCantidad();

            //Se obtiene la cantidad actual del material
            Material material = materialServiceImpl.findById(compraMayorista.getIdMaterial());
            double cantidadActualMat = material.getCantidadActual();
            double cantMatNew = cantidadActualMat - cantidadMat;

            //Se modifica la cantidad actual del material
            materialServiceImpl.entradasMaterial(compraMayorista.getIdMaterial(), cantMatNew);

            //Se obtiene la cantidad actual del tipo de material
            TipoMaterial tipoMaterial = tipoMaterialServiceImpl.findById(material.getIdMaterialCat());
            double cantidadActualTip = tipoMaterial.getCantidad();

            //Se modifica la cantidad actual del tipo de material
            double cantidadTipNew = cantidadActualTip - cantidadMat;
            tipoMaterialServiceImpl.modificarCantidadTipo(material.getIdMaterialCat(), cantidadTipNew);


            compraMayoristaServiceImpl.cancelarCompraMayorista(folioComp);
            //return "redirect:/compraMayorista/consultar";
            redirectAttr
                    .addFlashAttribute("mensaje", "Cancelación exitosa")
                    .addFlashAttribute("clase", "success");
        }
        else{

            redirectAttr
                    .addFlashAttribute("mensajex", "Cancelación fallida")
                    .addFlashAttribute("clasex", "unsuccessfully");
        }
        return "redirect:/compraMayorista/consultar";
    }

}
