package ec.gob.comercial.clientes.service;

import ec.gob.comercial.clientes.dto.ClienteCreateDTO;
import ec.gob.comercial.clientes.dto.ClienteDTO;
import ec.gob.comercial.clientes.dto.ClienteUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.domain.Sort;

/**
 * Servicio para Cliente
 */
public interface ClienteService {
    
    /**
     * Obtiene todos los clientes activos (paginado)
     */
    Page<ClienteDTO> findAll(Pageable pageable);

    /**
     * Busca clientes por nombre, apellido, cédula o email
     */
    Page<ClienteDTO> search(String search, Pageable pageable);
    
    /**
     * Obtiene un cliente por ID
     */
    ClienteDTO findById(Long id);
    
    /**
     * Obtiene un cliente por cédula
     */
    ClienteDTO findByCedula(String cedula);
    
    /**
     * Crea un nuevo cliente
     */
    ClienteDTO create(ClienteCreateDTO dto);
    
    /**
     * Actualiza un cliente existente
     */
    ClienteDTO update(Long id, ClienteUpdateDTO dto);
    
    /**
     * Elimina un cliente (soft delete)
     */
    void delete(Long id);
    
    /**
     * Obtiene clientes con tercera edad
     */
    Page<ClienteDTO> findClientesTerceraEdad(Pageable pageable);
    
    /**
     * Obtiene clientes con discapacidad
     */
    Page<ClienteDTO> findClientesConDiscapacidad(Pageable pageable);

    List<ClienteDTO> findAllSinFiltro(Sort sort);

}
