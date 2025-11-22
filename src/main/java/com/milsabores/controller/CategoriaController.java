package com.milsabores.controller;

import com.milsabores.dto.CategoriaDTO;
import com.milsabores.model.Categoria;
import com.milsabores.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoriaDTO> listar() {
        return service.listar()
                .stream()
                .map(c -> new CategoriaDTO(c.getId(), c.getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtener(@PathVariable Long id) {
        return service.obtener(id)
                .map(c -> ResponseEntity.ok(new CategoriaDTO(c.getId(), c.getNombre())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> crear(@RequestBody CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        Categoria creada = service.crear(categoria);
        return ResponseEntity.ok(new CategoriaDTO(creada.getId(), creada.getNombre()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNombre(dto.getNombre());
        Categoria actualizada = service.actualizar(id, categoria);
        return ResponseEntity.ok(new CategoriaDTO(actualizada.getId(), actualizada.getNombre()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
