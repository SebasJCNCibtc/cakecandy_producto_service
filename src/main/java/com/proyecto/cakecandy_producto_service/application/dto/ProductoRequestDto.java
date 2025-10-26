package com.proyecto.cakecandy_producto_service.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoRequestDto {
    private String nombreProducto;
    private BigDecimal precioVenta;
    private BigDecimal precioCosto;
    private Integer stock;
    private Integer idCategoria;
    private Integer idMarca;
    private Integer idProveedor;
}