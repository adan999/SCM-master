package com.scm.scm.Controllers;


import com.scm.scm.Constant.ViewConstant;
import com.scm.scm.Model.TipoMaterial;
import com.scm.scm.Services.impl.TipoMaterialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//Notacion declarada para que el sistema sepa que esta clase pertenece al componente Controller
@Controller
@RequestMapping(path = "/tipoMaterial")
public class TipoMaterialController {

    //Bean que nos permitira tener acceso a los elementos de la tabla compra y sus metodos correspondientes
    @Autowired
    private TipoMaterialServiceImpl tipoMaterialServiceImpl;

    //Al entrar en esta ruta se obtendran los datos de la lista y se pasaran a la vista proporcionada
    @GetMapping(path = "/consultar")
    public String consultarTipoMaterial(Model model){
        try {
            model.addAttribute("tipoMaterial", tipoMaterialServiceImpl.consultarTipoMaterial());
            model.addAttribute("tipoMateriales",new TipoMaterial());
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return ViewConstant.CONSULTATM;
    }

    /*@GetMapping(path = "/consultaEspecificaTipo/{id}")
    public String consultaEspecificaTipo(Model model,@RequestParam(name = "id", required = true)int id){
    }*/

    /*Metodo utilizado para entrar en la vista del formulario*/
    @GetMapping(path = "/realizar")
    public ModelAndView getForm(){

        return new ModelAndView(ViewConstant.CONSULTATM).addObject("tipoMaterial", new TipoMaterial());
    }

    /*Metodo que permite agregar regisitros, en donde se utiliza @PostMapping
     * para solamente realizar la accion y redirigir a la pagina proporcionada*/
    @PostMapping(path = "/addTipoMaterial")
    public String realizarTipoMaterial(TipoMaterial tipoMaterial, Model model, RedirectAttributes redirectAttr){
        List<TipoMaterial> tipoMateriales = tipoMaterialServiceImpl.consultarTipoMaterial();
        tipoMateriales.add(tipoMaterial);
        model.addAttribute("tipoMateriales",tipoMateriales);

        //Se envian cantidad, entradas y salidas en 0
        double cantidad = 0;
        tipoMaterial.setCantidad(Double.parseDouble(String.format("%.2f",cantidad)));
        tipoMaterial.setUnidadMedida("Kilogramos");

        //Se inserta un id generado automaticamente

        //Se Manda el objeto al metodo  save() donde nos regresara un resultado booleano, true si se registro y false en el caso contrario
        if(tipoMaterialServiceImpl.realizarTipoMaterial(tipoMaterial)){
            System.out.println("Entro");
            redirectAttr
                    .addFlashAttribute("mensaje", "Registrado correctamente")
                    .addFlashAttribute("clase", "success");
        }
        else{
            System.out.println("No entro");
            redirectAttr
                    .addFlashAttribute("mensajex", "No se realizó el registro")
                    .addFlashAttribute("clasex", "unsuccessfully");
        }

        return "redirect:/tipoMaterial/consultar";
    }

    /*Metodo utilizado para modificar solo el nombre del tipo de material y despues redirecciona a
     * la misma página de consultar para obtener los datos actualizados*/
    @PostMapping(path = "/modificarTipoMaterial")
    public String modificarTipoMaterial(TipoMaterial tipoMaterial, Model model, RedirectAttributes redirectAttr){
        List<TipoMaterial> tipoMateriales = tipoMaterialServiceImpl.consultarTipoMaterial();
        tipoMateriales.add(tipoMaterial);
        model.addAttribute("tipoMateriales",tipoMateriales);

        String clave = tipoMaterial.getCodigoTipo();
        String nombre = tipoMaterial.getNomCategoria();

        System.out.println(clave + nombre);

        if(tipoMaterialServiceImpl.modificarTipoMaterial(clave, nombre)){
            System.out.println("Entro");
            redirectAttr
                    .addFlashAttribute("mensaje", "Modificación exitosa")
                    .addFlashAttribute("clase", "success");
        }
        else{
            System.out.println("No entro");
            redirectAttr
                    .addFlashAttribute("mensajex", "Modificación fallida")
                    .addFlashAttribute("clasex", "unsuccessfully");
        }
        return "redirect:/tipoMaterial/consultar";
    }
}
