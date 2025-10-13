#!/bin/bash

# Script de inicio rápido para el Sistema Comercial Municipal
# Autor: Sistema Comercial
# Fecha: Octubre 2025

set -e

echo "🚀 Iniciando Sistema Comercial Municipal..."
echo ""

# Colores
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para verificar si un comando existe
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Verificar requisitos
echo -e "${BLUE}📋 Verificando requisitos...${NC}"

if ! command_exists java; then
    echo -e "${RED}❌ Java no está instalado${NC}"
    echo "Instala Java 21: brew install openjdk@21"
    exit 1
fi

if ! command_exists mvn; then
    echo -e "${RED}❌ Maven no está instalado${NC}"
    echo "Instala Maven: brew install maven"
    exit 1
fi

if ! command_exists node; then
    echo -e "${RED}❌ Node.js no está instalado${NC}"
    echo "Instala Node.js: brew install node@20"
    exit 1
fi

if ! command_exists psql; then
    echo -e "${RED}❌ PostgreSQL no está instalado${NC}"
    echo "Instala PostgreSQL: brew install postgresql@16"
    exit 1
fi

echo -e "${GREEN}✅ Todos los requisitos están instalados${NC}"
echo ""

# Verificar base de datos
echo -e "${BLUE}🗄️  Verificando base de datos...${NC}"
if psql -lqt | cut -d \| -f 1 | grep -qw comercial_db; then
    echo -e "${GREEN}✅ Base de datos 'comercial_db' existe${NC}"
else
    echo -e "${YELLOW}⚠️  Base de datos 'comercial_db' no existe${NC}"
    echo "Creando base de datos..."
    psql postgres -c "CREATE DATABASE comercial_db;"
    psql comercial_db -c "CREATE EXTENSION IF NOT EXISTS postgis;"
    echo -e "${GREEN}✅ Base de datos creada${NC}"
fi
echo ""

# Configurar variables de entorno
export DB_USERNAME=${DB_USERNAME:-postgres}
export DB_PASSWORD=${DB_PASSWORD:-postgres}
export JWT_SECRET=${JWT_SECRET:-404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}

echo -e "${BLUE}⚙️  Variables de entorno configuradas${NC}"
echo "  DB_USERNAME: $DB_USERNAME"
echo "  DB_PASSWORD: ****"
echo ""

# Compilar backend
echo -e "${BLUE}🔨 Compilando backend...${NC}"
cd ImpulsoComercialBack/PComercialTest
mvn clean install -DskipTests
echo -e "${GREEN}✅ Backend compilado${NC}"
echo ""

# Iniciar backend en background
echo -e "${BLUE}🚀 Iniciando backend...${NC}"
mvn spring-boot:run > ../../logs/backend.log 2>&1 &
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

# Configurar frontend
cd ../../frontend

if [ ! -f ".env.local" ]; then
    echo -e "${BLUE}⚙️  Configurando frontend...${NC}"
    cp .env.local.example .env.local
    echo -e "${GREEN}✅ Archivo .env.local creado${NC}"
fi

# Instalar dependencias del frontend si es necesario
if [ ! -d "node_modules" ]; then
    echo -e "${BLUE}📦 Instalando dependencias del frontend...${NC}"
    npm install
    echo -e "${GREEN}✅ Dependencias instaladas${NC}"
fi
echo ""

# Iniciar frontend en background
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
echo "  Frontend:  http://localhost:3000"
echo "  Backend:   http://localhost:8080"
echo "  Swagger:   http://localhost:8080/swagger-ui.html"
echo ""
echo -e "${BLUE}📝 Logs:${NC}"
echo "  Backend:   tail -f logs/backend.log"
echo "  Frontend:  tail -f logs/frontend.log"
echo ""
echo -e "${BLUE}🛑 Para detener:${NC}"
echo "  ./stop.sh"
echo ""
echo -e "${YELLOW}⚠️  Presiona Ctrl+C para detener ambos servicios${NC}"
echo ""

# Mantener el script corriendo
wait
