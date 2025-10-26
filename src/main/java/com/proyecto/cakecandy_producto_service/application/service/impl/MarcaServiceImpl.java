package com.proyecto.cakecandy_producto_service.application.service.impl;

import com.proyecto.cakecandy_producto_service.application.service.MarcaService;
import com.proyecto.cakecandy_producto_service.domain.model.Marca;
import com.proyecto.cakecandy_producto_service.domain.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;

    @Override
    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }
}