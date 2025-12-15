package com.milsabores.repository;

import com.milsabores.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // ✅ Búsqueda exacta (se mantiene por compatibilidad)
    Optional<Usuario> findByEmail(String email);

    // ✅ Búsqueda ignorando mayúsculas/minúsculas (la que usará recuperar contraseña)
    Optional<Usuario> findByEmailIgnoreCase(String email);
}
