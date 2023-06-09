package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.servicios.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        
        System.out.println(usuarios);
        
        return "usuario_list.html";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id) {
        usuarioService.cambiarRol(id);

        return "redirect:/admin/usuarios";
    }
}
