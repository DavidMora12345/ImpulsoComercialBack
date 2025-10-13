# ⚡ INICIO RÁPIDO

## 🚀 Opción 1: Script Automático (RECOMENDADO)

```bash
cd /Users/lehends/new_comercial

# Iniciar todo
./start.sh

# Detener todo
./stop.sh
```

**¡Eso es todo!** El script hace todo automáticamente:
- ✅ Verifica requisitos
- ✅ Crea la base de datos si no existe
- ✅ Compila el backend
- ✅ Inicia backend y frontend
- ✅ Muestra las URLs

---

## 🔧 Opción 2: Manual (Paso a Paso)

### 1. Base de Datos
```bash
psql postgres -c "CREATE DATABASE comercial_db;"
psql comercial_db -c "CREATE EXTENSION IF NOT EXISTS postgis;"
```

### 2. Backend
```bash
cd /Users/lehends/new_comercial/ImpulsoComercialBack/PComercialTest

# Configurar variables
export DB_USERNAME=postgres
export DB_PASSWORD=postgres

# Compilar y ejecutar
mvn clean install -DskipTests
mvn spring-boot:run
```

### 3. Frontend (en otra terminal)
```bash
cd /Users/lehends/new_comercial/frontend

# Instalar dependencias (solo la primera vez)
npm install

# Copiar configuración (solo la primera vez)
cp .env.local.example .env.local

# Ejecutar
npm run dev
```

---

## 📍 URLs

Una vez iniciado:

- **Frontend:** http://localhost:3000
- **Backend:** http://localhost:8080
- **Swagger:** http://localhost:8080/swagger-ui.html

---

## 🚨 Solución Rápida de Problemas

### Puerto ocupado
```bash
# Backend (8080)
lsof -ti:8080 | xargs kill -9

# Frontend (3000)
lsof -ti:3000 | xargs kill -9
```

### PostgreSQL no inicia
```bash
brew services start postgresql@16
```

### Error de Java
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

---

## 📝 Comandos Útiles

```bash
# Ver logs del backend
tail -f logs/backend.log

# Ver logs del frontend
tail -f logs/frontend.log

# Reiniciar todo
./stop.sh && ./start.sh

# Limpiar y recompilar backend
cd ImpulsoComercialBack/PComercialTest
mvn clean install -DskipTests
```

---

**¿Problemas?** Revisa la [GUIA_INSTALACION.md](GUIA_INSTALACION.md) completa.
