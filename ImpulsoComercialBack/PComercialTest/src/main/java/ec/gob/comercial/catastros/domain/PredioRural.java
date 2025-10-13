package ec.gob.comercial.catastros.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;

/**
 * Entidad Predio Rural
 * 
 * Representa un predio rural con características agrícolas/ganaderas
 * Áreas en HECTÁREAS (no metros cuadrados)
 */
@Entity
@Table(name = "predio_rural", schema = "catastros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredioRural extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Clave catastral rural: ZZ-PP-RR-PPP-LLL
    @Column(name = "clave_catastral_rural", unique = true, nullable = false, length = 50)
    private String claveCatastralRural;
    
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    // Ubicación
    @Column(name = "id_zona_rural", nullable = false)
    private Long idZonaRural;
    
    @Column(name = "id_parroquia_rural", nullable = false)
    private Long idParroquiaRural;
    
    @Column(name = "id_recinto", nullable = false)
    private Long idRecinto; // Comunidad
    
    @Column(name = "predio", nullable = false, length = 10)
    private String predio;
    
    @Column(name = "lote", nullable = false, length = 10)
    private String lote;
    
    // Dimensiones en HECTÁREAS
    @Column(name = "area_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal areaTotal; // ha
    
    @Column(name = "area_cultivo", precision = 12, scale = 2)
    private BigDecimal areaCultivo; // ha
    
    @Column(name = "area_pasto", precision = 12, scale = 2)
    private BigDecimal areaPasto; // ha
    
    @Column(name = "area_bosque", precision = 12, scale = 2)
    private BigDecimal areaBosque; // ha
    
    @Column(name = "area_improductiva", precision = 12, scale = 2)
    private BigDecimal areaImproductiva; // ha
    
    // Características
    @Column(name = "id_uso_suelo", nullable = false)
    private Long idUsoSuelo; // Agrícola, Ganadero, Forestal, etc.
    
    @Column(name = "id_tipo_riego")
    private Long idTipoRiego; // Riego, Secano
    
    @Column(name = "acceso_vehicular")
    private Boolean accesoVehicular;
    
    @Column(name = "distancia_via_principal", precision = 10, scale = 2)
    private BigDecimal distanciaViaPrincipal; // km
    
    @Column(name = "nombre_predio", length = 200)
    private String nombrePredio;
    
    // GIS - PostGIS
    @Column(name = "geometria", columnDefinition = "geometry(Polygon,4326)")
    private Polygon geometria;
    
    /**
     * Calcula el área productiva total
     */
    @Transient
    public BigDecimal getAreaProductiva() {
        BigDecimal total = BigDecimal.ZERO;
        
        if (areaCultivo != null) {
            total = total.add(areaCultivo);
        }
        if (areaPasto != null) {
            total = total.add(areaPasto);
        }
        if (areaBosque != null) {
            total = total.add(areaBosque);
        }
        
        return total;
    }
}
