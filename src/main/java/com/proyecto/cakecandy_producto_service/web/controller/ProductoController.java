package com.proyecto.cakecandy_producto_service.web.controller;

import com.proyecto.cakecandy_producto_service.application.dto.ProductoRequestDto;
import com.proyecto.cakecandy_producto_service.application.dto.ProductoResponseDto;
import com.proyecto.cakecandy_producto_service.application.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> getAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDto> create(@RequestBody ProductoRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> update(@PathVariable Integer id, @RequestBody ProductoRequestDto requestDto) {
        return ResponseEntity.ok(productoService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    // En ProductoController.java
    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable Integer id, @RequestParam Integer cantidad) {
        productoService.updateStock(id, cantidad);
        return ResponseEntity.ok().build();
    }
    // En ProductoController.java
    @PutMapping("/{id}/add-stock")
    public ResponseEntity<Void> addStock(@PathVariable Integer id, @RequestParam Integer cantidad) {
        productoService.addStock(id, cantidad);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> exportPdf() {
        try {
            byte[] pdfReport = productoService.exportProductsToPdf();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "reporte_productos.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(pdfReport, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/resumen/bajo-stock")
    public ResponseEntity<Long> getLowStockCount(@RequestParam(defaultValue = "10") Integer umbral) {
        return ResponseEntity.ok(productoService.countLowStockProducts(umbral));
    }
}