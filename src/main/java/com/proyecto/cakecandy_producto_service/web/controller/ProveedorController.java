package com.proyecto.cakecandy_producto_service.web.controller;

import com.proyecto.cakecandy_producto_service.application.service.MarcaService;
import com.proyecto.cakecandy_producto_service.application.service.ProveedorService;
import com.proyecto.cakecandy_producto_service.domain.model.Categoria;
import com.proyecto.cakecandy_producto_service.domain.model.Marca;
import com.proyecto.cakecandy_producto_service.domain.model.Proveedor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedor>> getAll() {
        return ResponseEntity.ok(proveedorService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedorService.findById(id));
    }
}