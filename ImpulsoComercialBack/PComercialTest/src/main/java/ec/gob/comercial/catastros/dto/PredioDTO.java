package ec.gob.comercial.catastros.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para Predio (respuesta)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredioDTO {
    private Long id;
    private String claveCatastral;
    private Long idCliente;
    private String nombreCliente;
    
    // Ubicación
    private Long idZona;
    private Long idParroquia;
    private Long idSector;
    private Long idManzana;
    private String lote;
    private String unidad;
    private String division;
    
    // Dimensiones
    private BigDecimal areaTerreno;
    private BigDecimal areaConstruccion;
    private BigDecimal frente;
    private BigDecimal fondo;
    
    // Dirección
    private String callePrincipal;
    private String calleSecundaria;
    private String numeroCasa;
    private String referencia;
    private String direccionCompleta;
    
    // GIS
    private String geometriaWKT; // Well-Known Text
    
    private Integer estado;
}
