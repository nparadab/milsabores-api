package com.milsabores.service;

import com.milsabores.model.Categoria;
import com.milsabores.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository repo;

    public CategoriaService(CategoriaRepository repo) { this.repo = repo; }

    public List<Categoria> listar() { return repo.findAll(); }
    public Optional<Categoria> obtener(Long id) { return repo.findById(id); }
    public Categoria crear(Categoria c) { return repo.save(c); }
    public Categoria actualizar(Long id, Categoria c) {
        c.setId(id);
        return repo.save(c);
    }
    public void eliminar(Long id) { repo.deleteById(id); }
}
