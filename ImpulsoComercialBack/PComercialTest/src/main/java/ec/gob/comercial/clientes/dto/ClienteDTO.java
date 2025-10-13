package ec.gob.comercial.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para Cliente (respuesta)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String cedula;
    private String apellido;
    private String nombre;
    private String nombreCompleto;
    private String nombreComercial;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    private LocalDate fechaNacimiento;
    private String estadoCivil;
    private Integer discapacitado;
    private BigDecimal porcentajeDiscapacidad;
    private Integer entidadReligiosaExonerada;
    private BigDecimal porcentajeExoneracion;
    private Long idTipoPersoneriaJuridica;
    private Long idNacionalidad;
    private Long idConyugue;
    private Long idRepresentanteLegal;
    private Integer estado;
    private boolean terceraEdad;
    private boolean tieneDescuentoDiscapacidad;
}
