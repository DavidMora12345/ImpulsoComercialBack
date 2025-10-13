package ec.gob.comercial.recaudacion.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad Factura
 * 
 * Representa una factura emitida a un cliente
 * Puede ser de: Predial, Agua, Patente, etc.
 */
@Entity
@Table(name = "factura", schema = "recaudacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_factura", unique = true, nullable = false, length = 50)
    private String numeroFactura;
    
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    @Column(name = "id_modulo", nullable = false)
    private Long idModulo; // 1=Predial, 2=Agua, 3=Patente, etc.
    
    // Fechas
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;
    
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;
    
    // Valores
    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
    
    @Column(name = "descuento", precision = 12, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO;
    
    @Column(name = "iva", precision = 12, scale = 2)
    private BigDecimal iva = BigDecimal.ZERO;
    
    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;
    
    @Column(name = "saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo; // Pendiente de pago
    
    // Estado
    @Column(name = "pagado")
    private Boolean pagado = false;
    
    @Column(name = "convenio_pago")
    private Boolean convenioPago = false;
    
    /**
     * Verifica si la factura está vencida
     */
    @Transient
    public boolean isVencida() {
        return !pagado && LocalDate.now().isAfter(fechaVencimiento);
    }
    
    /**
     * Calcula los días de mora
     */
    @Transient
    public int getDiasMora() {
        if (pagado || !isVencida()) {
            return 0;
        }
        return (int) java.time.temporal.ChronoUnit.DAYS.between(fechaVencimiento, LocalDate.now());
    }
}
