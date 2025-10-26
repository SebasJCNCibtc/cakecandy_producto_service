package com.proyecto.cakecandy_producto_service.web.controller;

import com.proyecto.cakecandy_producto_service.application.service.MarcaService;
import com.proyecto.cakecandy_producto_service.domain.model.Categoria;
import com.proyecto.cakecandy_producto_service.domain.model.Marca;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> getAll() {
        return ResponseEntity.ok(marcaService.findAll());
    }
}