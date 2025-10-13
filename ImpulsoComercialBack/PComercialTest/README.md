# API Comercial Municipal - Spring Boot

Sistema moderno de gestión comercial municipal desarrollado con Spring Boot 3.5.6 y Java 21.

## 🏗️ Arquitectura

**Patrón:** Modular Monolith + API-First  
**Stack:** Spring Boot 3.5.6 + Java 21 + PostgreSQL 16 + PostGIS

### Estructura del Proyecto

```
src/main/java/ec/gob/comercial/
│
├── ComercialApplication.java          # Main class
│
├── shared/                            # Código compartido
│   ├── config/                        # Configuraciones
│   │   ├── SecurityConfig.java        # Spring Security + JWT
│   │   ├── CorsConfig.java            # CORS
│   │   └── SwaggerConfig.java         # OpenAPI/Swagger
│   │
│   ├── security/                      # Seguridad
│   │   ├── JwtTokenProvider.java      # Generación/validación JWT
│   │   ├── JwtAuthenticationFilter.java
│   │   └── UserDetailsServiceImpl.java
│   │
│   ├── domain/                        # Entidades base
│   │   └── AuditableEntity.java       # Auditoría automática
│   │
│   ├── exception/                     # Manejo de errores
│   │   ├── GlobalExceptionHandler.java
│   │   ├── ResourceNotFoundException.java
│   │   └── BusinessException.java
│   │
│   ├── dto/                           # DTOs compartidos
│   │   ├── ApiResponseDTO.java
│   │   ├── PageResponseDTO.java
│   │   └── ErrorResponseDTO.java
│   │
│   └── util/                          # Utilidades
│       └── CedulaValidator.java       # Validación cédula ecuatoriana
│
├── clientes/                          # MÓDULO CLIENTES
│   ├── domain/
│   │   └── Cliente.java               # Entidad Cliente
│   ├── repository/
│   │   └── ClienteRepository.java     # JPA Repository
│   ├── dto/
│   │   ├── ClienteDTO.java
│   │   ├── ClienteCreateDTO.java
│   │   └── ClienteUpdateDTO.java
│   ├── service/
│   │   ├── ClienteService.java
│   │   └── ClienteServiceImpl.java
│   ├── controller/
│   │   └── ClienteController.java     # REST API
│   └── mapper/
│       └── ClienteMapper.java         # MapStruct
│
├── catastros/                         # MÓDULO CATASTROS (TODO)
│   └── (estructura similar)
│
└── recaudacion/                       # MÓDULO RECAUDACIÓN (TODO)
    └── (estructura similar)
```

## 🚀 Tecnologías

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Spring Boot** | 3.5.6 | Framework principal |
| **Java** | 21 (LTS) | Lenguaje |
| **PostgreSQL** | 16+ | Base de datos |
| **PostGIS** | Latest | Datos geoespaciales (GIS) |
| **Flyway** | Latest | Migraciones de BD |
| **Spring Security** | 6.x | Seguridad |
| **JWT** | 0.12.3 | Autenticación |
| **MapStruct** | 1.5.5 | Mapeo Entity ↔ DTO |
| **Swagger/OpenAPI** | 2.3.0 | Documentación API |
| **Lombok** | 1.18.34 | Reduce boilerplate |

## 📋 Requisitos

- Java 21 (LTS)
- Maven 3.9+
- PostgreSQL 16+
- PostGIS extension

## ⚙️ Configuración

### 1. Base de Datos

```sql
-- Crear base de datos
CREATE DATABASE comercial_db;

-- Conectar a la base de datos
\c comercial_db

-- Habilitar PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;

-- Los schemas se crean automáticamente con Flyway
```

### 2. Variables de Entorno

```bash
# Base de datos
export DB_USERNAME=postgres
export DB_PASSWORD=tu_password

# JWT
export JWT_SECRET=tu_secret_key_aqui
```

### 3. Compilar

```bash
mvn clean install
```

### 4. Ejecutar

```bash
mvn spring-boot:run
```

## 📚 Documentación API

Una vez iniciada la aplicación, accede a:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

## 🔐 Autenticación

La API usa **JWT (JSON Web Tokens)** para autenticación.

### Usuario Temporal (Testing)

```
Email: admin@municipio.gob.ec
Password: admin123
```

### Obtener Token

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@municipio.gob.ec",
    "password": "admin123"
  }'
```

### Usar Token

```bash
curl -X GET http://localhost:8080/api/clientes \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 📦 Módulos

### ✅ CLIENTES (Completado)

Gestión de clientes/contribuyentes del municipio.

**Endpoints:**
- `GET /api/clientes` - Lista clientes (paginado)
- `GET /api/clientes/search?query=...` - Buscar clientes
- `GET /api/clientes/{id}` - Obtener cliente por ID
- `GET /api/clientes/cedula/{cedula}` - Obtener por cédula
- `POST /api/clientes` - Crear cliente
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente
- `GET /api/clientes/tercera-edad` - Clientes con tercera edad
- `GET /api/clientes/discapacidad` - Clientes con discapacidad

**Reglas de Negocio:**
- Validación de cédula ecuatoriana (módulo 10)
- Validación de RUC (13 dígitos)
- Cálculo automático de tercera edad (> 64 años)
- Cálculo automático de descuento por discapacidad (>= 50%)

### 🚧 CATASTROS (Pendiente)

Gestión de predios urbanos y rurales con soporte GIS.

**Entidades:**
- Predio (Urbano)
- PredioRural
- Avaluo
- Construccion
- LiquidacionAvaluo

### 🚧 RECAUDACIÓN (Pendiente)

Gestión de facturación, pagos y recaudación.

**Entidades:**
- Factura
- FacturaDetalle
- Pago
- PagoDetalle
- ConvenioPago
- InteresMora

## 🧪 Testing

```bash
# Ejecutar tests
mvn test

# Con cobertura
mvn test jacoco:report
```

## 📊 Base de Datos

### Schemas

| Schema | Propósito |
|--------|-----------|
| `clientes` | Clientes/contribuyentes |
| `catastros` | Predios y avalúos |
| `recaudacion` | Facturas y pagos |
| `shared` | Datos compartidos (usuarios, roles) |
| `audit` | Auditoría (JSONB) |

### Migraciones

Las migraciones se ejecutan automáticamente con **Flyway** al iniciar la aplicación.

Ubicación: `src/main/resources/db/migration/`

## 🔒 Seguridad

- ✅ Spring Security configurado
- ✅ JWT para autenticación
- ✅ CORS habilitado
- ✅ Roles y permisos (RBAC)
- ✅ Validación de inputs
- ✅ Manejo global de excepciones
- ✅ Auditoría automática

## 📈 Performance

- ✅ Connection pooling (HikariCP)
- ✅ Batch processing (JPA)
- ✅ Índices en BD
- ✅ Lazy loading
- ✅ Paginación

## 🐛 Debugging

```bash
# Logs en consola
tail -f logs/comercial-api.log

# Nivel de log DEBUG
mvn spring-boot:run -Dspring-boot.run.arguments="--logging.level.ec.gob.comercial=DEBUG"
```

## 📝 Próximos Pasos

1. ✅ Módulo CLIENTES completo
2. ⏳ Módulo CATASTROS
   - Crear entidades (Predio, PredioRural, Avaluo)
   - Implementar soporte PostGIS
   - APIs REST
3. ⏳ Módulo RECAUDACIÓN
   - Crear entidades (Factura, Pago, ConvenioPago)
   - Implementar cálculo de intereses
   - Proceso de emisión de agua
4. ⏳ Módulo USUARIOS
   - Gestión de usuarios y roles
   - Integración con LDAP (opcional)
5. ⏳ Frontend React
   - Next.js 14
   - TailwindCSS
   - shadcn/ui


---

**Versión:** 1.0.0  
**Última actualización:** Octubre 2025
