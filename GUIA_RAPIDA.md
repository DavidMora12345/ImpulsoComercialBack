# ⚡ GUÍA RÁPIDA - SISTEMA COMERCIAL MUNICIPAL

## 🚀 INICIAR EL SISTEMA

```bash
./start-simple.sh
```

Espera 2-3 minutos y listo! ✅

---

## ✅ VERIFICAR QUE TODO ESTÉ CORRIENDO

```bash
./verificar-sistema.sh
```

O abre en tu navegador:
- **Frontend:** http://localhost:3000
- **Backend:** http://localhost:8080/actuator/health

---

## 🛑 DETENER EL SISTEMA

```bash
./stop-simple.sh
```

---

## 📝 VER LOGS

```bash
# Backend
tail -f logs/backend.log

# Frontend
tail -f logs/frontend.log

# PostgreSQL
docker logs -f comercial-postgres
```

---

## 🔧 SOLUCIÓN RÁPIDA DE PROBLEMAS

### Puerto ocupado:
```bash
# Liberar puerto 8080 (Backend)
kill -9 $(lsof -t -i:8080)

# Liberar puerto 3000 (Frontend)
kill -9 $(lsof -t -i:3000)
```

### Reiniciar todo:
```bash
./stop-simple.sh
sleep 5
./start-simple.sh
```

### Limpiar y reiniciar:
```bash
# Detener
./stop-simple.sh

# Limpiar Docker
docker-compose down -v

# Limpiar frontend
cd frontend && rm -rf .next node_modules && npm install && cd ..

# Reiniciar
./start-simple.sh
```

---

## 📍 URLS IMPORTANTES

| Servicio | URL |
|----------|-----|
| Frontend | http://localhost:3000 |
| Backend | http://localhost:8080 |
| Swagger | http://localhost:8080/swagger-ui.html |
| Health | http://localhost:8080/actuator/health |

---

## 🆘 AYUDA

**¿No funciona?**

1. Ejecuta: `./verificar-sistema.sh`
2. Revisa los logs
3. Consulta el [Manual Completo](MANUAL_INICIO_SISTEMA.md)

---

**¡Eso es todo!** 🎉
