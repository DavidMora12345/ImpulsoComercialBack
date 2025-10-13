# 🛠️ COMANDOS ÚTILES - COPY & PASTE

## 🚀 INICIO Y PARADA

```bash
# Iniciar todo
cd /Users/lehends/new_comercial && ./start-simple.sh

# Detener todo
cd /Users/lehends/new_comercial && ./stop-simple.sh

# Verificar estado
cd /Users/lehends/new_comercial && ./verificar-sistema.sh
```

---

## ✅ VERIFICACIÓN COMPLETA (Copy & Paste)

```bash
echo "🔍 VERIFICANDO SISTEMA..." && \
echo "" && \
echo "1️⃣ PostgreSQL:" && docker ps | grep comercial-postgres && \
echo "" && \
echo "2️⃣ Backend:" && lsof -i :8080 && \
echo "" && \
echo "3️⃣ Frontend:" && lsof -i :3000 && \
echo "" && \
echo "4️⃣ Health:" && curl -s http://localhost:8080/actuator/health && \
echo "" && echo "" && \
echo "✅ Si ves todo, el sistema está OK!"
```

---

## 🛑 DETENER SERVICIOS ESPECÍFICOS

```bash
# Detener Backend
kill -9 $(lsof -t -i:8080)

# Detener Frontend
kill -9 $(lsof -t -i:3000)

# Detener PostgreSQL
docker stop comercial-postgres
```

---

## 📝 VER LOGS (Copy & Paste)

```bash
# Ver logs del Backend (últimas 50 líneas)
tail -50 /Users/lehends/new_comercial/logs/backend.log

# Ver logs del Frontend (últimas 50 líneas)
tail -50 /Users/lehends/new_comercial/logs/frontend.log

# Ver logs de PostgreSQL (últimas 50 líneas)
docker logs --tail 50 comercial-postgres

# Ver logs en tiempo real (Backend)
tail -f /Users/lehends/new_comercial/logs/backend.log

# Ver logs en tiempo real (Frontend)
tail -f /Users/lehends/new_comercial/logs/frontend.log
```

---

## 🔍 BUSCAR ERRORES

```bash
# Buscar errores en Backend
grep -i "error" /Users/lehends/new_comercial/logs/backend.log | tail -20

# Buscar errores en Frontend
grep -i "error" /Users/lehends/new_comercial/logs/frontend.log | tail -20

# Buscar excepciones en Backend
grep -i "exception" /Users/lehends/new_comercial/logs/backend.log | tail -20
```

---

## 🔧 LIMPIAR Y REINICIAR

```bash
# Limpiar TODO y reiniciar (Copy & Paste)
cd /Users/lehends/new_comercial && \
./stop-simple.sh && \
docker-compose down -v && \
cd frontend && rm -rf .next node_modules && npm install && \
cd ../ImpulsoComercialBack/PComercialTest && mvn clean && \
cd ../.. && \
./start-simple.sh
```

---

## 🐘 POSTGRESQL (Docker)

```bash
# Ver contenedores Docker
docker ps

# Ver logs de PostgreSQL
docker logs -f comercial-postgres

# Conectar a PostgreSQL
docker exec -it comercial-postgres psql -U postgres -d comercial_db

# Verificar salud
docker exec comercial-postgres pg_isready -U postgres

# Reiniciar PostgreSQL
docker restart comercial-postgres

# Detener PostgreSQL
docker stop comercial-postgres

# Iniciar PostgreSQL
docker start comercial-postgres
```

---

## 🔄 REINICIAR SERVICIOS

```bash
# Reiniciar solo Backend
cd /Users/lehends/new_comercial && \
kill -9 $(lsof -t -i:8080) && \
cd ImpulsoComercialBack/PComercialTest && \
java -jar target/comercial-api-1.0.0.jar > ../../logs/backend.log 2>&1 &

# Reiniciar solo Frontend
cd /Users/lehends/new_comercial && \
kill -9 $(lsof -t -i:3000) && \
cd frontend && \
npm run dev > ../logs/frontend.log 2>&1 &

# Reiniciar PostgreSQL
docker restart comercial-postgres
```

---

## 🧹 LIMPIAR CACHÉ

```bash
# Limpiar caché del Frontend
cd /Users/lehends/new_comercial/frontend && \
rm -rf .next && \
npm run dev

# Limpiar y recompilar Backend
cd /Users/lehends/new_comercial/ImpulsoComercialBack/PComercialTest && \
mvn clean package -DskipTests
```

---

## 📊 MONITOREO

```bash
# Ver uso de puertos
lsof -i :3000 -i :8080 -i :5433

# Ver procesos Java
ps aux | grep java

# Ver procesos Node
ps aux | grep node

# Ver uso de memoria
docker stats comercial-postgres
```

---

## 🌐 ABRIR EN NAVEGADOR (macOS)

```bash
# Abrir Frontend
open http://localhost:3000

# Abrir Swagger
open http://localhost:8080/swagger-ui.html

# Abrir Health Check
open http://localhost:8080/actuator/health
```

---

## 🔑 CREDENCIALES

### PostgreSQL:
```
Host:     localhost
Puerto:   5433
Database: comercial_db
Usuario:  postgres
Password: postgres
```

### Conectar con psql:
```bash
docker exec -it comercial-postgres psql -U postgres -d comercial_db
```

### Conectar con DBeaver/pgAdmin:
```
Host:     localhost
Port:     5433
Database: comercial_db
Username: postgres
Password: postgres
```

---

## 🆘 SOLUCIÓN RÁPIDA

### El sistema no inicia:
```bash
cd /Users/lehends/new_comercial && \
./stop-simple.sh && \
sleep 5 && \
./start-simple.sh
```

### Puerto ocupado:
```bash
# Liberar todos los puertos
kill -9 $(lsof -t -i:3000) 2>/dev/null || true && \
kill -9 $(lsof -t -i:8080) 2>/dev/null || true
```

### Node.js antiguo:
```bash
brew install node@20 && \
brew unlink node && \
brew link --overwrite node@20 && \
node --version
```

### PostgreSQL no responde:
```bash
docker restart comercial-postgres && \
sleep 10 && \
docker exec comercial-postgres pg_isready -U postgres
```

---

## 📱 NOTIFICACIÓN AL TERMINAR

```bash
# Ejecutar comando y notificar cuando termine (macOS)
./start-simple.sh && osascript -e 'display notification "Sistema iniciado" with title "Comercial"'
```

---

**¡Guarda este archivo para referencia rápida!** 📌
