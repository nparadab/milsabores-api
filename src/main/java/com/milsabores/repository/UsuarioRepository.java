package com.milsabores.repository;

import com.milsabores.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // ✅ Búsqueda exacta (usada por el controlador)
    Optional<Usuario> findByEmail(String email);

    // ✅ Búsqueda ignorando mayúsculas/minúsculas (útil para login)
    Optional<Usuario> findByEmailIgnoreCase(String email);
}
