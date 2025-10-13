package ec.gob.comercial.catastros.repository;

import ec.gob.comercial.catastros.domain.PredioRural;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Predio Rural
 */
@Repository
public interface PredioRuralRepository extends JpaRepository<PredioRural, Long> {
    
    /**
     * Busca un predio rural por clave catastral
     */
    Optional<PredioRural> findByClaveCatastralRural(String claveCatastralRural);
    
    /**
     * Verifica si existe un predio rural con la clave catastral dada
     */
    boolean existsByClaveCatastralRural(String claveCatastralRural);
    
    /**
     * Obtiene todos los predios rurales de un cliente
     */
    @Query("SELECT pr FROM PredioRural pr WHERE pr.idCliente = :idCliente AND pr.estado = 1")
    Page<PredioRural> findByIdCliente(@Param("idCliente") Long idCliente, Pageable pageable);
    
    /**
     * Búsqueda avanzada de predios rurales
     */
    @Query("""
        SELECT pr FROM PredioRural pr
        WHERE pr.estado = 1
        AND (
            LOWER(pr.claveCatastralRural) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(pr.nombrePredio) LIKE LOWER(CONCAT('%', :search, '%'))
        )
        """)
    Page<PredioRural> searchPrediosRurales(@Param("search") String search, Pageable pageable);
    
    /**
     * Obtiene predios rurales por parroquia
     */
    @Query("SELECT pr FROM PredioRural pr WHERE pr.idParroquiaRural = :idParroquia AND pr.estado = 1")
    Page<PredioRural> findByIdParroquiaRural(@Param("idParroquia") Long idParroquia, Pageable pageable);
    
    /**
     * Obtiene predios rurales por recinto
     */
    @Query("SELECT pr FROM PredioRural pr WHERE pr.idRecinto = :idRecinto AND pr.estado = 1")
    Page<PredioRural> findByIdRecinto(@Param("idRecinto") Long idRecinto, Pageable pageable);
    
    /**
     * Obtiene predios rurales por uso de suelo
     */
    @Query("SELECT pr FROM PredioRural pr WHERE pr.idUsoSuelo = :idUsoSuelo AND pr.estado = 1")
    Page<PredioRural> findByIdUsoSuelo(@Param("idUsoSuelo") Long idUsoSuelo, Pageable pageable);
    
    /**
     * Query espacial: Encuentra predios rurales cercanos
     */
    @Query(value = """
        SELECT * FROM catastros.predio_rural pr
        WHERE pr.estado = 1
        AND ST_DWithin(
            pr.geometria::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
            :radiusMeters
        )
        ORDER BY ST_Distance(
            pr.geometria::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
        )
        """, nativeQuery = true)
    List<PredioRural> findPrediosRuralesNearby(
        @Param("latitude") double latitude,
        @Param("longitude") double longitude,
        @Param("radiusMeters") double radiusMeters
    );
    
    /**
     * Obtiene todos los predios rurales activos
     */
    @Query("SELECT pr FROM PredioRural pr WHERE pr.estado = 1")
    Page<PredioRural> findAllActivos(Pageable pageable);
}
