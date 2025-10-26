package com.proyecto.cakecandy_producto_service.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "proveedor")
@Data
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @Column(name = "nombre_proveedor", nullable = false)
    private String nombreProveedor;

    private String contacto;
    private String telefono;
    private String email;
}