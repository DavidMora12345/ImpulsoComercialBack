package ec.gob.comercial.catastros.repository;

import ec.gob.comercial.catastros.domain.Predio;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Predio (Urbano)
 * 
 * Incluye queries espaciales con PostGIS
 */
@Repository
public interface PredioRepository extends JpaRepository<Predio, Long> {
    
    /**
     * Busca un predio por clave catastral
     */
    Optional<Predio> findByClaveCatastral(String claveCatastral);
    
    /**
     * Verifica si existe un predio con la clave catastral dada
     */
    boolean existsByClaveCatastral(String claveCatastral);
    
    /**
     * Obtiene todos los predios de un cliente
     */
    @Query("SELECT p FROM Predio p WHERE p.idCliente = :idCliente AND p.estado = 1")
    Page<Predio> findByIdCliente(@Param("idCliente") Long idCliente, Pageable pageable);
    
    /**
     * Búsqueda avanzada de predios
     */
    @Query("""
        SELECT p FROM Predio p
        WHERE p.estado = 1
        AND (
            LOWER(p.claveCatastral) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(p.callePrincipal) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(p.numeroCasa) LIKE LOWER(CONCAT('%', :search, '%'))
        )
        """)
    Page<Predio> searchPredios(@Param("search") String search, Pageable pageable);
    
    /**
     * Obtiene predios por zona
     */
    @Query("SELECT p FROM Predio p WHERE p.idZona = :idZona AND p.estado = 1")
    Page<Predio> findByIdZona(@Param("idZona") Long idZona, Pageable pageable);
    
    /**
     * Obtiene predios por parroquia
     */
    @Query("SELECT p FROM Predio p WHERE p.idParroquia = :idParroquia AND p.estado = 1")
    Page<Predio> findByIdParroquia(@Param("idParroquia") Long idParroquia, Pageable pageable);
    
    /**
     * Obtiene predios por sector
     */
    @Query("SELECT p FROM Predio p WHERE p.idSector = :idSector AND p.estado = 1")
    Page<Predio> findByIdSector(@Param("idSector") Long idSector, Pageable pageable);
    
    /**
     * Query espacial: Encuentra predios dentro de un radio (en metros)
     * Usa PostGIS ST_DWithin
     */
    @Query(value = """
        SELECT * FROM catastros.predio p
        WHERE p.estado = 1
        AND ST_DWithin(
            p.geometria::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
            :radiusMeters
        )
        ORDER BY ST_Distance(
            p.geometria::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
        )
        """, nativeQuery = true)
    List<Predio> findPrediosNearby(
        @Param("latitude") double latitude,
        @Param("longitude") double longitude,
        @Param("radiusMeters") double radiusMeters
    );
    
    /**
     * Query espacial: Encuentra predios que contienen un punto
     * Usa PostGIS ST_Contains
     */
    @Query(value = """
        SELECT * FROM catastros.predio p
        WHERE p.estado = 1
        AND ST_Contains(
            p.geometria,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)
        )
        """, nativeQuery = true)
    Optional<Predio> findPredioByPoint(
        @Param("latitude") double latitude,
        @Param("longitude") double longitude
    );
    
    /**
     * Obtiene todos los predios activos
     */
    @Query("SELECT p FROM Predio p WHERE p.estado = 1")
    Page<Predio> findAllActivos(Pageable pageable);
}
