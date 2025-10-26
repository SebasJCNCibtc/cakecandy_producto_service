package com.proyecto.cakecandy_producto_service.application.mapper;

import com.proyecto.cakecandy_producto_service.application.dto.*;
import com.proyecto.cakecandy_producto_service.domain.model.Categoria;
import com.proyecto.cakecandy_producto_service.domain.model.Marca;
import com.proyecto.cakecandy_producto_service.domain.model.Producto;
import com.proyecto.cakecandy_producto_service.domain.model.Proveedor;
import com.proyecto.cakecandy_producto_service.domain.repository.CategoriaRepository;
import com.proyecto.cakecandy_producto_service.domain.repository.MarcaRepository;
import com.proyecto.cakecandy_producto_service.domain.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoMapper {

    // Inyectamos los repositorios para poder buscar las entidades relacionadas
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ProveedorRepository proveedorRepository;

    public Producto toEntity(ProductoRequestDto dto) {
        Producto producto = new Producto();
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setPrecioCosto(dto.getPrecioCosto());
        producto.setStock(dto.getStock());

        // Buscamos las entidades completas usando los IDs del DTO
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getIdCategoria()));
        Marca marca = marcaRepository.findById(dto.getIdMarca())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + dto.getIdMarca()));
        Proveedor proveedor = proveedorRepository.findById(dto.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + dto.getIdProveedor()));

        producto.setCategoria(categoria);
        producto.setMarca(marca);
        producto.setProveedor(proveedor);

        return producto;
    }

    public ProductoResponseDto toResponseDto(Producto entity) {
        ProductoResponseDto dto = new ProductoResponseDto();
        dto.setIdProducto(entity.getIdProducto());
        dto.setNombreProducto(entity.getNombreProducto());
        dto.setPrecioVenta(entity.getPrecioVenta());
        dto.setPrecioCosto(entity.getPrecioCosto());
        dto.setStock(entity.getStock());

        // Mapeamos las entidades anidadas a sus DTOs correspondientes
        if (entity.getCategoria() != null) {
            CategoriaDto categoriaDto = new CategoriaDto();
            categoriaDto.setIdCategoria(entity.getCategoria().getIdCategoria());
            categoriaDto.setNombreCategoria(entity.getCategoria().getNombreCategoria());
            dto.setCategoria(categoriaDto);
        }

        if (entity.getMarca() != null) {
            MarcaDto marcaDto = new MarcaDto();
            marcaDto.setIdMarca(entity.getMarca().getIdMarca());
            marcaDto.setNombreMarca(entity.getMarca().getNombreMarca());
            dto.setMarca(marcaDto);
        }

        if (entity.getProveedor() != null) {
            ProveedorDto proveedorDto = new ProveedorDto();
            proveedorDto.setIdProveedor(entity.getProveedor().getIdProveedor());
            proveedorDto.setNombreProveedor(entity.getProveedor().getNombreProveedor());
            dto.setProveedor(proveedorDto);
        }

        return dto;
    }
}