package com.milsabores.service;

import com.milsabores.dto.DetallePedidoDTO;
import com.milsabores.dto.PedidoDTO;
import com.milsabores.dto.ProductoDTO;
import com.milsabores.model.DetallePedido;
import com.milsabores.model.Pedido;
import com.milsabores.model.Producto;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) return null;

        List<DetallePedidoDTO> detallesDTO = null;
        if (pedido.getDetalles() != null) {
            detallesDTO = pedido.getDetalles().stream().map(detalle -> {
                Producto producto = detalle.getProducto();
                ProductoDTO productoDTO = null;
                if (producto != null) {
                    productoDTO = new ProductoDTO(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getDescripcion(),
                        producto.getStock(),
                        null // categoría opcional, puedes mapearla si lo necesitas
                    );
                }
                return new DetallePedidoDTO(
                    detalle.getId(),
                    detalle.getCantidad(),
                    detalle.getSubtotal(),
                    productoDTO
                );
            }).collect(Collectors.toList());
        }

        return new PedidoDTO(
            pedido.getId(),
            pedido.getFecha(),
            pedido.getEstado(),
            pedido.getTotal(),
            detallesDTO
        );
    }

    public static Pedido toEntity(PedidoDTO dto) {
        if (dto == null) return null;

        Pedido pedido = new Pedido();
        pedido.setId(dto.getId());
        pedido.setFecha(dto.getFecha());
        pedido.setEstado(dto.getEstado());
        pedido.setTotal(dto.getTotal());

        if (dto.getDetalles() != null) {
            pedido.setDetalles(dto.getDetalles().stream().map(detalleDTO -> {
                DetallePedido detalle = new DetallePedido();
                detalle.setId(detalleDTO.getId());
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setSubtotal(detalleDTO.getSubtotal());

                if (detalleDTO.getProducto() != null) {
                    Producto producto = new Producto();
                    producto.setId(detalleDTO.getProducto().getId());
                    producto.setNombre(detalleDTO.getProducto().getNombre());
                    producto.setPrecio(detalleDTO.getProducto().getPrecio());
                    producto.setDescripcion(detalleDTO.getProducto().getDescripcion());
                    producto.setStock(detalleDTO.getProducto().getStock());
                    detalle.setProducto(producto);
                }

                detalle.setPedido(pedido);
                return detalle;
            }).collect(Collectors.toList()));
        }

        return pedido;
    }
}
