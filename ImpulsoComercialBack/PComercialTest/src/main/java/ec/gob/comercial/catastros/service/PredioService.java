package ec.gob.comercial.catastros.service;

import ec.gob.comercial.catastros.dto.PredioCreateDTO;
import ec.gob.comercial.catastros.dto.PredioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Servicio para Predio
 */
public interface PredioService {
    
    Page<PredioDTO> findAll(Pageable pageable);
    
    Page<PredioDTO> search(String search, Pageable pageable);
    
    PredioDTO findById(Long id);
    
    PredioDTO findByClaveCatastral(String claveCatastral);
    
    Page<PredioDTO> findByIdCliente(Long idCliente, Pageable pageable);
    
    PredioDTO create(PredioCreateDTO dto);
    
    void delete(Long id);
    
    List<PredioDTO> findPrediosNearby(double latitude, double longitude, double radiusMeters);
}
