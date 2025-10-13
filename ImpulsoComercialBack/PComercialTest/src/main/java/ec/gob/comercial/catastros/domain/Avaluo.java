package ec.gob.comercial.catastros.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidad Avalúo (Urbano)
 * 
 * Representa el avalúo catastral de un predio urbano
 * Se genera anualmente para el cálculo del impuesto predial
 */
@Entity
@Table(name = "avaluo", schema = "catastros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avaluo extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_predio", nullable = false)
    private Long idPredio;
    
    @Column(name = "anio", nullable = false)
    private Integer anio;
    
    // Valores
    @Column(name = "valor_terreno", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTerreno;
    
    @Column(name = "valor_construccion", precision = 12, scale = 2)
    private BigDecimal valorConstruccion;
    
    @Column(name = "valor_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;
    
    @Column(name = "valor_m2_terreno", precision = 10, scale = 2)
    private BigDecimal valorM2Terreno;
    
    // Factores de ajuste
    @Column(name = "factor_forma", precision = 5, scale = 4)
    private BigDecimal factorForma; // Regular/Irregular
    
    @Column(name = "factor_topografia", precision = 5, scale = 4)
    private BigDecimal factorTopografia; // Plano/Pendiente
    
    @Column(name = "factor_ubicacion", precision = 5, scale = 4)
    private BigDecimal factorUbicacion; // Esquina/Intermedio
    
    @Column(name = "factor_servicios", precision = 5, scale = 4)
    private BigDecimal factorServicios; // Todos/Algunos/Ninguno
    
    @Column(name = "factor_vias", precision = 5, scale = 4)
    private BigDecimal factorVias; // Asfaltado/Lastrado/Tierra
    
    /**
     * Calcula el factor total aplicado
     */
    @Transient
    public BigDecimal getFactorTotal() {
        BigDecimal factor = BigDecimal.ONE;
        
        if (factorForma != null) {
            factor = factor.multiply(factorForma);
        }
        if (factorTopografia != null) {
            factor = factor.multiply(factorTopografia);
        }
        if (factorUbicacion != null) {
            factor = factor.multiply(factorUbicacion);
        }
        if (factorServicios != null) {
            factor = factor.multiply(factorServicios);
        }
        if (factorVias != null) {
            factor = factor.multiply(factorVias);
        }
        
        return factor;
    }
}
