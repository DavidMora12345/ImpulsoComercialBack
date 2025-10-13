package ec.gob.comercial.catastros.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidad Construcción
 * 
 * Representa una construcción dentro de un predio urbano
 * Un predio puede tener múltiples construcciones
 */
@Entity
@Table(name = "construccion", schema = "catastros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Construccion extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_predio", nullable = false)
    private Long idPredio;
    
    @Column(name = "id_tipo_construccion", nullable = false)
    private Long idTipoConstruccion; // Casa, Edificio, Local Comercial, etc.
    
    @Column(name = "numero_pisos")
    private Integer numeroPisos;
    
    @Column(name = "area_construccion", nullable = false, precision = 10, scale = 2)
    private BigDecimal areaConstruccion; // m²
    
    @Column(name = "anio_construccion")
    private Integer anioConstruccion;
    
    @Column(name = "estado_conservacion", length = 50)
    private String estadoConservacion; // Bueno, Regular, Malo
    
    @Column(name = "porcentaje_avance", precision = 5, scale = 2)
    private BigDecimal porcentajeAvance; // % de avance de obra
    
    @Column(name = "valor_construccion", precision = 12, scale = 2)
    private BigDecimal valorConstruccion;
    
    /**
     * Calcula la edad de la construcción
     */
    @Transient
    public Integer getEdadConstruccion() {
        if (anioConstruccion == null) {
            return null;
        }
        return java.time.Year.now().getValue() - anioConstruccion;
    }
}
