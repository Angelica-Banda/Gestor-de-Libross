package com.example.library.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "notas")
public class Nota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnotas")
    private Integer id;
    
    @Column(nullable = false)
    private String contenido;
    
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;
}