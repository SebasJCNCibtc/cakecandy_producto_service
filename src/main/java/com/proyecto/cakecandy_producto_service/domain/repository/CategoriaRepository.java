package com.proyecto.cakecandy_producto_service.domain.repository;

import com.proyecto.cakecandy_producto_service.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}