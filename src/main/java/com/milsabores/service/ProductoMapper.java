package com.milsabores.service;

import com.milsabores.dto.CategoriaDTO;
import com.milsabores.dto.ProductoDTO;
import com.milsabores.model.Categoria;
import com.milsabores.model.Producto;

public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {
        if (producto == null) return null;

        CategoriaDTO catDTO = null;
        if (producto.getCategoria() != null) {
            catDTO = new CategoriaDTO();
            catDTO.setId(producto.getCategoria().getId());
            catDTO.setNombre(producto.getCategoria().getNombre());
        }

        return new ProductoDTO(
            producto.getId(),
            producto.getNombre(),
            producto.getPrecio(),
            producto.getDescripcion(),
            producto.getStock(),
            catDTO
        );
    }

    public static Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;

        Categoria categoria = null;
        if (dto.getCategoria() != null) {
            categoria = new Categoria();
            categoria.setId(dto.getCategoria().getId());
            categoria.setNombre(dto.getCategoria().getNombre());
        }

        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria);

        return producto;
    }
}
