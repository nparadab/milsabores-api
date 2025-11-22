package com.milsabores.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {
    private Long id;
    private LocalDateTime fecha;
    private String estado;
    private Double total;
    private List<DetallePedidoDTO> detalles;

    public PedidoDTO() { }

    public PedidoDTO(Long id, LocalDateTime fecha, String estado, Double total, List<DetallePedidoDTO> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.detalles = detalles;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public List<DetallePedidoDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoDTO> detalles) { this.detalles = detalles; }
}
