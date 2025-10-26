package com.proyecto.cakecandy_producto_service.application.service;

import com.proyecto.cakecandy_producto_service.domain.model.Marca;
import java.util.List;

public interface MarcaService {
    List<Marca> findAll();
}