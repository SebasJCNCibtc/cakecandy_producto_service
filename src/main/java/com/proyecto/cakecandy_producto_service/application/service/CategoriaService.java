package com.proyecto.cakecandy_producto_service.application.service;

import com.proyecto.cakecandy_producto_service.domain.model.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> findAll();
}