package com.example.library.service;

import com.example.library.model.TipoUsuario;
import com.example.library.model.Usuario;
import com.example.library.repository.TipoUsuarioRepository;
import com.example.library.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(Usuario usuario) {
        // Asignar tipo de usuario estándar (ID = 2)
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(2)
            .orElseThrow(() -> new RuntimeException("Tipo de usuario estándar no encontrado"));
        
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuarioRepository.save(usuario);
    }

    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
}