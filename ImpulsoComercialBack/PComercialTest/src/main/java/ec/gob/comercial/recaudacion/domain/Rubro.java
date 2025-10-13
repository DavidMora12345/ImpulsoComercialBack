package ec.gob.comercial.recaudacion.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidad Rubro
 * 
 * Representa un concepto de cobro (impuesto predial, agua, basura, etc.)
 */
@Entity
@Table(name = "rubro", schema = "recaudacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rubro extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo", unique = true, nullable = false, length = 20)
    private String codigo;
    
    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;
    
    @Column(name = "id_modulo", nullable = false)
    private Long idModulo; // 1=Predial, 2=Agua, 3=Patente
    
    // Configuración
    @Column(name = "iva")
    private Boolean iva = false; // Aplica IVA
    
    @Column(name = "calculable")
    private Boolean calculable = false; // Se calcula automáticamente
    
    @Column(name = "valor_fijo", precision = 12, scale = 2)
    private BigDecimal valorFijo; // Valor fijo si aplica
}
