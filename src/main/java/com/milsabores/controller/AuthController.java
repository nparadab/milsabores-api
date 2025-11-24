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
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    // 🔐 Registro público (siempre CLIENTE)
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Forzar rol CLIENTE en el registro público
        request.setRol("CLIENTE");
        return ResponseEntity.ok(service.register(request));
    }

    // 🔐 Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    // 👥 Listar todos los usuarios (ADMIN)
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    // 👥 Crear usuario desde panel admin (ADMIN)
    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody RegisterRequest request) {
        Usuario u = new Usuario();
        u.setNombre(request.getNombre());
        u.setEmail(request.getEmail());
        u.setPassword(service.encodePassword(request.getPassword())); // usar PasswordEncoder
        u.setRol(request.getRol());
        usuarioRepository.save(u);
        return ResponseEntity.ok(u);
    }

    // 👥 Modificar usuario por ID (ADMIN)
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

        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    // 👥 Eliminar usuario por ID (ADMIN)
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}

