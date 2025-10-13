-- =====================================================
-- SCHEMA: RECAUDACION
-- Descripción: Módulo de facturación, pagos y recaudación
-- =====================================================

CREATE SCHEMA IF NOT EXISTS recaudacion;

-- =====================================================
-- TABLA: rubro
-- =====================================================
CREATE TABLE recaudacion.rubro (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    id_modulo BIGINT NOT NULL,
    
    -- Configuración
    iva BOOLEAN DEFAULT FALSE,
    calculable BOOLEAN DEFAULT FALSE,
    valor_fijo NUMERIC(12,2),
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1
);

-- =====================================================
-- TABLA: factura
-- =====================================================
CREATE TABLE recaudacion.factura (
    id BIGSERIAL PRIMARY KEY,
    numero_factura VARCHAR(50) UNIQUE NOT NULL,
    id_cliente BIGINT NOT NULL,
    id_modulo BIGINT NOT NULL,
    
    -- Fechas
    fecha_emision DATE NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    
    -- Valores
    subtotal NUMERIC(12,2) NOT NULL,
    descuento NUMERIC(12,2) DEFAULT 0,
    iva NUMERIC(12,2) DEFAULT 0,
    total NUMERIC(12,2) NOT NULL,
    saldo NUMERIC(12,2) NOT NULL,
    
    -- Estado
    pagado BOOLEAN DEFAULT FALSE,
    convenio_pago BOOLEAN DEFAULT FALSE,
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1
);

-- =====================================================
-- TABLA: factura_detalle
-- =====================================================
CREATE TABLE recaudacion.factura_detalle (
    id BIGSERIAL PRIMARY KEY,
    id_factura BIGINT NOT NULL,
    id_rubro BIGINT NOT NULL,
    
    descripcion VARCHAR(500),
    cantidad INTEGER DEFAULT 1,
    valor_unitario NUMERIC(12,2) NOT NULL,
    subtotal NUMERIC(12,2) NOT NULL,
    descuento NUMERIC(12,2) DEFAULT 0,
    iva NUMERIC(12,2) DEFAULT 0,
    total NUMERIC(12,2) NOT NULL,
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1
);

-- =====================================================
-- TABLA: pago
-- =====================================================
CREATE TABLE recaudacion.pago (
    id BIGSERIAL PRIMARY KEY,
    numero_recibo VARCHAR(50) UNIQUE NOT NULL,
    id_cliente BIGINT NOT NULL,
    id_caja BIGINT NOT NULL,
    id_forma_pago BIGINT NOT NULL,
    
    -- Valores
    total_pago NUMERIC(12,2) NOT NULL,
    
    -- Fechas
    fecha_pago TIMESTAMP NOT NULL DEFAULT NOW(),
    
    -- Referencia bancaria
    referencia_bancaria VARCHAR(100),
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1
);

-- =====================================================
-- TABLA: pago_detalle
-- =====================================================
CREATE TABLE recaudacion.pago_detalle (
    id BIGSERIAL PRIMARY KEY,
    id_pago BIGINT NOT NULL,
    id_factura BIGINT NOT NULL,
    
    valor_pagado NUMERIC(12,2) NOT NULL,
    interes_mora NUMERIC(12,2) DEFAULT 0,
    total NUMERIC(12,2) NOT NULL,
    
    -- Auditoría
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    usuario_creacion BIGINT,
    usuario_actualizacion BIGINT,
    estado INTEGER DEFAULT 1
);

-- =====================================================
-- TABLA: convenio_pago
-- =====================================================
CREATE TABLE recaudacion.convenio_pago (
    id BIGSERIAL PRIMARY KEY,
    numero_convenio VARCHAR(50) UNIQUE NOT NULL,
    id_cliente BIGINT NOT NULL,
    
    -- Valores
    valor_total NUMERIC(12,2) NOT NULL,
    valor_entrada NUMERIC(12,2),
    valor_cuotas NUMERIC(12,2) NOT NULL,
    numero_cuotas INTEGER NOT NULL,
    
    -- Fechas
    fecha_convenio DATE NOT NULL,
    fecha_primera_cuota DATE NOT NULL,
    
    -- Estado
    cumplido BOOLEAN DEFAULT FALSE,
    
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

-- Rubro
CREATE INDEX idx_rubro_codigo ON recaudacion.rubro(codigo);
CREATE INDEX idx_rubro_modulo ON recaudacion.rubro(id_modulo);

-- Factura
CREATE INDEX idx_factura_numero ON recaudacion.factura(numero_factura);
CREATE INDEX idx_factura_cliente ON recaudacion.factura(id_cliente);
CREATE INDEX idx_factura_modulo ON recaudacion.factura(id_modulo);
CREATE INDEX idx_factura_fecha_emision ON recaudacion.factura(fecha_emision);
CREATE INDEX idx_factura_fecha_vencimiento ON recaudacion.factura(fecha_vencimiento);
CREATE INDEX idx_factura_pagado ON recaudacion.factura(pagado);
CREATE INDEX idx_factura_estado ON recaudacion.factura(estado);

-- Factura Detalle
CREATE INDEX idx_factura_detalle_factura ON recaudacion.factura_detalle(id_factura);
CREATE INDEX idx_factura_detalle_rubro ON recaudacion.factura_detalle(id_rubro);

-- Pago
CREATE INDEX idx_pago_numero ON recaudacion.pago(numero_recibo);
CREATE INDEX idx_pago_cliente ON recaudacion.pago(id_cliente);
CREATE INDEX idx_pago_fecha ON recaudacion.pago(fecha_pago);
CREATE INDEX idx_pago_caja ON recaudacion.pago(id_caja);

-- Pago Detalle
CREATE INDEX idx_pago_detalle_pago ON recaudacion.pago_detalle(id_pago);
CREATE INDEX idx_pago_detalle_factura ON recaudacion.pago_detalle(id_factura);

-- Convenio Pago
CREATE INDEX idx_convenio_numero ON recaudacion.convenio_pago(numero_convenio);
CREATE INDEX idx_convenio_cliente ON recaudacion.convenio_pago(id_cliente);

-- =====================================================
-- COMENTARIOS
-- =====================================================
COMMENT ON TABLE recaudacion.rubro IS 'Conceptos de cobro (impuestos, tasas, servicios)';
COMMENT ON TABLE recaudacion.factura IS 'Facturas emitidas a clientes';
COMMENT ON TABLE recaudacion.factura_detalle IS 'Detalles/rubros de las facturas';
COMMENT ON TABLE recaudacion.pago IS 'Pagos realizados por clientes';
COMMENT ON TABLE recaudacion.pago_detalle IS 'Relación entre pagos y facturas';
COMMENT ON TABLE recaudacion.convenio_pago IS 'Convenios de pago en cuotas';
