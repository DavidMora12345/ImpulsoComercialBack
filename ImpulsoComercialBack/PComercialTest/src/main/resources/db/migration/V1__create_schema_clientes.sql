-- =====================================================
-- SCHEMA: CLIENTES
-- Descripción: Módulo de gestión de clientes/contribuyentes
-- =====================================================

CREATE SCHEMA IF NOT EXISTS clientes;

-- =====================================================
-- TABLA: cliente
-- =====================================================
CREATE TABLE clientes.cliente (
    id BIGSERIAL PRIMARY KEY,
    
    -- Identificación
    cedula VARCHAR(20) UNIQUE,
    apellido VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    nombre_comercial VARCHAR(250),
    
    -- Contacto
    direccion VARCHAR(200),
    telefono VARCHAR(60),
    celular VARCHAR(10),
    email VARCHAR(100) NOT NULL UNIQUE,
    
    -- Información personal
    fecha_nacimiento DATE,
    estado_civil VARCHAR(20),
    
    -- Descuentos
    discapacitado INTEGER DEFAULT 0,
    porcentaje_discapacidad NUMERIC(17,17),
    entidad_religiosa_exonerada INTEGER DEFAULT 0,
    porcentaje_exoneracion NUMERIC(17,17),
    
    -- Tipo de cliente
    id_tipo_personeria_juridica BIGINT NOT NULL,
    id_nacionalidad BIGINT,
    
    -- Relaciones
    id_conyugue BIGINT,
    id_representante_legal BIGINT,
    
    -- Portal Ciudadano
    password VARCHAR(255),
    email_verificado INTEGER DEFAULT 0,
    email_verificacion_token VARCHAR(255),
    portal_consulta INTEGER DEFAULT 0,
    device_android_token VARCHAR(255),
    device_ios_token VARCHAR(255),
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1
);

-- =====================================================
-- ÍNDICES
-- =====================================================
CREATE INDEX idx_cliente_cedula ON clientes.cliente(cedula);
CREATE INDEX idx_cliente_email ON clientes.cliente(email);
CREATE INDEX idx_cliente_apellido ON clientes.cliente(apellido);
CREATE INDEX idx_cliente_nombre ON clientes.cliente(nombre);
CREATE INDEX idx_cliente_estado ON clientes.cliente(estado);
CREATE INDEX idx_cliente_fecha_nacimiento ON clientes.cliente(fecha_nacimiento);
CREATE INDEX idx_cliente_discapacitado ON clientes.cliente(discapacitado);

-- =====================================================
-- COMENTARIOS
-- =====================================================
COMMENT ON TABLE clientes.cliente IS 'Tabla de clientes/contribuyentes del municipio';
COMMENT ON COLUMN clientes.cliente.cedula IS 'Cédula o RUC del cliente';
COMMENT ON COLUMN clientes.cliente.discapacitado IS '0=No, 1=Sí';
COMMENT ON COLUMN clientes.cliente.porcentaje_discapacidad IS 'Porcentaje de discapacidad (0-1)';
COMMENT ON COLUMN clientes.cliente.estado IS '1=Activo, 0=Inactivo';
