#!/bin/bash

# Script de inicio SIMPLE - Solo usa Docker para PostgreSQL
# Backend y Frontend corren localmente

set -e

echo "🚀 Iniciando Sistema Comercial Municipal (Modo Simple)..."
echo ""

GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m'

# 1. Iniciar PostgreSQL con Docker
echo -e "${BLUE}🐳 Iniciando PostgreSQL + PostGIS con Docker...${NC}"
docker-compose up -d postgres

echo -e "${BLUE}⏳ Esperando a que PostgreSQL esté listo...${NC}"
for i in {1..30}; do
    if docker exec comercial-postgres pg_isready -U postgres > /dev/null 2>&1; then
        echo -e "${GREEN}✅ PostgreSQL está listo${NC}"
        break
    fi
    echo -n "."
    sleep 2
done
echo ""

# Verificar que PostGIS esté habilitado
echo -e "${BLUE}🗺️  Verificando PostGIS...${NC}"
docker exec comercial-postgres psql -U postgres -d comercial_db -c "CREATE EXTENSION IF NOT EXISTS postgis;" 2>/dev/null || true
docker exec comercial-postgres psql -U postgres -d comercial_db -c "SELECT PostGIS_version();" > /dev/null 2>&1 && echo -e "${GREEN}✅ PostGIS habilitado${NC}" || echo -e "${YELLOW}⚠️  PostGIS no disponible${NC}"
echo ""

# 2. Detener PostgreSQL local si está corriendo
echo -e "${BLUE}🛑 Deteniendo PostgreSQL local...${NC}"
brew services stop postgresql@17 2>/dev/null || true
brew services stop postgresql@16 2>/dev/null || true
brew services stop postgresql@14 2>/dev/null || true

# 3. Configurar variables de entorno para el backend
export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"
export JAVA_HOME="/opt/homebrew/opt/openjdk@21"
export DB_HOST=localhost
export DB_PORT=5433
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970

# 4. Compilar y ejecutar backend
echo -e "${BLUE}🔨 Compilando backend...${NC}"
cd ImpulsoComercialBack/PComercialTest
mvn clean package -DskipTests
echo -e "${GREEN}✅ Backend compilado${NC}"
echo ""

echo -e "${BLUE}🚀 Iniciando backend...${NC}"
java -jar target/comercial-api-1.0.0.jar > ../../logs/backend.log 2>&1 &
BACKEND_PID=$!
echo "  PID: $BACKEND_PID"
echo $BACKEND_PID > ../../.backend.pid
echo -e "${GREEN}✅ Backend iniciado en http://localhost:8080${NC}"
echo ""

# Esperar a que el backend esté listo
echo -e "${BLUE}⏳ Esperando a que el backend esté listo...${NC}"
for i in {1..30}; do
    if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
        echo -e "${GREEN}✅ Backend está listo${NC}"
        break
    fi
    echo -n "."
    sleep 2
done
echo ""

# 4. Configurar e iniciar frontend
cd ../../frontend

if [ ! -f ".env.local" ]; then
    echo -e "${BLUE}⚙️  Configurando frontend...${NC}"
    cp .env.local.example .env.local
    echo -e "${GREEN}✅ Archivo .env.local creado${NC}"
fi

if [ ! -d "node_modules" ]; then
    echo -e "${BLUE}📦 Instalando dependencias del frontend...${NC}"
    npm install
    echo -e "${GREEN}✅ Dependencias instaladas${NC}"
fi
echo ""

echo -e "${BLUE}🚀 Iniciando frontend...${NC}"
npm run dev > ../logs/frontend.log 2>&1 &
FRONTEND_PID=$!
echo "  PID: $FRONTEND_PID"
echo $FRONTEND_PID > ../.frontend.pid
echo -e "${GREEN}✅ Frontend iniciado en http://localhost:3000${NC}"
echo ""

# Resumen
echo -e "${GREEN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${GREEN}🎉 Sistema iniciado correctamente!${NC}"
echo -e "${GREEN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo ""
echo -e "${BLUE}📍 URLs:${NC}"
echo "  Frontend:    http://localhost:3000"
echo "  Backend:     http://localhost:8080"
echo "  Swagger:     http://localhost:8080/swagger-ui.html"
echo "  PostgreSQL:  localhost:5433 (Docker)"
echo ""
echo -e "${BLUE}📝 Logs:${NC}"
echo "  Backend:   tail -f logs/backend.log"
echo "  Frontend:  tail -f logs/frontend.log"
echo "  Postgres:  docker logs -f comercial-postgres"
echo ""
echo -e "${BLUE}🛑 Para detener:${NC}"
echo "  ./stop-simple.sh"
echo ""
echo -e "${YELLOW}⚠️  Presiona Ctrl+C para detener (o usa ./stop-simple.sh)${NC}"
echo ""

wait
