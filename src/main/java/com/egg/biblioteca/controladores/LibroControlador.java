package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorService;
import com.egg.biblioteca.servicios.EditorialService;
import com.egg.biblioteca.servicios.LibroService;
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
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private EditorialService editorialService;
    
    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {
        
        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditoriales();
        
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        
        return "libro_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo) {
        
        try {
            
            libroService.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            
            modelo.put("exito", "El libro fue cargado correctamente!");
            
        } catch (MiException ex) {
            
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editorialService.listarEditoriales();
            
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            
            modelo.put("error", ex.getMessage());
            
            return "libro_form.html"; //volvemos a cargar el formulario
        }
        
        return "inicio.html";
        
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        
        List<Libro> libros = libroService.listarLibros();
        
        modelo.addAttribute("libros", libros);
        
        return "libro_list.html";
        
    }
    
    @GetMapping("/eliminar/{isbn}")
    public String eliminar(@PathVariable Long isbn, ModelMap modelo) {
        
        modelo.put("libro", libroService.getOne(isbn));
        
        return "libro_eliminar.html";
        
    }
    
    @PostMapping("/eliminado/{isbn}")
    public String eliminar(@PathVariable Long isbn, String titulo, ModelMap modelo) {
        
        try {
            libroService.eliminarLibro(isbn);
            modelo.put("exito", "El libro fue eliminado!!!!");
            
            return "inicio.html";
            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "libro_list.html";
        }
        
    }

    //Prueba modal
    @PostMapping("/modalautor")
    public String modalAutor(@RequestParam String nombre, ModelMap modelo) {
        
        try {
            
            autorService.crearAutor(nombre);
            
            modelo.put("exito", "El autor fue cargado con exito");
            
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            
            return "libro_form.html";
        }
        
        return "redirect:/libro/registrar";
    }

    //Prueba modal
    @PostMapping("/modaleditorial")
    public String modalEditorial(@RequestParam String nombre, ModelMap modelo) {
        
        try {
            
            editorialService.crearEditorial(nombre);
            
            modelo.put("exito", "El autor fue cargado con exito");
            
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            
            return "libro_form.html";
        }
        
        return "redirect:/libro/registrar";
    }
    
}
