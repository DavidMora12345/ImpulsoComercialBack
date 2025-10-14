package ec.gob.comercial.shared.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entidad base auditable
 * 
 * Todas las entidades que extiendan de esta tendrán:
 * - Fecha de creación
 * - Fecha de última modificación
 * - Usuario que creó
 * - Usuario que modificó
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {
    
    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "usuario_creacion")
    private Long usuarioCreacion;
    
    @Column(name = "usuario_actualizacion")
    private Long usuarioActualizacion;
    
    @Column(name = "estado")
    private Integer estado = 1; // 1=Activo, 0=Inactivo

    @PrePersist
    protected void prePersist() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
