package com.milsabores.service;

import com.milsabores.model.Producto;
import com.milsabores.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
  private final ProductoRepository repo;

  public ProductoService(ProductoRepository repo) {
    this.repo = repo;
  }

  public List<Producto> listar() {
    return repo.findAll();
  }

  public Optional<Producto> obtener(Long id) {
    return repo.findById(id);
  }

  public Producto crear(Producto p) {
    return repo.save(p);
  }

  public Producto actualizar(Long id, Producto p) {
    p.setId(id);
    return repo.save(p);
  }

  public void eliminar(Long id) {
    repo.deleteById(id);
  }
}
