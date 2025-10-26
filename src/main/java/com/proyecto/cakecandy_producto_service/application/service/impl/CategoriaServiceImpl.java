package com.proyecto.cakecandy_producto_service.application.service.impl;

import com.proyecto.cakecandy_producto_service.application.service.CategoriaService;
import com.proyecto.cakecandy_producto_service.domain.model.Categoria;
import com.proyecto.cakecandy_producto_service.domain.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }
}