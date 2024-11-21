package com.example.library.controller;

import com.example.library.model.Nota;
import com.example.library.service.NotaService;
import com.example.library.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @Autowired
    private LibroService libroService;

    @GetMapping
    public String listarNotas(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("notas", notaService.findByUsuarioCorreo(userDetails.getUsername()));
        return "notas/lista";
    }

    @GetMapping("/nueva")
    public String nuevaNota(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("nota", new Nota());
        model.addAttribute("libros", libroService.findByUsuarioCorreo(userDetails.getUsername())); 
        return "notas/form";
    }

    @PostMapping("/guardar")
    public String guardarNota(
            @ModelAttribute Nota nota,
            @RequestParam Integer libro_id,
            @AuthenticationPrincipal UserDetails userDetails) {

        notaService.save(nota, libro_id, userDetails.getUsername());
        return "redirect:/notas";
    }

    @GetMapping("/editar/{id}")
    public String editarNota(@PathVariable Integer id, Model model) {
        Nota  nota = notaService.findById(id);
        model.addAttribute("nota", nota);
        model.addAttribute("libro_id", nota.getLibro().getId());
        return "notas/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarNota(@PathVariable Integer id) {
        notaService.deleteById(id);
        return "redirect:/notas";
    }
}