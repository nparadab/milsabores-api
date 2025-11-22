package com.milsabores.dto;

public class DetallePedidoDTO {
    private Long id;
    private Integer cantidad;
    private Double subtotal;
    private ProductoDTO producto;

    public DetallePedidoDTO() { }

    public DetallePedidoDTO(Long id, Integer cantidad, Double subtotal, ProductoDTO producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public ProductoDTO getProducto() { return producto; }
    public void setProducto(ProductoDTO producto) { this.producto = producto; }
}
