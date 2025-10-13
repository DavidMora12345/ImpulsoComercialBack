# 🚀 CÓMO EJECUTAR EL PROYECTO

## ⚠️ PROBLEMA ACTUAL: PostGIS

Tu PostgreSQL 16 local no es compatible con PostGIS 3.6 (que requiere PostgreSQL 17).

## ✅ SOLUCIÓN: 3 OPCIONES

---

## **OPCIÓN 1: Docker (RECOMENDADO - MÁS FÁCIL)**

### Requisitos:
- Docker Desktop instalado y corriendo

### Pasos:

1. **Iniciar Docker Desktop**
   ```bash
   # Abre Docker Desktop desde Applications
   open -a Docker
   ```

2. **Ejecutar el script**
   ```bash
   cd /Users/lehends/new_comercial
   ./start-simple.sh
   ```

Esto levantará:
- ✅ PostgreSQL 16 + PostGIS en Docker (puerto 5433)
- ✅ Backend Spring Boot local (puerto 8080)
- ✅ Frontend Next.js local (puerto 3000)

**URLs:**
- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

**Para detener:**
```bash
./stop-simple.sh
```

---

## **OPCIÓN 2: Sin PostGIS (TEMPORAL)**

Si no necesitas las funciones GIS por ahora:

### 1. Crear base de datos sin PostGIS
```bash
psql postgres -c "DROP DATABASE IF EXISTS comercial_db;"
psql postgres -c "CREATE DATABASE comercial_db;"
```

### 2. Comentar PostGIS en el código

Edita: `ImpulsoComercialBack/PComercialTest/src/main/resources/db/migration/V2__create_schema_catastros.sql`

Comenta estas líneas:
```sql
-- CREATE EXTENSION IF NOT EXISTS postgis;
-- geometria GEOMETRY(POLYGON, 4326),
-- CREATE INDEX idx_predio_geometria ON catastros.predio USING GIST(geometria);
```

### 3. Ejecutar manualmente

**Terminal 1 - Backend:**
```bash
cd /Users/lehends/new_comercial/ImpulsoComercialBack/PComercialTest
export DB_PORT=5432
mvn spring-boot:run
```

**Terminal 2 - Frontend:**
```bash
cd /Users/lehends/new_comercial/frontend
npm install
npm run dev
```

---

## **OPCIÓN 3: Actualizar PostgreSQL a 17**

### 1. Backup (si tienes datos importantes)
```bash
pg_dumpall > backup_all.sql
```

### 2. Instalar PostgreSQL 17
```bash
brew install postgresql@17
brew services stop postgresql@16
brew services start postgresql@17
```

### 3. Actualizar PATH
```bash
echo 'export PATH="/opt/homebrew/opt/postgresql@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

### 4. Crear base de datos
```bash
psql postgres -c "CREATE DATABASE comercial_db;"
psql comercial_db -c "CREATE EXTENSION IF NOT EXISTS postgis;"
```

### 5. Ejecutar
```bash
cd /Users/lehends/new_comercial
export DB_PORT=5432
./start.sh
```

---

## 📋 RESUMEN DE COMANDOS

### Con Docker (Opción 1):
```bash
# Iniciar
./start-simple.sh

# Detener
./stop-simple.sh
```

### Sin Docker (Opción 2):
```bash
# Terminal 1
cd ImpulsoComercialBack/PComercialTest
mvn spring-boot:run

# Terminal 2
cd frontend
npm run dev
```

---

## 🔍 VERIFICAR QUE FUNCIONA

### Backend:
```bash
curl http://localhost:8080/actuator/health
# Debe responder: {"status":"UP"}
```

### Frontend:
```bash
open http://localhost:3000
```

### Swagger:
```bash
open http://localhost:8080/swagger-ui.html
```

---

## 🚨 PROBLEMAS COMUNES

### "Docker daemon not running"
```bash
# Abre Docker Desktop
open -a Docker
# Espera 30 segundos y vuelve a intentar
```

### "Port already in use"
```bash
# Puerto 8080
lsof -ti:8080 | xargs kill -9

# Puerto 3000
lsof -ti:3000 | xargs kill -9

# Puerto 5433
lsof -ti:5433 | xargs kill -9
```

### "Connection refused" (PostgreSQL)
```bash
# Verificar que PostgreSQL está corriendo
brew services list

# Si usas Docker
docker ps | grep postgres
```

---

## 💡 RECOMENDACIÓN

**Usa la OPCIÓN 1 (Docker)** porque:
- ✅ No requiere actualizar PostgreSQL
- ✅ PostGIS funciona perfectamente
- ✅ Aislado de tu sistema
- ✅ Fácil de limpiar
- ✅ Un solo comando para iniciar todo

---

**¿Listo?** Abre Docker Desktop y ejecuta:
```bash
./start-simple.sh
```

🎉 ¡Y VUAMOOOS!
