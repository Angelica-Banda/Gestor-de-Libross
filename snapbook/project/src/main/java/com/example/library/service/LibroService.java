package com.example.library.service;

import com.example.library.model.Categoria;
import com.example.library.model.Libro;
import com.example.library.model.Usuario;
import com.example.library.repository.LibroRepository;
import com.example.library.repository.UsuarioRepository;
import com.example.library.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Libro> findByUsuarioCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        return libroRepository.findByUsuario(usuario);
    }

    public Libro findById(Integer id) {
        return libroRepository.findById(id).orElse(null);
    }

    public Libro save(Libro libro, Integer categoria_id, String correoUsuario) {
        Categoria categoria = categoriaRepository.findById(categoria_id).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario);
        libro.setCategoria(categoria);
        libro.setUsuario(usuario);
        libro.setTitulo(libro.getTitulo());
        libro.setAutor(libro.getAutor());
        libro.setFechaPublicacion(libro.getFechaPublicacion());
        return libroRepository.save(libro);
    }

    public void deleteById(Integer id) {
        libroRepository.deleteById(id);
    }

    // Método de búsqueda avanzada
    public List<Libro> buscarLibros(String titulo, String autor, Integer categoria_id, String correoUsuario) {
        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario);

        if ((titulo == null || titulo.isEmpty()) && 
            (autor == null || autor.isEmpty())) {
                return new ArrayList<>();  // Retornamos lista vacía si no hay parámetros
            }   

        // Filtrar por título, autor o categoría
        return libroRepository.findByUsuarioAndTituloOrAutorOrCategoriaId(usuario, titulo, autor, categoria_id);
    }
}