package com.milsabores.service;

import com.milsabores.model.Pedido;
import com.milsabores.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository repo;

    public PedidoService(PedidoRepository repo) { this.repo = repo; }

    public List<Pedido> listar() { return repo.findAll(); }
    public Optional<Pedido> obtener(Long id) { return repo.findById(id); }
    public Pedido crear(Pedido p) { return repo.save(p); }
    public Pedido actualizar(Long id, Pedido p) {
        p.setId(id);
        return repo.save(p);
    }
    public void eliminar(Long id) { repo.deleteById(id); }
}
