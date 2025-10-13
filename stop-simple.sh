#!/bin/bash

# Script para detener el Sistema Comercial Municipal (Modo Simple)

set -e

echo "🛑 Deteniendo Sistema Comercial Municipal..."
echo ""

GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

# Detener backend
if [ -f ".backend.pid" ]; then
    BACKEND_PID=$(cat .backend.pid)
    echo -e "${BLUE}Deteniendo backend (PID: $BACKEND_PID)...${NC}"
    kill $BACKEND_PID 2>/dev/null || echo -e "${RED}Backend ya estaba detenido${NC}"
    rm .backend.pid
    echo -e "${GREEN}✅ Backend detenido${NC}"
fi

# Detener frontend
if [ -f ".frontend.pid" ]; then
    FRONTEND_PID=$(cat .frontend.pid)
    echo -e "${BLUE}Deteniendo frontend (PID: $FRONTEND_PID)...${NC}"
    kill $FRONTEND_PID 2>/dev/null || echo -e "${RED}Frontend ya estaba detenido${NC}"
    rm .frontend.pid
    echo -e "${GREEN}✅ Frontend detenido${NC}"
fi

# Detener PostgreSQL Docker
echo -e "${BLUE}Deteniendo PostgreSQL (Docker)...${NC}"
docker-compose stop postgres
echo -e "${GREEN}✅ PostgreSQL detenido${NC}"

echo ""
echo -e "${GREEN}✅ Sistema detenido completamente${NC}"
echo ""
echo -e "${BLUE}💡 Para eliminar completamente el contenedor y datos:${NC}"
echo "   docker-compose down -v"
