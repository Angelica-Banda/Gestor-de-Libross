package com.example.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.model.Categoria;
import com.example.library.repository.CategoriaRepository;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public void eliminarCategoria(Integer id) {
        categoriaRepository.deleteById(id);
    }

    public void guardarCategoria(Categoria categoria) {
        categoriaRepository.save(categoria);
    }
}
