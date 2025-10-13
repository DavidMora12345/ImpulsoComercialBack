package ec.gob.comercial.clientes.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para actualizar un Cliente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteUpdateDTO {
    
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String apellido;
    
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 250, message = "El nombre comercial no puede exceder 250 caracteres")
    private String nombreComercial;
    
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String direccion;
    
    @Size(max = 60, message = "El teléfono no puede exceder 60 caracteres")
    private String telefono;
    
    @Size(max = 10, message = "El celular no puede exceder 10 caracteres")
    @Pattern(regexp = "^[0-9]{10}$", message = "El celular debe tener 10 dígitos")
    private String celular;
    
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    private LocalDate fechaNacimiento;
    
    private String estadoCivil;
    
    @Min(value = 0, message = "Discapacitado debe ser 0 o 1")
    @Max(value = 1, message = "Discapacitado debe ser 0 o 1")
    private Integer discapacitado;
    
    @DecimalMin(value = "0.0", message = "El porcentaje de discapacidad debe ser mayor o igual a 0")
    @DecimalMax(value = "1.0", message = "El porcentaje de discapacidad debe ser menor o igual a 1")
    private BigDecimal porcentajeDiscapacidad;
    
    private Integer entidadReligiosaExonerada;
    
    private BigDecimal porcentajeExoneracion;
    
    private Long idNacionalidad;
    
    private Long idConyugue;
    
    private Long idRepresentanteLegal;
}
