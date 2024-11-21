package com.example.library.repository;

import com.example.library.model.Nota;
import com.example.library.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByUsuario(Usuario correo);
}