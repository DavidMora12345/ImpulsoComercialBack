package ec.gob.comercial.clientes.repository;

import ec.gob.comercial.clientes.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para Cliente
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Busca un cliente por cédula
     */
    Optional<Cliente> findByCedula(String cedula);
    
    /**
     * Busca un cliente por email
     */
    Optional<Cliente> findByEmail(String email);
    
    /**
     * Verifica si existe un cliente con la cédula dada
     */
    boolean existsByCedula(String cedula);
    
    /**
     * Verifica si existe un cliente con el email dado
     */
    boolean existsByEmail(String email);
    
    /**
     * Búsqueda avanzada de clientes
     * 
     * Busca por: nombre, apellido, cédula o email
     */
    @Query("""
        SELECT c FROM Cliente c
        WHERE c.estado = 1
        AND (
            LOWER(c.nombre) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(c.cedula) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%'))
        )
        """)
    Page<Cliente> searchClientes(@Param("search") String search, Pageable pageable);
    
    /**
     * Obtiene todos los clientes activos
     */
    @Query("SELECT c FROM Cliente c WHERE c.estado = 1")
    Page<Cliente> findAllActivos(Pageable pageable);
    
    /**
     * Obtiene clientes con tercera edad
     */
    @Query("""
        SELECT c FROM Cliente c
        WHERE c.estado = 1
        AND EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM c.fechaNacimiento) > 64
        """)
    Page<Cliente> findClientesTerceraEdad(Pageable pageable);
    
    /**
     * Obtiene clientes con discapacidad
     */
    @Query("""
        SELECT c FROM Cliente c
        WHERE c.estado = 1
        AND c.discapacitado = 1
        AND c.porcentajeDiscapacidad >= 0.50
        """)
    Page<Cliente> findClientesConDiscapacidad(Pageable pageable);
}
