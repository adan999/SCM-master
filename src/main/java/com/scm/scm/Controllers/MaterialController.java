package com.scm.scm.Controllers;

import com.scm.scm.Constant.ViewConstant;
import com.scm.scm.Model.Material;
import com.scm.scm.Model.TipoMaterial;
import com.scm.scm.Services.impl.MaterialServiceImpl;
import com.scm.scm.Services.impl.TipoMaterialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//Notacion declarada para que el sistema sepa que esta clase pertenece al componente Controller
@Controller
@RequestMapping(path = "/material")
public class MaterialController {

    //Bean que nos permitira tener acceso a los elementos de la tabla compra y sus metodos correspondientes
    @Autowired
    private MaterialServiceImpl materialServiceImpl;

    @Autowired
    private TipoMaterialServiceImpl tipoMaterialServiceImpl;

    //Al entrar en esta ruta se obtendran los datos de la lista y se pasaran a la vista proporcionada
    @GetMapping(path = "/consultar")
    public String consultarTipoMaterial(Model model){
        try {
            model.addAttribute("material", materialServiceImpl.consultarMaterial());
            model.addAttribute("materiales",new Material());
            model.addAttribute("tipoMaterial", tipoMaterialServiceImpl.consultarTipoMaterial());
            model.addAttribute("tipoMateriales", new TipoMaterial());
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return ViewConstant.CONSULTAM;
    }

    /*@GetMapping(path = "/consultaEspecificaTipo/{id}")
    public String consultaEspecificaTipo(Model model,@RequestParam(name = "id", required = true)int id){

    }*/

    /*Metodo utilizado para entrar en la vista del formulario*/
    @GetMapping(path = "/realizar")
    public ModelAndView getForm(){

        return new ModelAndView(ViewConstant.CONSULTAM).addObject("material", new Material());
    }

    /*Metodo que permite agregar regisitros, en donde se utiliza @PostMapping
     * para solamente realizar la accion y redirigir a la pagina proporcionada*/
    @PostMapping(path = "/addMaterial")
    public String realizarMaterial(Material material, Model model, RedirectAttributes redirectAttr){
        /*List<Material> materiales = materialServiceImpl.consultarMaterial();
        materiales.add(material);
        model.addAttribute("materiales",materiales);
        */
        //se obtiene el id para buscar el tipo de material y asi sumar la cantidad ingresada con la cantidad en existencia
        int id = material.getIdMaterialCat();
        TipoMaterial tipoMaterial = tipoMaterialServiceImpl.findById(id);
        double cantidadTipo= tipoMaterial.getCantidad();
        double cantidadMater = material.getCantidadActual();
        double cantidadNueva = cantidadTipo + cantidadMater;
        material.setUnidadMedida("Kilogramos");

        //Se Manda el objeto al metodo  save() donde nos regresara un resultado booleano, true si se registro y false en el caso contrario
        if(materialServiceImpl.realizarMaterial(material)){
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


        //se manda el id del tipo de material junto con la nueva cantidad, si se regresa un true si se modifico, si regresa un false la modificación falló
        if(tipoMaterialServiceImpl.modificarCantidadTipo(id, cantidadNueva)){
            System.out.println("Modificó");
        }else{
            System.out.println("No modificó");
        }

        return "redirect:/material/consultar";
    }

    /*Metodo utilizado para modificar solo el nombre del tipo de material y despues redirecciona a
     * la misma página de consultar para obtener los datos actualizados*/
    @PostMapping(path = "/modificarMaterial")
    public String modificarMaterial(Material material, Model model, RedirectAttributes redirectAttr){
        List<Material> materiales = materialServiceImpl.consultarMaterial();
        materiales.add(material);
        model.addAttribute("materiales",materiales);

        String clave = material.getCodigoMaterial();
        String nombre = material.getNomMaterial();
        System.out.println(clave + nombre);
        if(materialServiceImpl.modificarMaterial(clave, nombre)){
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
        return "redirect:/material/consultar";
    }

    @GetMapping(path = "/buscarEspecificaMaterial")
    public String consultaEspecificaMaterial(@RequestParam(name = "id",required = true)int id, Model model){
        Material materiales = materialServiceImpl.findById(id);
        model.addAttribute("materialE",materiales);

        return "";
    }
}
