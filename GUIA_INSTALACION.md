# 🚀 GUÍA DE INSTALACIÓN - Sistema Comercial Municipal

## 📋 REQUISITOS PREVIOS

### Software Necesario:
- ✅ **Java 21** (JDK)
- ✅ **Maven 3.9+**
- ✅ **Node.js 18+** o **20+**
- ✅ **PostgreSQL 16+**
- ✅ **Git**

---

## 🔧 PASO 1: VERIFICAR INSTALACIONES

### 1.1 Verificar Java
```bash
java -version
# Debe mostrar: openjdk version "21.x.x"
```

**Si no tienes Java 21:**
```bash
# macOS (con Homebrew)
brew install openjdk@21

# Configurar JAVA_HOME
export JAVA_HOME=/opt/homebrew/opt/openjdk@21
export PATH="$JAVA_HOME/bin:$PATH"
```

### 1.2 Verificar Maven
```bash
mvn -version
# Debe mostrar: Apache Maven 3.9.x
```

**Si no tienes Maven:**
```bash
# macOS
brew install maven
```

### 1.3 Verificar Node.js
```bash
node -v
# Debe mostrar: v18.x.x o v20.x.x

npm -v
# Debe mostrar: 9.x.x o 10.x.x
```

**Si no tienes Node.js:**
```bash
# macOS
brew install node@20
```

### 1.4 Verificar PostgreSQL
```bash
psql --version
# Debe mostrar: psql (PostgreSQL) 16.x
```

**Si no tienes PostgreSQL:**
```bash
# macOS
brew install postgresql@16
brew services start postgresql@16
```

---

## 🗄️ PASO 2: CONFIGURAR BASE DE DATOS

### 2.1 Crear Base de Datos
```bash
# Conectar a PostgreSQL
psql postgres

# Dentro de psql:
CREATE DATABASE comercial_db;
\c comercial_db
CREATE EXTENSION IF NOT EXISTS postgis;
\q
```

### 2.2 Verificar PostGIS
```bash
psql -d comercial_db -c "SELECT PostGIS_version();"
```

---

## ⚙️ PASO 3: CONFIGURAR BACKEND

### 3.1 Navegar al directorio
```bash
cd /Users/lehends/new_comercial/ImpulsoComercialBack/PComercialTest
```

### 3.2 Configurar variables de entorno
```bash
# Crear archivo .env (opcional)
cat > .env << EOF
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
EOF

# O exportar directamente
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
```

### 3.3 Compilar el proyecto
```bash
# Limpiar y compilar
mvn clean install

# Si hay errores de tests, saltar tests temporalmente
mvn clean install -DskipTests
```

### 3.4 Ejecutar el backend
```bash
mvn spring-boot:run
```

**✅ El backend estará corriendo en:** http://localhost:8080

**Verificar que funciona:**
```bash
# En otra terminal
curl http://localhost:8080/swagger-ui.html
```

---

## 🎨 PASO 4: CONFIGURAR FRONTEND

### 4.1 Navegar al directorio
```bash
cd /Users/lehends/new_comercial/frontend
```

### 4.2 Instalar dependencias
```bash
npm install

# Si hay errores, intenta:
npm install --legacy-peer-deps
```

### 4.3 Configurar variables de entorno
```bash
# Copiar archivo de ejemplo
cp .env.local.example .env.local

# Editar si es necesario
nano .env.local
```

Contenido de `.env.local`:
```bash
NEXT_PUBLIC_API_URL=http://localhost:8080
NEXT_PUBLIC_JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
```

### 4.4 Ejecutar el frontend
```bash
npm run dev
```

**✅ El frontend estará corriendo en:** http://localhost:3000

---

## 🐳 OPCIÓN ALTERNATIVA: DOCKER

### Si prefieres usar Docker:

```bash
cd /Users/lehends/new_comercial

# Levantar todo
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener todo
docker-compose down
```

---

## ✅ VERIFICAR QUE TODO FUNCIONA

### 1. Backend
```bash
# Swagger UI
open http://localhost:8080/swagger-ui.html

# Health check
curl http://localhost:8080/actuator/health
```

### 2. Frontend
```bash
# Abrir en navegador
open http://localhost:3000
```

### 3. Base de Datos
```bash
# Verificar que las tablas se crearon
psql -d comercial_db -c "\dt clientes.*"
psql -d comercial_db -c "\dt catastros.*"
psql -d comercial_db -c "\dt recaudacion.*"
```

---

## 🚨 SOLUCIÓN DE PROBLEMAS COMUNES

### Error: "Port 8080 already in use"
```bash
# Encontrar proceso usando el puerto
lsof -i :8080

# Matar el proceso
kill -9 <PID>
```

### Error: "Port 3000 already in use"
```bash
# Encontrar proceso usando el puerto
lsof -i :3000

# Matar el proceso
kill -9 <PID>
```

### Error: "Connection refused" (PostgreSQL)
```bash
# Verificar que PostgreSQL está corriendo
brew services list

# Iniciar PostgreSQL
brew services start postgresql@16
```

### Error: "JAVA_HOME not set"
```bash
# Encontrar instalación de Java
/usr/libexec/java_home -V

# Configurar JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

### Error de compilación Maven
```bash
# Limpiar cache de Maven
mvn clean

# Forzar actualización de dependencias
mvn clean install -U

# Saltar tests
mvn clean install -DskipTests
```

---

## 📝 COMANDOS ÚTILES

### Backend
```bash
# Compilar sin ejecutar
mvn clean package

# Ejecutar JAR directamente
java -jar target/PComercialTest-0.0.1-SNAPSHOT.jar

# Ver logs
tail -f logs/comercial-api.log

# Ejecutar con perfil específico
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Frontend
```bash
# Build para producción
npm run build

# Ejecutar build de producción
npm start

# Limpiar cache
rm -rf .next node_modules
npm install
```

### Base de Datos
```bash
# Backup
pg_dump comercial_db > backup.sql

# Restore
psql comercial_db < backup.sql

# Conectar a la base de datos
psql -d comercial_db
```

---

## 🎯 PRÓXIMOS PASOS

Una vez que todo esté corriendo:

1. ✅ Accede a Swagger: http://localhost:8080/swagger-ui.html
2. ✅ Accede al Frontend: http://localhost:3000
3. ✅ Prueba el endpoint de clientes
4. ✅ Revisa los logs para verificar que no hay errores

---

## 📞 AYUDA

Si tienes problemas:
1. Revisa los logs del backend: `logs/comercial-api.log`
2. Revisa la consola del frontend
3. Verifica que PostgreSQL está corriendo
4. Verifica que los puertos 8080 y 3000 están libres

---

**¡Listo para desarrollar!** 🚀
