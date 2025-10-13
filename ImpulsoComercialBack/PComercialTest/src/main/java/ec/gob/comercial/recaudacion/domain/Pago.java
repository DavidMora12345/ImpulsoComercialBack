package ec.gob.comercial.recaudacion.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad Pago
 * 
 * Representa un pago realizado por un cliente
 */
@Entity
@Table(name = "pago", schema = "recaudacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_recibo", unique = true, nullable = false, length = 50)
    private String numeroRecibo;
    
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    @Column(name = "id_caja", nullable = false)
    private Long idCaja; // Caja recaudadora
    
    @Column(name = "id_forma_pago", nullable = false)
    private Long idFormaPago; // 1=Efectivo, 2=Tarjeta, 3=Transferencia, etc.
    
    // Valores
    @Column(name = "total_pago", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPago;
    
    // Fechas
    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;
    
    // Referencia bancaria (para tarjeta/transferencia)
    @Column(name = "referencia_bancaria", length = 100)
    private String referenciaBancaria;
}
