package com.proyecto.cakecandy_producto_service.domain.repository;

import com.proyecto.cakecandy_producto_service.domain.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    long countByStockLessThan(Integer stock);
}