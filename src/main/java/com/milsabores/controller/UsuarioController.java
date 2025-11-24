package com.milsabores.controller;

import com.milsabores.model.Usuario;
import com.milsabores.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 📌 Listar todos los usuarios (solo ADMIN)
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // 📌 Crear usuario (solo ADMIN)
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        // encriptar contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario nuevo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevo);
    }

    // 📌 Actualizar usuario (solo ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(u -> {
                    u.setNombre(usuario.getNombre());
                    u.setEmail(usuario.getEmail());
                    u.setRol(usuario.getRol());
                    // si viene contraseña nueva, encriptarla
                    if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
                        u.setPassword(passwordEncoder.encode(usuario.getPassword()));
                    }
                    Usuario actualizado = usuarioRepository.save(u);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 📌 Eliminar usuario (solo ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
