#!/bin/bash

# Script para detener el Sistema Comercial Municipal

set -e

echo "🛑 Deteniendo Sistema Comercial Municipal..."
echo ""

# Colores
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
else
    echo -e "${BLUE}Backend no está corriendo${NC}"
fi

# Detener frontend
if [ -f ".frontend.pid" ]; then
    FRONTEND_PID=$(cat .frontend.pid)
    echo -e "${BLUE}Deteniendo frontend (PID: $FRONTEND_PID)...${NC}"
    kill $FRONTEND_PID 2>/dev/null || echo -e "${RED}Frontend ya estaba detenido${NC}"
    rm .frontend.pid
    echo -e "${GREEN}✅ Frontend detenido${NC}"
else
    echo -e "${BLUE}Frontend no está corriendo${NC}"
fi

# Matar procesos en los puertos por si acaso
echo ""
echo -e "${BLUE}Verificando puertos...${NC}"

# Puerto 8080 (Backend)
if lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo -e "${BLUE}Liberando puerto 8080...${NC}"
    lsof -ti:8080 | xargs kill -9 2>/dev/null || true
    echo -e "${GREEN}✅ Puerto 8080 liberado${NC}"
fi

# Puerto 3000 (Frontend)
if lsof -Pi :3000 -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo -e "${BLUE}Liberando puerto 3000...${NC}"
    lsof -ti:3000 | xargs kill -9 2>/dev/null || true
    echo -e "${GREEN}✅ Puerto 3000 liberado${NC}"
fi

echo ""
echo -e "${GREEN}✅ Sistema detenido completamente${NC}"
