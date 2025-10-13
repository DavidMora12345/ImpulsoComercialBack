# 🚀 MANUAL DE INICIO - SISTEMA COMERCIAL MUNICIPAL

## 📋 ÍNDICE

1. [Inicio Rápido](#inicio-rápido)
2. [Verificar que Todo Esté Corriendo](#verificar-que-todo-esté-corriendo)
3. [Detener el Sistema](#detener-el-sistema)
4. [Ver Logs](#ver-logs)
5. [Solución de Problemas](#solución-de-problemas)

---

## 🚀 INICIO RÁPIDO

### **Opción 1: Script Automático (RECOMENDADO)**

```bash
# 1. Ir al directorio del proyecto
cd /Users/lehends/new_comercial

# 2. Ejecutar el script de inicio
./start-simple.sh
```

**¡Eso es todo!** El script hace TODO automáticamente:
- ✅ Inicia PostgreSQL con Docker
- ✅ Compila el backend
- ✅ Inicia el backend
- ✅ Instala dependencias del frontend (si no están)
- ✅ Inicia el frontend

**Tiempo estimado:** 2-3 minutos

---

### **Opción 2: Inicio Manual (Paso a Paso)**

#### **Paso 1: Iniciar PostgreSQL**

```bash
cd /Users/lehends/new_comercial
docker-compose up -d postgres
```

Espera 10 segundos para que PostgreSQL esté listo.

#### **Paso 2: Iniciar Backend**

```bash
cd ImpulsoComercialBack/PComercialTest
mvn clean package -DskipTests
java -jar target/comercial-api-1.0.0.jar
```

Espera a ver el mensaje: "Started ComercialApplication in X seconds"

#### **Paso 3: Iniciar Frontend** (en otra terminal)

```bash
cd /Users/lehends/new_comercial/frontend
npm run dev
```

Espera a ver: "✓ Ready in X.Xs"

---

## ✅ VERIFICAR QUE TODO ESTÉ CORRIENDO

### **Método 1: Verificación Rápida (URLs)**

Abre tu navegador y verifica estas URLs:

| Servicio | URL | Estado Esperado |
|----------|-----|-----------------|
| **Frontend** | http://localhost:3000 | ✅ Página carga correctamente |
| **Backend** | http://localhost:8080/actuator/health | ✅ Muestra `{"status":"UP"}` |
| **Swagger** | http://localhost:8080/swagger-ui.html | ✅ Documentación API |
| **PostgreSQL** | - | ✅ Ver método 2 |

---

### **Método 2: Verificación por Terminal**

```bash
# Ir al directorio del proyecto
cd /Users/lehends/new_comercial

# Ejecutar script de verificación
./verificar-sistema.sh
```

**O manualmente:**

```bash
# 1. Verificar PostgreSQL (Docker)
docker ps | grep comercial-postgres
# ✅ Debe mostrar: comercial-postgres ... Up ... (healthy)

# 2. Verificar Backend (Puerto 8080)
lsof -i :8080
# ✅ Debe mostrar: java ... *:http-alt (LISTEN)

# 3. Verificar Frontend (Puerto 3000)
lsof -i :3000
# ✅ Debe mostrar: node ... *:hbci (LISTEN)

# 4. Test de salud del Backend
curl http://localhost:8080/actuator/health
# ✅ Debe mostrar: {"status":"UP"}

# 5. Test del Frontend
curl -I http://localhost:3000
# ✅ Debe mostrar: HTTP/1.1 200 OK
```

---

### **Método 3: Verificación Visual Completa**

#### **✅ CHECKLIST:**

- [ ] **PostgreSQL corriendo**
  ```bash
  docker ps
  # Busca: comercial-postgres ... Up ... (healthy)
  ```

- [ ] **Backend corriendo**
  ```bash
  lsof -i :8080
  # Debe mostrar proceso Java
  ```

- [ ] **Frontend corriendo**
  ```bash
  lsof -i :3000
  # Debe mostrar proceso node
  ```

- [ ] **Frontend carga en navegador**
  - Abrir: http://localhost:3000
  - Debe ver: Header con logo "SC", sidebar, etc.

- [ ] **Backend responde**
  - Abrir: http://localhost:8080/actuator/health
  - Debe ver: `{"status":"UP"}`

- [ ] **Swagger funciona**
  - Abrir: http://localhost:8080/swagger-ui.html
  - Debe ver: Documentación de APIs

---

## 🛑 DETENER EL SISTEMA

### **Opción 1: Script Automático (RECOMENDADO)**

```bash
cd /Users/lehends/new_comercial
./stop-simple.sh
```

---

### **Opción 2: Detener Manualmente**

```bash
# 1. Detener Frontend (Ctrl+C en la terminal donde corre)
# O buscar y matar el proceso:
kill -9 $(lsof -t -i:3000)

# 2. Detener Backend (Ctrl+C en la terminal donde corre)
# O buscar y matar el proceso:
kill -9 $(lsof -t -i:8080)

# 3. Detener PostgreSQL
docker-compose down postgres
```

---

## 📝 VER LOGS

### **Logs en Tiempo Real:**

```bash
# Backend
tail -f logs/backend.log

# Frontend
tail -f logs/frontend.log

# PostgreSQL (Docker)
docker logs -f comercial-postgres
```

### **Ver Últimas 50 Líneas:**

```bash
# Backend
tail -50 logs/backend.log

# Frontend
tail -50 logs/frontend.log

# PostgreSQL
docker logs --tail 50 comercial-postgres
```

### **Buscar Errores:**

```bash
# Errores en Backend
grep -i "error" logs/backend.log

# Errores en Frontend
grep -i "error" logs/frontend.log
```

---

## 🔧 SOLUCIÓN DE PROBLEMAS

### **Problema 1: Puerto 8080 ya está en uso**

**Error:**
```
Port 8080 was already in use.
```

**Solución:**
```bash
# Ver qué está usando el puerto
lsof -i :8080

# Matar el proceso
kill -9 $(lsof -t -i:8080)

# Reiniciar
./start-simple.sh
```

---

### **Problema 2: Puerto 3000 ya está en uso**

**Error:**
```
Port 3000 is already in use
```

**Solución:**
```bash
# Matar el proceso
kill -9 $(lsof -t -i:3000)

# Reiniciar frontend
cd frontend
npm run dev
```

---

### **Problema 3: Node.js muy antiguo**

**Error:**
```
Node.js version >= v18.17.0 is required
```

**Solución:**
```bash
# Instalar Node 20
brew install node@20

# Enlazar
brew unlink node
brew link --overwrite node@20

# Agregar al PATH
echo 'export PATH="/opt/homebrew/opt/node@20/bin:$PATH"' >> ~/.zshrc

# Recargar terminal
source ~/.zshrc

# Verificar versión
node --version  # Debe mostrar v20.x.x
```

---

### **Problema 4: PostgreSQL no inicia**

**Error:**
```
Database connection failed
```

**Solución:**
```bash
# Detener contenedor
docker-compose down postgres

# Eliminar volumen (CUIDADO: borra datos)
docker volume rm new_comercial_postgres_data

# Reiniciar
docker-compose up -d postgres

# Esperar 10 segundos
sleep 10

# Verificar salud
docker exec comercial-postgres pg_isready -U postgres
```

---

### **Problema 5: Frontend no carga (página en blanco)**

**Solución:**
```bash
cd frontend

# Limpiar caché
rm -rf .next
rm -rf node_modules

# Reinstalar dependencias
npm install

# Reiniciar
npm run dev
```

---

### **Problema 6: Backend no compila**

**Error:**
```
BUILD FAILURE
```

**Solución:**
```bash
cd ImpulsoComercialBack/PComercialTest

# Limpiar completamente
mvn clean

# Compilar sin tests
mvn package -DskipTests

# Si persiste, verificar Java
java --version  # Debe ser Java 21
```

---

## 🎯 COMANDOS ÚTILES

### **Ver Estado de Todo:**

```bash
# PostgreSQL
docker ps | grep postgres

# Backend
lsof -i :8080

# Frontend
lsof -i :3000

# Todo junto
echo "=== PostgreSQL ===" && docker ps | grep postgres && \
echo "=== Backend ===" && lsof -i :8080 && \
echo "=== Frontend ===" && lsof -i :3000
```

### **Reiniciar Todo:**

```bash
# Detener
./stop-simple.sh

# Esperar 5 segundos
sleep 5

# Iniciar
./start-simple.sh
```

### **Limpiar Todo (Reset Completo):**

```bash
# CUIDADO: Esto borra la base de datos

# Detener todo
./stop-simple.sh

# Limpiar Docker
docker-compose down -v

# Limpiar frontend
cd frontend
rm -rf .next node_modules
npm install

# Limpiar backend
cd ../ImpulsoComercialBack/PComercialTest
mvn clean

# Reiniciar
cd ../..
./start-simple.sh
```

---

## 📊 URLS IMPORTANTES

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **Frontend** | http://localhost:3000 | Aplicación web principal |
| **Backend API** | http://localhost:8080 | API REST |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | Documentación interactiva |
| **Health Check** | http://localhost:8080/actuator/health | Estado del backend |
| **PostgreSQL** | localhost:5433 | Base de datos (Docker) |

**Credenciales PostgreSQL:**
- Host: `localhost`
- Puerto: `5433`
- Database: `comercial_db`
- Usuario: `postgres`
- Password: `postgres`

---

## 🎓 FLUJO DE TRABAJO DIARIO

### **Inicio del Día:**

```bash
cd /Users/lehends/new_comercial
./start-simple.sh
```

Espera 2-3 minutos y abre: http://localhost:3000

### **Durante el Desarrollo:**

- Frontend con hot-reload: Los cambios se ven automáticamente
- Backend: Reiniciar si cambias código Java
- Base de datos: Siempre corriendo en Docker

### **Fin del Día:**

```bash
./stop-simple.sh
```

---

## 🆘 AYUDA RÁPIDA

**¿El sistema no inicia?**
1. Ejecuta: `./stop-simple.sh`
2. Espera 5 segundos
3. Ejecuta: `./start-simple.sh`
4. Revisa logs: `tail -f logs/backend.log` y `tail -f logs/frontend.log`

**¿Cambios no se ven?**
- Frontend: Refresca el navegador (Cmd+R o Ctrl+R)
- Backend: Reinicia el backend
- Caché: Limpia caché del navegador (Cmd+Shift+R)

**¿Necesitas ayuda?**
- Revisa los logs
- Busca errores en rojo
- Verifica que todos los puertos estén libres

---

## ✅ CHECKLIST DE VERIFICACIÓN COMPLETA

```bash
# Copiar y pegar este comando para verificar TODO:

echo "🔍 VERIFICANDO SISTEMA COMERCIAL MUNICIPAL..." && \
echo "" && \
echo "1️⃣ PostgreSQL:" && \
docker ps | grep comercial-postgres && \
echo "" && \
echo "2️⃣ Backend (Puerto 8080):" && \
lsof -i :8080 && \
echo "" && \
echo "3️⃣ Frontend (Puerto 3000):" && \
lsof -i :3000 && \
echo "" && \
echo "4️⃣ Health Check:" && \
curl -s http://localhost:8080/actuator/health && \
echo "" && \
echo "" && \
echo "✅ Si ves todo lo anterior, el sistema está CORRIENDO correctamente!"
```

---

**¡LISTO! 🎉**

Con este manual puedes iniciar, verificar y detener el sistema fácilmente.

**Recuerda:**
- Usa `./start-simple.sh` para iniciar
- Usa `./stop-simple.sh` para detener
- Abre http://localhost:3000 para ver la aplicación
