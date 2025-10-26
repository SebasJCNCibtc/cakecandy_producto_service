package com.proyecto.cakecandy_producto_service.domain.repository;

import com.proyecto.cakecandy_producto_service.domain.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}