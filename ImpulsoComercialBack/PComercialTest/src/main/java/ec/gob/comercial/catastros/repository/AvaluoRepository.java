package ec.gob.comercial.catastros.repository;

import ec.gob.comercial.catastros.domain.Avaluo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para Avalúo (Urbano)
 */
@Repository
public interface AvaluoRepository extends JpaRepository<Avaluo, Long> {
    
    /**
     * Obtiene el avalúo de un predio para un año específico
     */
    @Query("SELECT a FROM Avaluo a WHERE a.idPredio = :idPredio AND a.anio = :anio AND a.estado = 1")
    Optional<Avaluo> findByIdPredioAndAnio(@Param("idPredio") Long idPredio, @Param("anio") Integer anio);
    
    /**
     * Obtiene el avalúo más reciente de un predio
     */
    @Query("""
        SELECT a FROM Avaluo a
        WHERE a.idPredio = :idPredio AND a.estado = 1
        ORDER BY a.anio DESC
        LIMIT 1
        """)
    Optional<Avaluo> findUltimoAvaluoByIdPredio(@Param("idPredio") Long idPredio);
    
    /**
     * Obtiene todos los avalúos de un predio
     */
    @Query("SELECT a FROM Avaluo a WHERE a.idPredio = :idPredio AND a.estado = 1 ORDER BY a.anio DESC")
    Page<Avaluo> findByIdPredio(@Param("idPredio") Long idPredio, Pageable pageable);
    
    /**
     * Obtiene todos los avalúos de un año específico
     */
    @Query("SELECT a FROM Avaluo a WHERE a.anio = :anio AND a.estado = 1")
    Page<Avaluo> findByAnio(@Param("anio") Integer anio, Pageable pageable);
    
    /**
     * Verifica si existe un avalúo para un predio en un año
     */
    boolean existsByIdPredioAndAnio(Long idPredio, Integer anio);
}
