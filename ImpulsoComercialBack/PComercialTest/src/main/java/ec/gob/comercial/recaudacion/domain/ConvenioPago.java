package ec.gob.comercial.recaudacion.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad Convenio de Pago
 * 
 * Representa un acuerdo de pago en cuotas (facilidades de pago)
 */
@Entity
@Table(name = "convenio_pago", schema = "recaudacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConvenioPago extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_convenio", unique = true, nullable = false, length = 50)
    private String numeroConvenio;
    
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    // Valores
    @Column(name = "valor_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;
    
    @Column(name = "valor_entrada", precision = 12, scale = 2)
    private BigDecimal valorEntrada;
    
    @Column(name = "valor_cuotas", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorCuotas;
    
    @Column(name = "numero_cuotas", nullable = false)
    private Integer numeroCuotas;
    
    // Fechas
    @Column(name = "fecha_convenio", nullable = false)
    private LocalDate fechaConvenio;
    
    @Column(name = "fecha_primera_cuota", nullable = false)
    private LocalDate fechaPrimeraCuota;
    
    // Estado
    @Column(name = "cumplido")
    private Boolean cumplido = false;
}
