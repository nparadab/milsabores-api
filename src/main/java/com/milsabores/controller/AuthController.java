package com.milsabores.controller;

import com.milsabores.dto.AuthRequest;
import com.milsabores.dto.AuthResponse;
import com.milsabores.dto.RegisterRequest;
import com.milsabores.model.Usuario;
import com.milsabores.service.AuthService;
import com.milsabores.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    // ✅ Nuevo endpoint para verificar usuarios guardados en la BD
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }
}
