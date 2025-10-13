package ec.gob.comercial.catastros.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidad Avalúo Rural
 * 
 * Representa el avalúo catastral de un predio rural
 * Valores en base a hectáreas y uso del suelo
 */
@Entity
@Table(name = "avaluo_rural", schema = "catastros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaluoRural extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_predio_rural", nullable = false)
    private Long idPredioRural;
    
    @Column(name = "anio", nullable = false)
    private Integer anio;
    
    // Valores
    @Column(name = "valor_tierra", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTierra;
    
    @Column(name = "valor_mejoras", precision = 12, scale = 2)
    private BigDecimal valorMejoras;
    
    @Column(name = "valor_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;
    
    @Column(name = "valor_ha", precision = 10, scale = 2)
    private BigDecimal valorHa; // Valor por hectárea
    
    // Factores de ajuste
    @Column(name = "factor_uso_suelo", precision = 5, scale = 4)
    private BigDecimal factorUsoSuelo; // Agrícola/Ganadero/Forestal
    
    @Column(name = "factor_riego", precision = 5, scale = 4)
    private BigDecimal factorRiego; // Riego/Secano
    
    @Column(name = "factor_acceso", precision = 5, scale = 4)
    private BigDecimal factorAcceso; // Bueno/Regular/Malo
    
    @Column(name = "factor_topografia", precision = 5, scale = 4)
    private BigDecimal factorTopografia; // Plano/Ondulado/Montañoso
    
    /**
     * Calcula el factor total aplicado
     */
    @Transient
    public BigDecimal getFactorTotal() {
        BigDecimal factor = BigDecimal.ONE;
        
        if (factorUsoSuelo != null) {
            factor = factor.multiply(factorUsoSuelo);
        }
        if (factorRiego != null) {
            factor = factor.multiply(factorRiego);
        }
        if (factorAcceso != null) {
            factor = factor.multiply(factorAcceso);
        }
        if (factorTopografia != null) {
            factor = factor.multiply(factorTopografia);
        }
        
        return factor;
    }
}
