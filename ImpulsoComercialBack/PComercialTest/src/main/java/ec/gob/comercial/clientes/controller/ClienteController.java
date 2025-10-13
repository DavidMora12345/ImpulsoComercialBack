package ec.gob.comercial.clientes.controller;

import ec.gob.comercial.clientes.dto.ClienteCreateDTO;
import ec.gob.comercial.clientes.dto.ClienteDTO;
import ec.gob.comercial.clientes.dto.ClienteUpdateDTO;
import ec.gob.comercial.clientes.service.ClienteService;
import ec.gob.comercial.shared.dto.ApiResponseDTO;
import ec.gob.comercial.shared.dto.PageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para Cliente
 * 
 * Endpoints:
 * - GET    /api/clientes              - Lista todos los clientes
 * - GET    /api/clientes/search       - Busca clientes
 * - GET    /api/clientes/{id}         - Obtiene un cliente
 * - GET    /api/clientes/cedula/{cedula} - Obtiene cliente por cédula
 * - POST   /api/clientes              - Crea un cliente
 * - PUT    /api/clientes/{id}         - Actualiza un cliente
 * - DELETE /api/clientes/{id}         - Elimina un cliente
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "API de gestión de clientes/contribuyentes")
public class ClienteController {
    
    private final ClienteService clienteService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista paginada de clientes activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECAUDADOR', 'CATASTRO', 'CONSULTA')")
    public ResponseEntity<PageResponseDTO<ClienteDTO>> getAllClientes(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("DESC") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ClienteDTO> clientes = clienteService.findAll(pageable);
        
        return ResponseEntity.ok(PageResponseDTO.of(clientes));
    }
    
    @GetMapping("/search")
    @Operation(summary = "Buscar clientes", description = "Busca clientes por nombre, apellido, cédula o email")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECAUDADOR', 'CATASTRO', 'CONSULTA')")
    public ResponseEntity<PageResponseDTO<ClienteDTO>> searchClientes(
        @RequestParam String query,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClienteDTO> clientes = clienteService.search(query, pageable);
        
        return ResponseEntity.ok(PageResponseDTO.of(clientes));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Retorna un cliente específico por su ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECAUDADOR', 'CATASTRO', 'CONSULTA')")
    public ResponseEntity<ApiResponseDTO<ClienteDTO>> getClienteById(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(cliente));
    }
    
    @GetMapping("/cedula/{cedula}")
    @Operation(summary = "Obtener cliente por cédula", description = "Retorna un cliente específico por su cédula")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECAUDADOR', 'CATASTRO', 'CONSULTA')")
    public ResponseEntity<ApiResponseDTO<ClienteDTO>> getClienteByCedula(@PathVariable String cedula) {
        ClienteDTO cliente = clienteService.findByCedula(cedula);
        return ResponseEntity.ok(ApiResponseDTO.success(cliente));
    }
    
    @PostMapping
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECAUDADOR')")
    public ResponseEntity<ApiResponseDTO<ClienteDTO>> createCliente(@Valid @RequestBody ClienteCreateDTO dto) {
        ClienteDTO cliente = clienteService.create(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponseDTO.success("Cliente creado exitosamente", cliente));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza un cliente existente")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECAUDADOR')")
    public ResponseEntity<ApiResponseDTO<ClienteDTO>> updateCliente(
        @PathVariable Long id,
        @Valid @RequestBody ClienteUpdateDTO dto
    ) {
        ClienteDTO cliente = clienteService.update(id, dto);
        return ResponseEntity.ok(ApiResponseDTO.success("Cliente actualizado exitosamente", cliente));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<Void>> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Cliente eliminado exitosamente", null));
    }
    
    @GetMapping("/tercera-edad")
    @Operation(summary = "Obtener clientes con tercera edad", description = "Retorna clientes mayores de 64 años")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECAUDADOR', 'CONSULTA')")
    public ResponseEntity<PageResponseDTO<ClienteDTO>> getClientesTerceraEdad(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClienteDTO> clientes = clienteService.findClientesTerceraEdad(pageable);
        
        return ResponseEntity.ok(PageResponseDTO.of(clientes));
    }
    
    @GetMapping("/discapacidad")
    @Operation(summary = "Obtener clientes con discapacidad", description = "Retorna clientes con discapacidad >= 50%")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECAUDADOR', 'CONSULTA')")
    public ResponseEntity<PageResponseDTO<ClienteDTO>> getClientesConDiscapacidad(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClienteDTO> clientes = clienteService.findClientesConDiscapacidad(pageable);
        
        return ResponseEntity.ok(PageResponseDTO.of(clientes));
    }
}
