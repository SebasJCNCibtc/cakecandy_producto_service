package com.proyecto.cakecandy_producto_service.application.service.impl;

import com.proyecto.cakecandy_producto_service.application.dto.ProductoRequestDto;
import com.proyecto.cakecandy_producto_service.application.dto.ProductoResponseDto;
import com.proyecto.cakecandy_producto_service.application.mapper.ProductoMapper;
import com.proyecto.cakecandy_producto_service.application.service.ProductoService;
import com.proyecto.cakecandy_producto_service.domain.model.Producto;
import com.proyecto.cakecandy_producto_service.domain.repository.CategoriaRepository;
import com.proyecto.cakecandy_producto_service.domain.repository.MarcaRepository;
import com.proyecto.cakecandy_producto_service.domain.repository.ProductoRepository;
import com.proyecto.cakecandy_producto_service.domain.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoMapper productoMapper;
    private final PdfReportServiceImpl pdfReportService;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDto> findAll() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDto findById(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return productoMapper.toResponseDto(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDto save(ProductoRequestDto requestDto) {
        Producto producto = productoMapper.toEntity(requestDto);
        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toResponseDto(savedProducto);
    }

    @Override
    @Transactional
    public ProductoResponseDto update(Integer id, ProductoRequestDto requestDto) {
        Producto existingProducto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Actualizamos los campos del producto existente
        existingProducto.setNombreProducto(requestDto.getNombreProducto());
        existingProducto.setPrecioVenta(requestDto.getPrecioVenta());
        existingProducto.setPrecioCosto(requestDto.getPrecioCosto());
        existingProducto.setStock(requestDto.getStock());

        // Actualizamos las relaciones
        existingProducto.setCategoria(categoriaRepository.findById(requestDto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + requestDto.getIdCategoria())));
        existingProducto.setMarca(marcaRepository.findById(requestDto.getIdMarca())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + requestDto.getIdMarca())));
        existingProducto.setProveedor(proveedorRepository.findById(requestDto.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + requestDto.getIdProveedor())));

        Producto updatedProducto = productoRepository.save(existingProducto);
        return productoMapper.toResponseDto(updatedProducto);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
    @Override
    @Transactional
    public void updateStock(Integer id, Integer cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        int nuevoStock = producto.getStock() - cantidad;
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombreProducto());
        }
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
    }
    // En ProductoServiceImpl.java
    @Override
    @Transactional
    public void addStock(Integer id, Integer cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a añadir debe ser positiva.");
        }
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        producto.setStock(producto.getStock() + cantidad);
        productoRepository.save(producto);
    }
    @Override
    public byte[] exportProductsToPdf() throws IOException {
        List<Producto> productos = productoRepository.findAll();
        return pdfReportService.generateProductsReport(productos);
    }
    @Override
    public long countLowStockProducts(Integer umbral) {
        return productoRepository.countByStockLessThan(umbral);
    }
}