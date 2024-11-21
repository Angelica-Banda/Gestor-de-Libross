package com.example.library.service;

import com.example.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private NotaRepository notaRepository;

    public long getTotalUsers() {
        return usuarioRepository.count();
    }

    public long getTotalBooks() {
        return libroRepository.count();
    }

    public long getTotalNotes() {
        return notaRepository.count();
    }

    public List<?> getAllUsers() {
        return usuarioRepository.findAll();
    }

    public Map<String, Object> getDatabaseStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", getTotalUsers());
        stats.put("totalBooks", getTotalBooks());
        stats.put("totalNotes", getTotalNotes());
        return stats;
    }
}