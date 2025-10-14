package ec.gob.comercial.clientes.service;

import ec.gob.comercial.clientes.domain.Cliente;
import ec.gob.comercial.clientes.dto.ClienteCreateDTO;
import ec.gob.comercial.clientes.dto.ClienteDTO;
import ec.gob.comercial.clientes.dto.ClienteUpdateDTO;
import ec.gob.comercial.clientes.mapper.ClienteMapper;
import ec.gob.comercial.clientes.repository.ClienteRepository;
import ec.gob.comercial.shared.exception.BusinessException;
import ec.gob.comercial.shared.exception.ResourceNotFoundException;
import ec.gob.comercial.shared.util.CedulaValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;                       // **AGREGAR**
import org.springframework.data.domain.Sort;

/**
 * Implementación del servicio de Cliente
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteServiceImpl implements ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        log.debug("Obteniendo todos los clientes activos");
        return clienteRepository.findAllActivos(pageable)
            .map(clienteMapper::toDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> search(String search, Pageable pageable) {
        log.debug("Buscando clientes con: {}", search);
        if (search == null || search.isBlank()) {
            return findAll(pageable);
        }
        return clienteRepository.searchClientes(search, pageable)
            .map(clienteMapper::toDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        log.debug("Buscando cliente con ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        return clienteMapper.toDTO(cliente);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ClienteDTO findByCedula(String cedula) {
        log.debug("Buscando cliente con cédula: {}", cedula);
        Cliente cliente = clienteRepository.findByCedula(cedula)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "cédula", cedula));
        return clienteMapper.toDTO(cliente);
    }
    
    @Override
    public ClienteDTO create(ClienteCreateDTO dto) {
        log.info("Creando nuevo cliente con cédula: {}", dto.getCedula());
        
        // Validar cédula
        if (!CedulaValidator.validar(dto.getCedula()) && !CedulaValidator.validarRuc(dto.getCedula())) {
            throw new BusinessException("La cédula/RUC ingresada no es válida");
        }
        
        // Verificar que no exista
        if (clienteRepository.existsByCedula(dto.getCedula())) {
            throw new BusinessException("Ya existe un cliente con la cédula: " + dto.getCedula());
        }
        
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Ya existe un cliente con el email: " + dto.getEmail());
        }
        
        // Crear cliente
        Cliente cliente = clienteMapper.toEntity(dto);
        cliente.setEstado(1);
        
        Cliente savedCliente = clienteRepository.save(cliente);
        log.info("Cliente creado exitosamente con ID: {}", savedCliente.getId());
        
        return clienteMapper.toDTO(savedCliente);
    }
    
    @Override
    public ClienteDTO update(Long id, ClienteUpdateDTO dto) {
        log.info("Actualizando cliente con ID: {}", id);
        
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        
        // Verificar email único (si cambió)
        if (dto.getEmail() != null && !dto.getEmail().equals(cliente.getEmail())) {
            if (clienteRepository.existsByEmail(dto.getEmail())) {
                throw new BusinessException("Ya existe un cliente con el email: " + dto.getEmail());
            }
        }
        
        // Actualizar campos
        clienteMapper.updateEntityFromDTO(dto, cliente);
        
        Cliente updatedCliente = clienteRepository.save(cliente);
        log.info("Cliente actualizado exitosamente con ID: {}", updatedCliente.getId());
        
        return clienteMapper.toDTO(updatedCliente);
    }
    
    @Override
    public void delete(Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        
        // Soft delete
        cliente.setEstado(0);
        clienteRepository.save(cliente);
        
        log.info("Cliente eliminado exitosamente con ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findClientesTerceraEdad(Pageable pageable) {
        log.debug("Obteniendo clientes con tercera edad");
        return clienteRepository.findClientesTerceraEdad(pageable)
            .map(clienteMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAllSinFiltro(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(clienteMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findClientesConDiscapacidad(Pageable pageable) {
        log.debug("Obteniendo clientes con discapacidad");
        return clienteRepository.findClientesConDiscapacidad(pageable)
            .map(clienteMapper::toDTO);
    }
}
