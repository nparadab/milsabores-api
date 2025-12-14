package com.milsabores.controller;

import com.milsabores.dto.AuthRequest;
import com.milsabores.dto.AuthResponse;
import com.milsabores.dto.RegisterRequest;
import com.milsabores.model.Usuario;
import com.milsabores.service.AuthService;
import com.milsabores.repository.UsuarioRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService service;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService service, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Registro
    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    // ✅ Login
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    // ✅ Recuperar contraseña (público)
    @PostMapping("/auth/recuperar")
    public ResponseEntity<?> recuperarPassword(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("No existe un usuario con ese correo");
        }

        Usuario usuario = usuarioOpt.get();

        // ✅ Generar contraseña temporal
        String tempPassword = "Temp" + (int)(Math.random() * 9000 + 1000);

        // ✅ Guardarla encriptada
        usuario.setPassword(passwordEncoder.encode(tempPassword));
        usuarioRepository.save(usuario);

        // ✅ Respuesta JSON
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Contraseña temporal generada");
        response.put("passwordTemporal", tempPassword);

        return ResponseEntity.ok(response);
    }

    // ✅ Listar usuarios (ADMIN)
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    // ✅ Crear usuario (ADMIN)
    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario nuevo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevo);
    }

    // ✅ Actualizar usuario (ADMIN)
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setRol(usuarioActualizado.getRol());

        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        }

        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    // ✅ Eliminar usuario (ADMIN)
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}


