package com.milsabores.controller;

import com.milsabores.dto.PedidoDTO;
import com.milsabores.model.Pedido;
import com.milsabores.service.PedidoService;
import com.milsabores.service.PedidoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoDTO> listar() {
        return service.listar()
                .stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtener(@PathVariable Long id) {
        return service.obtener(id)
                .map(p -> ResponseEntity.ok(PedidoMapper.toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> crear(@RequestBody PedidoDTO dto) {
        Pedido pedido = PedidoMapper.toEntity(dto);
        pedido.setFecha(LocalDateTime.now()); // asigna fecha automática
        Pedido creado = service.crear(pedido);
        return ResponseEntity.ok(PedidoMapper.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> actualizar(@PathVariable Long id, @RequestBody PedidoDTO dto) {
        Pedido pedido = PedidoMapper.toEntity(dto);
        Pedido actualizado = service.actualizar(id, pedido);
        return ResponseEntity.ok(PedidoMapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
