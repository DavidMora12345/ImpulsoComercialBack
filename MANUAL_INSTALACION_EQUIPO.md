# Manual de Instalación y Ejecución - Sistema Comercial

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalado lo siguiente en tu máquina:

### Software Requerido

- **Java 21** (OpenJDK o Oracle JDK)
- **Maven 3.9+**
- **Node.js 20+** (LTS recomendado)
- **Docker Desktop** (para PostgreSQL)
- **Git**

### Verificar Instalaciones

Ejecuta los siguientes comandos para verificar que todo está instalado correctamente:

```bash
# Verificar Java
java --version
# Debe mostrar: openjdk 21.x.x o java 21.x.x

# Verificar Maven
mvn --version
# Debe mostrar: Apache Maven 3.9.x

# Verificar Node.js
node --version
# Debe mostrar: v20.x.x o superior

# Verificar npm
npm --version
# Debe mostrar: 10.x.x o superior

# Verificar Docker
docker --version
# Debe mostrar: Docker version 24.x.x o superior

# Verificar Git
git --version
# Debe mostrar: git version 2.x.x
```

---

## Instalación del Proyecto

### 1. Clonar el Repositorio

```bash
# Clonar el proyecto
git clone git@github.com:DavidMora12345/ImpulsoComercialBack.git

# Entrar al directorio del proyecto
cd ImpulsoComercialBack
```

### 2. Estructura del Proyecto

El proyecto es un monorepo con la siguiente estructura:

```
ImpulsoComercialBack/
├── ImpulsoComercialBack/     # Backend (Spring Boot)
│   └── PComercialTest/
├── frontend/                  # Frontend (Next.js)
├── docker-compose.yml         # Configuración de Docker
├── start-simple.sh            # Script de inicio automático
├── stop-simple.sh             # Script de parada
└── verificar-sistema.sh       # Script de verificación
```

---

## Configuración Inicial

### 1. Configurar Variables de Entorno (Backend)

El backend ya tiene configuración por defecto en `application.yml`. No es necesario modificar nada para desarrollo local.

### 2. Configurar Variables de Entorno (Frontend)

```bash
# Ir al directorio del frontend
cd frontend

# Copiar el archivo de ejemplo
cp .env.local.example .env.local

# Editar si es necesario (opcional)
# El archivo ya tiene la configuración correcta por defecto
```

Contenido de `.env.local`:
```
NEXT_PUBLIC_API_URL=http://localhost:8080
```

---

## Ejecución del Sistema

Tienes dos opciones para ejecutar el sistema: **Automática** (recomendada) o **Manual**.

---

## OPCIÓN 1: Ejecución Automática (Recomendada)

### Iniciar Todo el Sistema

Desde la raíz del proyecto, ejecuta:

```bash
./start-simple.sh
```

Este script hace lo siguiente automáticamente:
1. Inicia PostgreSQL con Docker
2. Compila el backend
3. Inicia el backend en el puerto 8080
4. Instala dependencias del frontend (si es necesario)
5. Inicia el frontend en el puerto 3000

### Verificar que Todo Está Corriendo

```bash
./verificar-sistema.sh
```

Este comando verifica:
- PostgreSQL (Docker)
- Backend (puerto 8080)
- Frontend (puerto 3000)

### Detener Todo el Sistema

```bash
./stop-simple.sh
```

---

## OPCIÓN 2: Ejecución Manual (Paso a Paso)

Si prefieres iniciar cada componente manualmente:

### Paso 1: Iniciar PostgreSQL

```bash
# Desde la raíz del proyecto
docker-compose up -d postgres

# Verificar que está corriendo
docker ps | grep comercial-postgres

# Esperar 10 segundos para que PostgreSQL esté listo
```

### Paso 2: Compilar e Iniciar el Backend

```bash
# Ir al directorio del backend
cd ImpulsoComercialBack/PComercialTest

# Compilar el proyecto (primera vez o después de cambios)
mvn clean package -DskipTests

# Iniciar el backend
java -jar target/comercial-api-1.0.0.jar
```

El backend estará disponible en: `http://localhost:8080`

**Nota:** Deja esta terminal abierta. El backend se ejecuta en primer plano.

### Paso 3: Iniciar el Frontend

Abre una **nueva terminal** y ejecuta:

```bash
# Ir al directorio del frontend
cd frontend

# Instalar dependencias (solo la primera vez)
npm install

# Iniciar el servidor de desarrollo
npm run dev
```

El frontend estará disponible en: `http://localhost:3000`

**Nota:** Deja esta terminal abierta. El frontend se ejecuta en primer plano.

---

## Verificación del Sistema

### URLs Disponibles

Una vez que todo esté corriendo, verifica las siguientes URLs en tu navegador:

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **Frontend** | http://localhost:3000 | Aplicación web principal |
| **Backend API** | http://localhost:8080 | API REST |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | Documentación interactiva de la API |
| **Health Check** | http://localhost:8080/actuator/health | Estado del backend |

### Verificación por Terminal

```bash
# Verificar PostgreSQL
docker ps | grep comercial-postgres
# Debe mostrar: comercial-postgres ... Up ... (healthy)

# Verificar Backend (puerto 8080)
lsof -i :8080
# Debe mostrar un proceso Java

# Verificar Frontend (puerto 3000)
lsof -i :3000
# Debe mostrar un proceso node

# Test de salud del backend
curl http://localhost:8080/actuator/health
# Debe responder: {"status":"UP"}
```

---

## Credenciales de Base de Datos

Si necesitas conectarte directamente a PostgreSQL:

```
Host:     localhost
Puerto:   5433
Database: comercial_db
Usuario:  postgres
Password: postgres
```

### Conectar con psql

```bash
docker exec -it comercial-postgres psql -U postgres -d comercial_db
```

### Conectar con DBeaver o pgAdmin

Usa las credenciales anteriores en tu cliente de base de datos preferido.

---

## Solución de Problemas Comunes

### Problema 1: Puerto 8080 ya está en uso

**Error:**
```
Port 8080 was already in use
```

**Solución:**
```bash
# Ver qué está usando el puerto
lsof -i :8080

# Matar el proceso (reemplaza PID con el número que aparece)
kill -9 PID

# O usar este comando directo
kill -9 $(lsof -t -i:8080)
```

### Problema 2: Puerto 3000 ya está en uso

**Error:**
```
Port 3000 is already in use
```

**Solución:**
```bash
# Matar el proceso en el puerto 3000
kill -9 $(lsof -t -i:3000)
```

### Problema 3: Node.js muy antiguo

**Error:**
```
Node.js version >= v18.17.0 is required
```

**Solución:**
```bash
# Instalar Node.js 20 con Homebrew (macOS)
brew install node@20
brew unlink node
brew link --overwrite node@20

# Verificar versión
node --version
```

### Problema 4: PostgreSQL no inicia

**Error:**
```
Database connection failed
```

**Solución:**
```bash
# Detener y eliminar el contenedor
docker-compose down

# Reiniciar PostgreSQL
docker-compose up -d postgres

# Esperar 10 segundos
sleep 10

# Verificar salud
docker exec comercial-postgres pg_isready -U postgres
```

### Problema 5: Error de compilación en el Backend

**Error:**
```
BUILD FAILURE
```

**Solución:**
```bash
# Limpiar completamente
cd ImpulsoComercialBack/PComercialTest
mvn clean

# Compilar sin tests
mvn package -DskipTests

# Verificar versión de Java
java --version
# Debe ser Java 21
```

### Problema 6: Frontend no carga (página en blanco)

**Solución:**
```bash
cd frontend

# Limpiar caché de Next.js
rm -rf .next

# Reinstalar dependencias
rm -rf node_modules
npm install

# Reiniciar
npm run dev
```

### Problema 7: Docker no está corriendo

**Error:**
```
Cannot connect to the Docker daemon
```

**Solución:**
- Abre Docker Desktop
- Espera a que inicie completamente
- Vuelve a ejecutar el comando

---

## Comandos Útiles

### Ver Logs

```bash
# Logs del Backend
tail -f logs/backend.log

# Logs del Frontend
tail -f logs/frontend.log

# Logs de PostgreSQL
docker logs -f comercial-postgres
```

### Reiniciar Servicios

```bash
# Reiniciar todo
./stop-simple.sh
sleep 5
./start-simple.sh

# Reiniciar solo el Backend
cd ImpulsoComercialBack/PComercialTest
mvn clean package -DskipTests
java -jar target/comercial-api-1.0.0.jar

# Reiniciar solo el Frontend
cd frontend
npm run dev
```

### Limpiar y Reiniciar Completamente

```bash
# Detener todo
./stop-simple.sh

# Limpiar Docker
docker-compose down -v

# Limpiar Frontend
cd frontend
rm -rf .next node_modules
npm install

# Limpiar Backend
cd ../ImpulsoComercialBack/PComercialTest
mvn clean

# Reiniciar todo
cd ../..
./start-simple.sh
```

---

## Flujo de Trabajo Diario

### Inicio del Día

```bash
# Ir al directorio del proyecto
cd ruta/al/proyecto/ImpulsoComercialBack

# Actualizar código (si hay cambios en el repositorio)
git pull

# Iniciar el sistema
./start-simple.sh

# Esperar 2-3 minutos
# Abrir http://localhost:3000 en el navegador
```

### Durante el Desarrollo

- **Frontend:** Los cambios se reflejan automáticamente (hot-reload)
- **Backend:** Necesitas reiniciar el backend después de hacer cambios en el código Java
- **Base de Datos:** Siempre está corriendo en Docker

### Fin del Día

```bash
# Detener todo
./stop-simple.sh
```

---

## Actualizar el Código

Cuando haya actualizaciones en el repositorio:

```bash
# Detener el sistema
./stop-simple.sh

# Actualizar código
git pull

# Si hay cambios en el Backend
cd ImpulsoComercialBack/PComercialTest
mvn clean package -DskipTests
cd ../..

# Si hay cambios en el Frontend
cd frontend
npm install
cd ..

# Reiniciar el sistema
./start-simple.sh
```

---

## Notas Importantes

### Para macOS

- Los scripts `.sh` ya tienen permisos de ejecución
- Si tienes problemas, ejecuta: `chmod +x *.sh`

### Para Windows

Si estás en Windows, usa **Git Bash** o **WSL2** para ejecutar los scripts `.sh`.

Alternativamente, ejecuta los comandos manualmente siguiendo la **OPCIÓN 2: Ejecución Manual**.

### Puertos Utilizados

Asegúrate de que estos puertos estén libres:
- **3000:** Frontend (Next.js)
- **8080:** Backend (Spring Boot)
- **5433:** PostgreSQL (Docker)

**Última actualización:** Octubre 2025  
**Versión del sistema:** 1.0.0
