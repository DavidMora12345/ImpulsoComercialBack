package ec.gob.comercial.recaudacion.repository;

import ec.gob.comercial.recaudacion.domain.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repositorio para Pago
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    
    /**
     * Busca un pago por número de recibo
     */
    Optional<Pago> findByNumeroRecibo(String numeroRecibo);
    
    /**
     * Obtiene todos los pagos de un cliente
     */
    @Query("SELECT p FROM Pago p WHERE p.idCliente = :idCliente AND p.estado = 1 ORDER BY p.fechaPago DESC")
    Page<Pago> findByIdCliente(@Param("idCliente") Long idCliente, Pageable pageable);
    
    /**
     * Obtiene pagos por rango de fechas
     */
    @Query("SELECT p FROM Pago p WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin AND p.estado = 1")
    Page<Pago> findByFechaPagoBetween(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin,
        Pageable pageable
    );
    
    /**
     * Obtiene pagos por caja
     */
    @Query("SELECT p FROM Pago p WHERE p.idCaja = :idCaja AND p.estado = 1 ORDER BY p.fechaPago DESC")
    Page<Pago> findByIdCaja(@Param("idCaja") Long idCaja, Pageable pageable);
    
    /**
     * Suma total recaudado por fecha
     */
    @Query("SELECT COALESCE(SUM(p.totalPago), 0) FROM Pago p WHERE DATE(p.fechaPago) = :fecha AND p.estado = 1")
    java.math.BigDecimal sumTotalRecaudadoByFecha(@Param("fecha") java.time.LocalDate fecha);
}
