package ec.gob.comercial.catastros.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;

/**
 * Entidad Predio (Urbano)
 * 
 * Representa un predio urbano con su ubicación y características
 * Incluye soporte PostGIS para geometrías
 */
@Entity
@Table(name = "predio", schema = "catastros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Predio extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Clave catastral: ZZ-PP-SS-MMM-LLL-UU-DD
    @Column(name = "clave_catastral", unique = true, nullable = false, length = 50)
    private String claveCatastral;
    
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    // Ubicación administrativa
    @Column(name = "id_zona", nullable = false)
    private Long idZona;
    
    @Column(name = "id_parroquia", nullable = false)
    private Long idParroquia;
    
    @Column(name = "id_sector", nullable = false)
    private Long idSector;
    
    @Column(name = "id_manzana", nullable = false)
    private Long idManzana;
    
    @Column(name = "lote", nullable = false, length = 10)
    private String lote;
    
    @Column(name = "unidad", length = 10)
    private String unidad; // Para propiedad horizontal
    
    @Column(name = "division", length = 10)
    private String division;
    
    // Dimensiones
    @Column(name = "area_terreno", nullable = false, precision = 10, scale = 2)
    private BigDecimal areaTerreno; // m²
    
    @Column(name = "area_construccion", precision = 10, scale = 2)
    private BigDecimal areaConstruccion; // m²
    
    @Column(name = "frente", precision = 10, scale = 2)
    private BigDecimal frente; // metros
    
    @Column(name = "fondo", precision = 10, scale = 2)
    private BigDecimal fondo; // metros
    
    // Dirección
    @Column(name = "calle_principal", nullable = false, length = 200)
    private String callePrincipal;
    
    @Column(name = "calle_secundaria", length = 200)
    private String calleSecundaria;
    
    @Column(name = "numero_casa", length = 20)
    private String numeroCasa;
    
    @Column(name = "referencia", columnDefinition = "TEXT")
    private String referencia;
    
    // GIS - PostGIS
    @Column(name = "geometria", columnDefinition = "geometry(Polygon,4326)")
    private Polygon geometria;
    
    /**
     * Retorna la dirección completa del predio
     */
    @Transient
    public String getDireccionCompleta() {
        StringBuilder direccion = new StringBuilder(callePrincipal);
        
        if (calleSecundaria != null && !calleSecundaria.isEmpty()) {
            direccion.append(" y ").append(calleSecundaria);
        }
        
        if (numeroCasa != null && !numeroCasa.isEmpty()) {
            direccion.append(" N° ").append(numeroCasa);
        }
        
        return direccion.toString();
    }
}
