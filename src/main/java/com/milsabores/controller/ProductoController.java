package com.milsabores.controller;

import com.milsabores.dto.ProductoDTO;
import com.milsabores.model.Producto;
import com.milsabores.service.ProductoService;
import com.milsabores.service.ProductoMapper;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductoDTO> listar() {
        return service.listar()
                .stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtener(@PathVariable Long id) {
        return service.obtener(id)
                .map(producto -> ResponseEntity.ok(ProductoMapper.toDTO(producto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto);
        Producto creado = service.crear(producto);
        return ResponseEntity.ok(ProductoMapper.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto);
        Producto actualizado = service.actualizar(id, producto);
        return ResponseEntity.ok(ProductoMapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
