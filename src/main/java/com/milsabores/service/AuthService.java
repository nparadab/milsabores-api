package com.milsabores.service;

import com.milsabores.dto.AuthRequest;
import com.milsabores.dto.AuthResponse;
import com.milsabores.dto.RegisterRequest;
import com.milsabores.model.Usuario;
import com.milsabores.repository.UsuarioRepository;
import com.milsabores.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ✅ Método auxiliar para encriptar contraseñas
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // ✅ Registro de usuario
    public AuthResponse register(RegisterRequest request) {
        usuarioRepository.findByEmailIgnoreCase(request.getEmail())
                .ifPresent(u -> { throw new RuntimeException("Email ya registrado"); });

        Usuario u = new Usuario();
        u.setNombre(request.getNombre());
        u.setEmail(request.getEmail());
        u.setPassword(passwordEncoder.encode(request.getPassword())); // encriptar
        u.setRol(request.getRol());
        usuarioRepository.save(u);

        String token = jwtUtil.generateToken(
                u.getEmail(),
                Map.of("rol", u.getRol(), "nombre", u.getNombre())
        );
        return new AuthResponse(token);
    }

    // ✅ Login de usuario
    public AuthResponse login(AuthRequest request) {
        Usuario u = usuarioRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), u.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtUtil.generateToken(
                u.getEmail(),
                Map.of("rol", u.getRol(), "nombre", u.getNombre())
        );
        return new AuthResponse(token);
    }
}

