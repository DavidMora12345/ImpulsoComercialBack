package ec.gob.comercial.clientes.domain;

import ec.gob.comercial.shared.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad Cliente
 * 
 * Representa un cliente/contribuyente del municipio
 * Puede ser: Persona Natural, Persona Jurídica o Extranjero
 */
@Entity
@Table(name = "cliente", schema = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Identificación
    @Column(name = "cedula", unique = true, length = 20)
    private String cedula;
    
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "nombre_comercial", length = 250)
    private String nombreComercial;
    
    // Contacto
    @Column(name = "direccion", length = 200)
    private String direccion;
    
    @Column(name = "telefono", length = 60)
    private String telefono;
    
    @Column(name = "celular", length = 10)
    private String celular;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    // Información personal
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "estado_civil", length = 20)
    private String estadoCivil;
    
    // Descuentos
    @Column(name = "discapacitado")
    private Integer discapacitado = 0; // 0=No, 1=Sí
    
    @Column(name = "porcentaje_discapacidad", precision = 17, scale = 17)
    private BigDecimal porcentajeDiscapacidad;
    
    @Column(name = "entidad_religiosa_exonerada")
    private Integer entidadReligiosaExonerada = 0;
    
    @Column(name = "porcentaje_exoneracion", precision = 17, scale = 17)
    private BigDecimal porcentajeExoneracion;
    
    // Tipo de cliente
    @Column(name = "id_tipo_personeria_juridica", nullable = false)
    private Long idTipoPersoneriaJuridica; // 1=Natural, 2=Jurídica
    
    @Column(name = "id_nacionalidad")
    private Long idNacionalidad;
    
    // Relaciones
    @Column(name = "id_conyugue")
    private Long idConyugue;
    
    @Column(name = "id_representante_legal")
    private Long idRepresentanteLegal;
    
    // Portal Ciudadano
    @Column(name = "password", length = 255)
    private String password;
    
    @Column(name = "email_verificado")
    private Integer emailVerificado = 0;
    
    @Column(name = "email_verificacion_token")
    private String emailVerificacionToken;
    
    @Column(name = "portal_consulta")
    private Integer portalConsulta = 0;
    
    @Column(name = "device_android_token")
    private String deviceAndroidToken;
    
    @Column(name = "device_ios_token")
    private String deviceIosToken;
    
    /**
     * Calcula si el cliente es tercera edad (> 64 años)
     */
    @Transient
    public boolean isTerceraEdad() {
        if (fechaNacimiento == null) {
            return false;
        }
        return LocalDate.now().getYear() - fechaNacimiento.getYear() > 64;
    }
    
    /**
     * Calcula si el cliente tiene descuento por discapacidad (>= 50%)
     */
    @Transient
    public boolean tieneDescuentoDiscapacidad() {
        if (discapacitado == 0 || porcentajeDiscapacidad == null) {
            return false;
        }
        return porcentajeDiscapacidad.compareTo(new BigDecimal("0.50")) >= 0;
    }
    
    /**
     * Retorna el nombre completo del cliente
     */
    @Transient
    public String getNombreCompleto() {
        return apellido + " " + nombre;
    }
}
