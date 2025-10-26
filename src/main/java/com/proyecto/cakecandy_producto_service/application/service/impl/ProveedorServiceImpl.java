package com.proyecto.cakecandy_producto_service.application.service.impl;

import com.proyecto.cakecandy_producto_service.application.service.ProveedorService;
import com.proyecto.cakecandy_producto_service.domain.model.Proveedor;
import com.proyecto.cakecandy_producto_service.domain.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }
    @Override
    public Proveedor findById(Integer id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
    }
}
