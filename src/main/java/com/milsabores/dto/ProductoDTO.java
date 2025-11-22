package com.milsabores.dto;

import java.math.BigDecimal;

public class ProductoDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private Integer stock;
    private CategoriaDTO categoria;

    // Constructor vacío
    public ProductoDTO() { }

    // Constructor con parámetros
    public ProductoDTO(Long id, String nombre, BigDecimal precio, String descripcion, Integer stock, CategoriaDTO categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public CategoriaDTO getCategoria() { return categoria; }
    public void setCategoria(CategoriaDTO categoria) { this.categoria = categoria; }

    // toString para depuración
    @Override
    public String toString() {
        return "ProductoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                ", stock=" + stock +
                ", categoria=" + (categoria != null ? categoria.getNombre() : "null") +
                '}';
    }
}
