package ec.gob.comercial.recaudacion.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidad Factura Detalle
 * 
 * Representa los rubros/conceptos de una factura
 */
@Entity
@Table(name = "factura_detalle", schema = "recaudacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaDetalle extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_factura", nullable = false)
    private Long idFactura;
    
    @Column(name = "id_rubro", nullable = false)
    private Long idRubro;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "cantidad")
    private Integer cantidad = 1;
    
    @Column(name = "valor_unitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorUnitario;
    
    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
    
    @Column(name = "descuento", precision = 12, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO;
    
    @Column(name = "iva", precision = 12, scale = 2)
    private BigDecimal iva = BigDecimal.ZERO;
    
    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;
}
