# Sistema Comercial Municipal - Monorepo

Sistema moderno de gestión comercial, catastros y recaudación municipal.

## 🚀 INICIO RÁPIDO

```bash
# Iniciar todo el sistema (PostgreSQL + Backend + Frontend)
./start-simple.sh

# Verificar que todo esté corriendo
./verificar-sistema.sh

# Detener todo
./stop-simple.sh
```

**URLs:**
- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

📖 **[Ver Manual Completo](MANUAL_INICIO_SISTEMA.md)**

---

## 🏗️ Arquitectura

**Monorepo** con Backend (Spring Boot) y Frontend (React + Next.js)

```
new_comercial/
├── ImpulsoComercialBack/    # Backend - Spring Boot 3.5.6 + Java 21
│   └── PComercialTest/
└── frontend/                  # Frontend - Next.js 14 + React 18
```

## 🚀 Stack Tecnológico

### Backend
- **Framework:** Spring Boot 3.5.6
- **Lenguaje:** Java 21 (LTS)
- **Base de Datos:** PostgreSQL 16 + PostGIS
- **Seguridad:** Spring Security + JWT
- **ORM:** JPA/Hibernate
- **Migraciones:** Flyway
- **Documentación:** Swagger/OpenAPI

### Frontend
- **Framework:** Next.js 14 (App Router)
- **Lenguaje:** TypeScript 5.5
- **Styling:** TailwindCSS
- **State Management:** React Query (TanStack Query)
- **HTTP Client:** Axios
- **Forms:** React Hook Form + Zod
- **Icons:** Lucide React

## 📋 Requisitos

- **Java:** 21 (LTS)
- **Node.js:** 18+ o 20+
- **PostgreSQL:** 16+
- **Maven:** 3.9+
- **npm/yarn:** Latest

## ⚙️ Configuración

### 1. Base de Datos

```sql
-- Crear base de datos
CREATE DATABASE comercial_db;

-- Conectar
\c comercial_db

-- Habilitar PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;
```

### 2. Backend

```bash
cd ImpulsoComercialBack/PComercialTest

# Configurar variables de entorno
export DB_USERNAME=postgres
export DB_PASSWORD=tu_password
export JWT_SECRET=tu_secret_key

# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

El backend estará disponible en: http://localhost:8080

**Swagger UI:** http://localhost:8080/swagger-ui.html

### 3. Frontend

```bash
cd frontend

# Instalar dependencias
npm install

# Copiar variables de entorno
cp .env.local.example .env.local

# Editar .env.local con tus valores
nano .env.local

# Ejecutar en desarrollo
npm run dev
```

El frontend estará disponible en: http://localhost:3000

## 🐳 Docker (Desarrollo)

```bash
# Desde la raíz del proyecto
docker-compose up -d
```

Esto levantará:
- PostgreSQL + PostGIS (puerto 5432)
- Backend (puerto 8080)
- Frontend (puerto 3000)

## 📚 Módulos

### ✅ CLIENTES
- CRUD completo de clientes/contribuyentes
- Validación de cédula ecuatoriana
- Búsqueda avanzada
- Filtros (tercera edad, discapacidad)

### 🚧 CATASTROS
- Predios urbanos con PostGIS
- Predios rurales
- Avalúos catastrales
- Mapas GIS (Leaflet)

### 🚧 RECAUDACIÓN
- Facturación
- Pagos y recaudación
- Convenios de pago
- Intereses de mora
- Reportes

## 🔐 Autenticación

El sistema usa **JWT** para autenticación.

**Usuario temporal (testing):**
```
Email: admin@municipio.gob.ec
Password: admin123
```

## 📁 Estructura del Proyecto

### Backend
```
ImpulsoComercialBack/PComercialTest/src/main/java/ec/gob/comercial/
├── shared/           # Código compartido
├── clientes/         # Módulo clientes
├── catastros/        # Módulo catastros
└── recaudacion/      # Módulo recaudación
```

### Frontend
```
frontend/src/
├── app/              # Next.js App Router
│   ├── (auth)/       # Rutas públicas
│   └── (dashboard)/  # Rutas protegidas
├── components/       # Componentes React
├── lib/              # Utilidades y API clients
└── types/            # TypeScript types
```

## 🧪 Testing

### Backend
```bash
cd ImpulsoComercialBack/PComercialTest
mvn test
```

### Frontend
```bash
cd frontend
npm run test
```

## 📦 Build para Producción

### Backend
```bash
cd ImpulsoComercialBack/PComercialTest
mvn clean package
```

El JAR estará en: `target/PComercialTest-0.0.1-SNAPSHOT.jar`

### Frontend
```bash
cd frontend
npm run build
npm start
```

## 🔧 Scripts Útiles

### Backend
```bash
# Limpiar y compilar
mvn clean install

# Ejecutar tests
mvn test

# Generar JAR
mvn package

# Ejecutar con perfil de producción
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Frontend
```bash
# Desarrollo
npm run dev

# Build
npm run build

# Producción
npm start

# Lint
npm run lint

# Type check
npm run type-check
```

## 📊 Base de Datos

### Schemas
- `clientes` - Clientes/contribuyentes
- `catastros` - Predios y avalúos (con PostGIS)
- `recaudacion` - Facturas y pagos
- `shared` - Datos compartidos
- `audit` - Auditoría

### Migraciones
Las migraciones se ejecutan automáticamente con Flyway al iniciar el backend.

Ubicación: `src/main/resources/db/migration/`

## 🐛 Debugging

### Backend
```bash
# Ver logs
tail -f ImpulsoComercialBack/PComercialTest/logs/comercial-api.log

# Ejecutar con debug
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

### Frontend
```bash
# Ver logs en consola del navegador
# O usar React Query Devtools (incluido)
```

**Versión:** 1.0.0  
**Última actualización:** Octubre 2025
