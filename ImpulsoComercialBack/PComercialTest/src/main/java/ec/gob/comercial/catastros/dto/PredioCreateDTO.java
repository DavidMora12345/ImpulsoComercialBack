package ec.gob.comercial.catastros.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para crear un Predio
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredioCreateDTO {
    
    @NotBlank(message = "La clave catastral es obligatoria")
    @Size(max = 50, message = "La clave catastral no puede exceder 50 caracteres")
    private String claveCatastral;
    
    @NotNull(message = "El cliente es obligatorio")
    private Long idCliente;
    
    @NotNull(message = "La zona es obligatoria")
    private Long idZona;
    
    @NotNull(message = "La parroquia es obligatoria")
    private Long idParroquia;
    
    @NotNull(message = "El sector es obligatorio")
    private Long idSector;
    
    @NotNull(message = "La manzana es obligatoria")
    private Long idManzana;
    
    @NotBlank(message = "El lote es obligatorio")
    @Size(max = 10, message = "El lote no puede exceder 10 caracteres")
    private String lote;
    
    @Size(max = 10, message = "La unidad no puede exceder 10 caracteres")
    private String unidad;
    
    @Size(max = 10, message = "La división no puede exceder 10 caracteres")
    private String division;
    
    @NotNull(message = "El área del terreno es obligatoria")
    @DecimalMin(value = "0.01", message = "El área del terreno debe ser mayor a 0")
    private BigDecimal areaTerreno;
    
    @DecimalMin(value = "0.0", message = "El área de construcción debe ser mayor o igual a 0")
    private BigDecimal areaConstruccion;
    
    @DecimalMin(value = "0.0", message = "El frente debe ser mayor o igual a 0")
    private BigDecimal frente;
    
    @DecimalMin(value = "0.0", message = "El fondo debe ser mayor o igual a 0")
    private BigDecimal fondo;
    
    @NotBlank(message = "La calle principal es obligatoria")
    @Size(max = 200, message = "La calle principal no puede exceder 200 caracteres")
    private String callePrincipal;
    
    @Size(max = 200, message = "La calle secundaria no puede exceder 200 caracteres")
    private String calleSecundaria;
    
    @Size(max = 20, message = "El número de casa no puede exceder 20 caracteres")
    private String numeroCasa;
    
    private String referencia;
    
    // GIS - Coordenadas del polígono en formato WKT
    private String geometriaWKT;
}
