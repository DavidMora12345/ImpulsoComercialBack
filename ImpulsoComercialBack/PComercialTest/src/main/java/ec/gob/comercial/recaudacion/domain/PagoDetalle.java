package ec.gob.comercial.recaudacion.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidad Pago Detalle
 * 
 * Relaciona un pago con las facturas que cancela
 * Relación N:M entre Pago y Factura
 */
@Entity
@Table(name = "pago_detalle", schema = "recaudacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoDetalle extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_pago", nullable = false)
    private Long idPago;
    
    @Column(name = "id_factura", nullable = false)
    private Long idFactura;
    
    @Column(name = "valor_pagado", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorPagado;
    
    @Column(name = "interes_mora", precision = 12, scale = 2)
    private BigDecimal interesMora = BigDecimal.ZERO;
    
    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;
}
