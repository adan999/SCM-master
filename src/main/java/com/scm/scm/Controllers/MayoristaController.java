package com.scm.scm.Controllers;

import com.scm.scm.Constant.ViewConstant;
import com.scm.scm.Model.Mayorista;
import com.scm.scm.Services.impl.MayoristaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(path = "/mayorista")
public class MayoristaController {

    //Bean que nos permitira tener acceso a los elementos de la tabla compra y sus metodos correspondientes
    @Autowired
    private MayoristaServiceImpl mayoristaServiceImpl;

    //Al entrar en esta ruta se obtendran los datos de la lista y se pasaran a la vista proporcionada
    @GetMapping(path = "/consultar")
    public String consultarMayorista(Model model){
        try {
            model.addAttribute("mayorista", mayoristaServiceImpl.consultarMayorista());
            model.addAttribute("mayoristas",new Mayorista());
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return ViewConstant.MANTMAYORISTAS;
    }

    /*Metodo que permite agregar regisitros, en donde se utiliza @PostMapping
     * para solamente realizar la accion y redirigir a la pagina proporcionada*/
    @PostMapping(path = "/addMayorista")
    public String registrarMayorista(Mayorista mayorista, Model model, RedirectAttributes redirectAttr){


        //Se Manda el objeto al metodo  save() donde nos regresara un resultado booleano, true si se registro y false en el caso contrario
        if(mayoristaServiceImpl.registrarMayorista(mayorista)){
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

        return "redirect:/mayorista/consultar";
    }

    //*Metodo utilizado para modificar los datos de los usuarios y despues redirecciona a
     /* la misma página de consultar para obtener los datos actualizados*/
    @PostMapping(path = "/modificarMayorista")
    public String modificarMayorista(Mayorista mayorista, Model model, RedirectAttributes redirectAttr){
        List<Mayorista> mayoristas = mayoristaServiceImpl.consultarMayorista();
        mayoristas.add(mayorista);
        model.addAttribute("mayoristas",mayoristas);

        String rfc = mayorista.getRfc();
        String nombre = mayorista.getNomEmpresa();
        String direccion = mayorista.getDir();
        String numero = mayorista.getNumTel();
        System.out.println(rfc + nombre + direccion + numero);

        if(mayoristaServiceImpl.modificarMayorista(rfc,nombre,direccion,numero)){
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
        return "redirect:/mayorista/consultar";
    }

    @GetMapping(path = "/buscarMayorista")
    public String consultaEspecificaMayorista(@RequestParam(name = "id",required = true)int id, Model model){
        Mayorista mayorista = mayoristaServiceImpl.findById(id);
        model.addAttribute("mayoristaE",mayorista);

        return "";
    }
}
