package com.example.library.repository;

import com.example.library.model.Libro;
import com.example.library.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    List<Libro> findByUsuario(Usuario correo);

    
    // Buscar por título, autor o categoría (utilizando JPQL o consultas personalizadas)
    @Query("SELECT l FROM Libro l WHERE l.usuario = :usuario "
            + "AND (COALESCE(:titulo, '') = '' OR l.titulo LIKE %:titulo%) "
            + "AND (COALESCE(:autor, '') = '' OR l.autor LIKE %:autor%) "
            + "AND (:categoria IS NULL OR l.categoria.id = :categoria)")
    List<Libro> findByUsuarioAndTituloOrAutorOrCategoriaId(@Param("usuario") Usuario usuario,
                                                           @Param("titulo") String titulo,
                                                           @Param("autor") String autor,
                                                           @Param("categoria") Integer categoria);

}