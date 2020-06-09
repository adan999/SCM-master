package com.scm.scm.Controllers;

import com.scm.scm.Constant.ViewConstant;
import com.scm.scm.Model.Compra;
import com.scm.scm.Repository.Repositories.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(path = "/compra")
public class CompraController {

    @Autowired
    private CompraRepository compraRepository;

    @GetMapping(path = "/consultar")
    public String consultar(Model model){
        try {
            model.addAttribute("compra", compraRepository.findAll());
        }
        catch (Exception e){
            System.out.println("Error Españo: "+e.getMessage());
        }

        return ViewConstant.CONSULTAM;
    }

    @GetMapping(path = "/realizar")
    public ModelAndView getForm(){

        return new ModelAndView(ViewConstant.CONSULTAM).addObject("compra", new Compra());
    }

    @PostMapping(path = "/addCompra")
    public String addNewCompra(Compra compra, Model model){
        List<Compra> compras = compraRepository.findAll();
        compras.add(compra);
        model.addAttribute("compras",compras);
        compra.setTotal(compra.getCantidad()*compra.getPrecio());
        if(compraRepository.save(compra)){
            System.out.println("Entro");
            System.out.println(compra);
        }
        else{
            System.out.println("No entro");
        }

        return "redirect:/compra/consultar";
    }


}
