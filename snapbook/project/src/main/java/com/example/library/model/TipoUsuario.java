package com.example.library.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_usuario")
public class TipoUsuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipo_usuario")
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
}