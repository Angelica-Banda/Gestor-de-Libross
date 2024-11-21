package com.example.library.controller;

import com.example.library.model.Libro;
import com.example.library.service.CategoriaService;
import com.example.library.service.LibroService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarLibros(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("libros", libroService.findByUsuarioCorreo(userDetails.getUsername()));
        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("libro", new Libro());
        model.addAttribute("categoria", categoriaService.obtenerTodas());
        return "libros/form";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute Libro libro, @RequestParam Integer categoria_id, @AuthenticationPrincipal UserDetails userDetails) {
        libroService.save(libro, categoria_id, userDetails.getUsername());
        return "redirect:/libros";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Libro libro = libroService.findById(id);
    if (libro != null) {
        model.addAttribute("libro", libro);
        model.addAttribute("categoria", categoriaService.obtenerTodas());
        return "libros/form";
    } else {
        return "redirect:/libros";
    }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Integer id) {
        libroService.deleteById(id);
        return "redirect:/libros";
    }

    @GetMapping("/buscar")
    public String mostrarFormularioBusqueda(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("categoria", categoriaService.obtenerTodas()); 
        model.addAttribute("libro", new Libro()); 
        return "libros/busqueda-avanzada"; 
    }

    @PostMapping("/buscar")
    public String realizarBusqueda(@ModelAttribute Libro libro, 
                                @RequestParam(required = false) String titulo,
                                @RequestParam(required = false) String autor,
                                @RequestParam(required = false) Integer categoria_id,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {

        List<Libro> resultados = libroService.buscarLibros(titulo, autor, categoria_id, userDetails.getUsername());

        model.addAttribute("libros", resultados);
        model.addAttribute("categoria", categoriaService.obtenerTodas());
        return "libros/busqueda-avanzada"; 
    }
}