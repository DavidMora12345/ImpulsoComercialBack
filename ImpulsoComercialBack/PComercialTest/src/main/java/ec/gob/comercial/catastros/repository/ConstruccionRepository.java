package ec.gob.comercial.catastros.repository;

import ec.gob.comercial.catastros.domain.Construccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para Construcción
 */
@Repository
public interface ConstruccionRepository extends JpaRepository<Construccion, Long> {
    
    /**
     * Obtiene todas las construcciones de un predio
     */
    @Query("SELECT c FROM Construccion c WHERE c.idPredio = :idPredio AND c.estado = 1")
    List<Construccion> findByIdPredio(@Param("idPredio") Long idPredio);
    
    /**
     * Cuenta las construcciones de un predio
     */
    @Query("SELECT COUNT(c) FROM Construccion c WHERE c.idPredio = :idPredio AND c.estado = 1")
    Long countByIdPredio(@Param("idPredio") Long idPredio);
    
    /**
     * Obtiene construcciones por tipo
     */
    @Query("SELECT c FROM Construccion c WHERE c.idTipoConstruccion = :idTipo AND c.estado = 1")
    List<Construccion> findByIdTipoConstruccion(@Param("idTipo") Long idTipo);
}
