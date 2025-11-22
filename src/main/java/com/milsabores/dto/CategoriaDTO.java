package com.milsabores.dto;

public class CategoriaDTO {
    private Long id;
    private String nombre;

    // Constructor vacío
    public CategoriaDTO() { }

    // Constructor con parámetros
    public CategoriaDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    // toString para depuración
    @Override
    public String toString() {
        return "CategoriaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
