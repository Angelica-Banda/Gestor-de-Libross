package com.example.library.controller;

import com.example.library.model.Categoria;
import com.example.library.service.AdminService;
import com.example.library.service.CategoriaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping("/dashboard")
    public String adminDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas(); 

        model.addAttribute("categorias", categorias);
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("totalUsers", adminService.getTotalUsers());
        model.addAttribute("totalBooks", adminService.getTotalBooks());
        model.addAttribute("totalNotes", adminService.getTotalNotes());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/database")
    public String databaseStats(Model model) {
        model.addAttribute("dbStats", adminService.getDatabaseStats());
        return "admin/database";
    }

    @GetMapping("/categoria/nueva")
    public String mostrarFormularioNuevaCategoria(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("categoria", new Categoria()); 
        return "admin/form-categoria"; 
    }

    @PostMapping("/categoria/guardar")
    public String agregarCategoria(@ModelAttribute Categoria categoria) {
        categoriaService.guardarCategoria(categoria);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/categoria/editar/{id}")
    public String mostrarFormularioEditarCategoria(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        Categoria categoria = categoriaService.findById(id);
        if (categoria != null) {
            model.addAttribute("categoria", categoria);
            return "admin/form-categoria";  
        } else {
            return "redirect:/admin/dashboard";
        }
    }

    @PostMapping("/categoria/editar/{id}")
    public String editarCategoria(@PathVariable Integer id, @ModelAttribute Categoria categoria) {
        Categoria categoriaExistente = categoriaService.findById(id);
        if (categoriaExistente != null) {
            categoriaExistente.setNombre(categoria.getNombre()); 
            categoriaService.guardarCategoria(categoriaExistente); 
        }
        return "redirect:/admin/dashboard";  
    }

    @GetMapping("/categoria/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id); 
        return "redirect:/admin/dashboard";  
    }
    
}