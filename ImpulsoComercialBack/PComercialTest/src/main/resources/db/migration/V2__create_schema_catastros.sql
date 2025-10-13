-- =====================================================
-- SCHEMA: CATASTROS
-- Descripción: Módulo de catastros urbanos y rurales
-- =====================================================

CREATE SCHEMA IF NOT EXISTS catastros;

-- Habilitar PostGIS si no está habilitado
CREATE EXTENSION IF NOT EXISTS postgis;

-- =====================================================
-- TABLA: predio (Urbano)
-- =====================================================
CREATE TABLE catastros.predio (
    id BIGSERIAL PRIMARY KEY,
    
    -- Clave catastral
    clave_catastral VARCHAR(50) UNIQUE NOT NULL,
    id_cliente BIGINT NOT NULL,
    
    -- Ubicación administrativa
    id_zona BIGINT NOT NULL,
    id_parroquia BIGINT NOT NULL,
    id_sector BIGINT NOT NULL,
    id_manzana BIGINT NOT NULL,
    lote VARCHAR(10) NOT NULL,
    unidad VARCHAR(10),
    division VARCHAR(10),
    
    -- Dimensiones
    area_terreno NUMERIC(10,2) NOT NULL,
    area_construccion NUMERIC(10,2),
    frente NUMERIC(10,2),
    fondo NUMERIC(10,2),
    
    -- Dirección
    calle_principal VARCHAR(200) NOT NULL,
    calle_secundaria VARCHAR(200),
    numero_casa VARCHAR(20),
    referencia TEXT,
    
    -- GIS - PostGIS
    geometria GEOMETRY(POLYGON, 4326),
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1
);

-- =====================================================
-- TABLA: predio_rural
-- =====================================================
CREATE TABLE catastros.predio_rural (
    id BIGSERIAL PRIMARY KEY,
    
    clave_catastral_rural VARCHAR(50) UNIQUE NOT NULL,
    id_cliente BIGINT NOT NULL,
    
    -- Ubicación
    id_zona_rural BIGINT NOT NULL,
    id_parroquia_rural BIGINT NOT NULL,
    id_recinto BIGINT NOT NULL,
    predio VARCHAR(10) NOT NULL,
    lote VARCHAR(10) NOT NULL,
    
    -- Dimensiones en HECTÁREAS
    area_total NUMERIC(12,2) NOT NULL,
    area_cultivo NUMERIC(12,2),
    area_pasto NUMERIC(12,2),
    area_bosque NUMERIC(12,2),
    area_improductiva NUMERIC(12,2),
    
    -- Características
    id_uso_suelo BIGINT NOT NULL,
    id_tipo_riego BIGINT,
    acceso_vehicular BOOLEAN,
    distancia_via_principal NUMERIC(10,2),
    nombre_predio VARCHAR(200),
    
    -- GIS
    geometria GEOMETRY(POLYGON, 4326),
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1
);

-- =====================================================
-- TABLA: construccion
-- =====================================================
CREATE TABLE catastros.construccion (
    id BIGSERIAL PRIMARY KEY,
    id_predio BIGINT NOT NULL,
    id_tipo_construccion BIGINT NOT NULL,
    
    numero_pisos INTEGER,
    area_construccion NUMERIC(10,2) NOT NULL,
    anio_construccion INTEGER,
    estado_conservacion VARCHAR(50),
    porcentaje_avance NUMERIC(5,2),
    valor_construccion NUMERIC(12,2),
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1
);

-- =====================================================
-- TABLA: avaluo (Urbano)
-- =====================================================
CREATE TABLE catastros.avaluo (
    id BIGSERIAL PRIMARY KEY,
    id_predio BIGINT NOT NULL,
    anio INTEGER NOT NULL,
    
    -- Valores
    valor_terreno NUMERIC(12,2) NOT NULL,
    valor_construccion NUMERIC(12,2),
    valor_total NUMERIC(12,2) NOT NULL,
    valor_m2_terreno NUMERIC(10,2),
    
    -- Factores de ajuste
    factor_forma NUMERIC(5,4),
    factor_topografia NUMERIC(5,4),
    factor_ubicacion NUMERIC(5,4),
    factor_servicios NUMERIC(5,4),
    factor_vias NUMERIC(5,4),
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1,
    
    UNIQUE(id_predio, anio)
);

-- =====================================================
-- TABLA: avaluo_rural
-- =====================================================
CREATE TABLE catastros.avaluo_rural (
    id BIGSERIAL PRIMARY KEY,
    id_predio_rural BIGINT NOT NULL,
    anio INTEGER NOT NULL,
    
    -- Valores
    valor_tierra NUMERIC(12,2) NOT NULL,
    valor_mejoras NUMERIC(12,2),
    valor_total NUMERIC(12,2) NOT NULL,
    valor_ha NUMERIC(10,2),
    
    -- Factores de ajuste
    factor_uso_suelo NUMERIC(5,4),
    factor_riego NUMERIC(5,4),
    factor_acceso NUMERIC(5,4),
    factor_topografia NUMERIC(5,4),
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1,
    
    UNIQUE(id_predio_rural, anio)
);

-- =====================================================
-- ÍNDICES
-- =====================================================

-- Predio
CREATE INDEX idx_predio_clave ON catastros.predio(clave_catastral);
CREATE INDEX idx_predio_cliente ON catastros.predio(id_cliente);
CREATE INDEX idx_predio_zona ON catastros.predio(id_zona);
CREATE INDEX idx_predio_parroquia ON catastros.predio(id_parroquia);
CREATE INDEX idx_predio_sector ON catastros.predio(id_sector);
CREATE INDEX idx_predio_estado ON catastros.predio(estado);

-- Índice espacial PostGIS
CREATE INDEX idx_predio_geometria ON catastros.predio USING GIST(geometria);

-- Predio Rural
CREATE INDEX idx_predio_rural_clave ON catastros.predio_rural(clave_catastral_rural);
CREATE INDEX idx_predio_rural_cliente ON catastros.predio_rural(id_cliente);
CREATE INDEX idx_predio_rural_geometria ON catastros.predio_rural USING GIST(geometria);

-- Construcción
CREATE INDEX idx_construccion_predio ON catastros.construccion(id_predio);

-- Avalúo
CREATE INDEX idx_avaluo_predio ON catastros.avaluo(id_predio);
CREATE INDEX idx_avaluo_anio ON catastros.avaluo(anio);

-- =====================================================
-- COMENTARIOS
-- =====================================================
COMMENT ON TABLE catastros.predio IS 'Predios urbanos';
COMMENT ON TABLE catastros.predio_rural IS 'Predios rurales (áreas en hectáreas)';
COMMENT ON TABLE catastros.construccion IS 'Construcciones dentro de predios urbanos';
COMMENT ON TABLE catastros.avaluo IS 'Avalúos catastrales de predios urbanos';
COMMENT ON TABLE catastros.avaluo_rural IS 'Avalúos catastrales de predios rurales';
