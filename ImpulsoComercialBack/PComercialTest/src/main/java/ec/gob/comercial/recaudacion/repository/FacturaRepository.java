package ec.gob.comercial.recaudacion.repository;

import ec.gob.comercial.recaudacion.domain.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Factura
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    
    /**
     * Busca una factura por número
     */
    Optional<Factura> findByNumeroFactura(String numeroFactura);
    
    /**
     * Obtiene todas las facturas de un cliente
     */
    @Query("SELECT f FROM Factura f WHERE f.idCliente = :idCliente AND f.estado = 1 ORDER BY f.fechaEmision DESC")
    Page<Factura> findByIdCliente(@Param("idCliente") Long idCliente, Pageable pageable);
    
    /**
     * Obtiene facturas pendientes de pago de un cliente
     */
    @Query("SELECT f FROM Factura f WHERE f.idCliente = :idCliente AND f.pagado = false AND f.estado = 1 ORDER BY f.fechaVencimiento")
    List<Factura> findFacturasPendientesByIdCliente(@Param("idCliente") Long idCliente);
    
    /**
     * Obtiene facturas vencidas
     */
    @Query("SELECT f FROM Factura f WHERE f.pagado = false AND f.fechaVencimiento < :fecha AND f.estado = 1")
    Page<Factura> findFacturasVencidas(@Param("fecha") LocalDate fecha, Pageable pageable);
    
    /**
     * Obtiene facturas por módulo
     */
    @Query("SELECT f FROM Factura f WHERE f.idModulo = :idModulo AND f.estado = 1")
    Page<Factura> findByIdModulo(@Param("idModulo") Long idModulo, Pageable pageable);
    
    /**
     * Cuenta facturas pendientes de un cliente
     */
    @Query("SELECT COUNT(f) FROM Factura f WHERE f.idCliente = :idCliente AND f.pagado = false AND f.estado = 1")
    Long countFacturasPendientesByIdCliente(@Param("idCliente") Long idCliente);
    
    /**
     * Suma el saldo pendiente de un cliente
     */
    @Query("SELECT COALESCE(SUM(f.saldo), 0) FROM Factura f WHERE f.idCliente = :idCliente AND f.pagado = false AND f.estado = 1")
    java.math.BigDecimal sumSaldoPendienteByIdCliente(@Param("idCliente") Long idCliente);
}
