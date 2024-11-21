package com.example.library.service;

import com.example.library.model.Libro;
import com.example.library.model.Nota;
import com.example.library.model.Usuario;
import com.example.library.repository.LibroRepository;
import com.example.library.repository.NotaRepository;
import com.example.library.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Nota> findByUsuarioCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        return notaRepository.findByUsuario(usuario);
    }

    public Nota findById(Integer id) {
        return notaRepository.findById(id).orElse(null);
    }

    public Nota save(Nota nota,  Integer libro_id, String correoUsuario) {
        Libro libro = libroRepository.findById(libro_id)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario);
        
        nota.setLibro(libro);
        nota.setUsuario(usuario);
        nota.setFechaCreacion(LocalDate.now());
        return notaRepository.save(nota);
    }

    public void deleteById(Integer id) {
        notaRepository.deleteById(id);
    }
}