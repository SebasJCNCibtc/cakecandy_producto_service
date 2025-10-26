package com.proyecto.cakecandy_producto_service.domain.repository;

import com.proyecto.cakecandy_producto_service.domain.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
}