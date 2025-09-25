package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "detalles_facturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFactura extends AbstractEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioTotal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;
}