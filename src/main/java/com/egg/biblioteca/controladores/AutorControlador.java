package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor") //localhost:8080/autor
public class AutorControlador {

    @Autowired
    private AutorService autorService;
    
    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {

            autorService.crearAutor(nombre);

            modelo.put("exito", "El autor fue cargado con exito");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "autor_form.html";
        }
        return "inicio.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Autor> autores = autorService.listarAutores();

        modelo.addAttribute("autores", autores);

        return "autor_list.html";

    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("autor", autorService.getOne(id));

        return "autor_modificar.html";

    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {

        try {
            autorService.modificarAutor(nombre, id);

            return "redirect:../lista";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {

        modelo.put("autor", autorService.getOne(id));

        return "autor_eliminar.html";

    }

    @PostMapping("/eliminado/{id}")
    public String eliminar(@PathVariable String id, String nombre, ModelMap modelo) {

        try {

            autorService.eliminar(id);

            return "inicio.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            
            return "autor_eliminar.html";
        }

    }

}
