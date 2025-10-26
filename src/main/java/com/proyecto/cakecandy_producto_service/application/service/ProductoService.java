package com.proyecto.cakecandy_producto_service.application.service;

import com.proyecto.cakecandy_producto_service.application.dto.ProductoRequestDto;
import com.proyecto.cakecandy_producto_service.application.dto.ProductoResponseDto;

import java.io.IOException;
import java.util.List;

public interface ProductoService {
    List<ProductoResponseDto> findAll();
    ProductoResponseDto findById(Integer id);
    ProductoResponseDto save(ProductoRequestDto requestDto);
    ProductoResponseDto update(Integer id, ProductoRequestDto requestDto);
    void deleteById(Integer id);
    void updateStock(Integer id, Integer cantidad);
    void addStock(Integer id, Integer cantidad);
    byte[] exportProductsToPdf() throws IOException;
    long countLowStockProducts(Integer umbral);
}