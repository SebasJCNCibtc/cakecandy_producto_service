package com.proyecto.cakecandy_producto_service.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoResponseDto {
    private Integer idProducto;
    private String nombreProducto;
    private BigDecimal precioVenta;
    private BigDecimal precioCosto;
    private Integer stock;
    private CategoriaDto categoria;
    private MarcaDto marca;
    private ProveedorDto proveedor;
}