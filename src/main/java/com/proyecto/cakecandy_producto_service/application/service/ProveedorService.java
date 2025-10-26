package com.proyecto.cakecandy_producto_service.application.service;

import com.proyecto.cakecandy_producto_service.domain.model.Proveedor;

import java.util.List;

public interface ProveedorService {
    List<Proveedor> findAll();
    Proveedor findById(Integer id);
}