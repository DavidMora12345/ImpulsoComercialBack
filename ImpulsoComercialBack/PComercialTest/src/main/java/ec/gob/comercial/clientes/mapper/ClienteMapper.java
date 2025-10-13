package ec.gob.comercial.clientes.mapper;

import ec.gob.comercial.clientes.domain.Cliente;
import ec.gob.comercial.clientes.dto.ClienteCreateDTO;
import ec.gob.comercial.clientes.dto.ClienteDTO;
import ec.gob.comercial.clientes.dto.ClienteUpdateDTO;
import org.mapstruct.*;

/**
 * Mapper para Cliente
 * 
 * MapStruct genera automáticamente la implementación
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper {
    
    /**
     * Convierte Entity a DTO
     */
    @Mapping(target = "nombreCompleto", expression = "java(cliente.getNombreCompleto())")
    @Mapping(target = "terceraEdad", expression = "java(cliente.isTerceraEdad())")
    @Mapping(target = "tieneDescuentoDiscapacidad", expression = "java(cliente.tieneDescuentoDiscapacidad())")
    ClienteDTO toDTO(Cliente cliente);
    
    /**
     * Convierte CreateDTO a Entity
     */
    Cliente toEntity(ClienteCreateDTO dto);
    
    /**
     * Actualiza una entidad existente con datos del UpdateDTO
     * Solo actualiza los campos no nulos
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ClienteUpdateDTO dto, @MappingTarget Cliente cliente);
}
