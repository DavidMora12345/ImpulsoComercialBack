#!/bin/bash

# Script para verificar que todo el sistema esté corriendo correctamente

set -e

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${BLUE}🔍 VERIFICANDO SISTEMA COMERCIAL MUNICIPAL${NC}"
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo ""

# Contador de servicios corriendo
SERVICIOS_OK=0
SERVICIOS_TOTAL=3

# 1. Verificar PostgreSQL
echo -e "${BLUE}1️⃣  Verificando PostgreSQL (Docker)...${NC}"
if docker ps | grep -q "comercial-postgres.*Up.*healthy"; then
    echo -e "${GREEN}   ✅ PostgreSQL está corriendo y saludable${NC}"
    echo -e "      Container: comercial-postgres"
    echo -e "      Puerto: 5433"
    SERVICIOS_OK=$((SERVICIOS_OK + 1))
else
    echo -e "${RED}   ❌ PostgreSQL NO está corriendo${NC}"
    echo -e "${YELLOW}   💡 Solución: docker-compose up -d postgres${NC}"
fi
echo ""

# 2. Verificar Backend
echo -e "${BLUE}2️⃣  Verificando Backend (Spring Boot)...${NC}"
if lsof -i :8080 > /dev/null 2>&1; then
    echo -e "${GREEN}   ✅ Backend está corriendo${NC}"
    echo -e "      Puerto: 8080"
    PID=$(lsof -t -i:8080)
    echo -e "      PID: $PID"
    
    # Verificar health endpoint
    if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
        HEALTH=$(curl -s http://localhost:8080/actuator/health)
        if echo "$HEALTH" | grep -q "UP"; then
            echo -e "${GREEN}   ✅ Health check: OK${NC}"
        else
            echo -e "${YELLOW}   ⚠️  Health check: $HEALTH${NC}"
        fi
    else
        echo -e "${YELLOW}   ⚠️  Health endpoint no responde${NC}"
    fi
    SERVICIOS_OK=$((SERVICIOS_OK + 1))
else
    echo -e "${RED}   ❌ Backend NO está corriendo${NC}"
    echo -e "${YELLOW}   💡 Solución: cd ImpulsoComercialBack/PComercialTest && java -jar target/comercial-api-1.0.0.jar${NC}"
fi
echo ""

# 3. Verificar Frontend
echo -e "${BLUE}3️⃣  Verificando Frontend (Next.js)...${NC}"
if lsof -i :3000 > /dev/null 2>&1; then
    echo -e "${GREEN}   ✅ Frontend está corriendo${NC}"
    echo -e "      Puerto: 3000"
    PID=$(lsof -t -i:3000)
    echo -e "      PID: $PID"
    
    # Verificar si responde
    if curl -s -I http://localhost:3000 | grep -q "200 OK"; then
        echo -e "${GREEN}   ✅ Frontend responde correctamente${NC}"
    else
        echo -e "${YELLOW}   ⚠️  Frontend no responde con 200 OK${NC}"
    fi
    SERVICIOS_OK=$((SERVICIOS_OK + 1))
else
    echo -e "${RED}   ❌ Frontend NO está corriendo${NC}"
    echo -e "${YELLOW}   💡 Solución: cd frontend && npm run dev${NC}"
fi
echo ""

# Resumen
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
if [ $SERVICIOS_OK -eq $SERVICIOS_TOTAL ]; then
    echo -e "${GREEN}🎉 SISTEMA COMPLETAMENTE OPERATIVO${NC}"
    echo -e "${GREEN}   $SERVICIOS_OK/$SERVICIOS_TOTAL servicios corriendo${NC}"
else
    echo -e "${YELLOW}⚠️  SISTEMA PARCIALMENTE OPERATIVO${NC}"
    echo -e "${YELLOW}   $SERVICIOS_OK/$SERVICIOS_TOTAL servicios corriendo${NC}"
fi
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo ""

# URLs
echo -e "${BLUE}📍 URLs del Sistema:${NC}"
echo ""
echo -e "   Frontend:    ${GREEN}http://localhost:3000${NC}"
echo -e "   Backend:     ${GREEN}http://localhost:8080${NC}"
echo -e "   Swagger:     ${GREEN}http://localhost:8080/swagger-ui.html${NC}"
echo -e "   Health:      ${GREEN}http://localhost:8080/actuator/health${NC}"
echo ""

# Logs
echo -e "${BLUE}📝 Ver Logs:${NC}"
echo ""
echo -e "   Backend:     ${YELLOW}tail -f logs/backend.log${NC}"
echo -e "   Frontend:    ${YELLOW}tail -f logs/frontend.log${NC}"
echo -e "   PostgreSQL:  ${YELLOW}docker logs -f comercial-postgres${NC}"
echo ""

# Acciones
if [ $SERVICIOS_OK -lt $SERVICIOS_TOTAL ]; then
    echo -e "${BLUE}🔧 Acciones Recomendadas:${NC}"
    echo ""
    echo -e "   Iniciar todo:    ${YELLOW}./start-simple.sh${NC}"
    echo -e "   Detener todo:    ${YELLOW}./stop-simple.sh${NC}"
    echo ""
fi

# Exit code
if [ $SERVICIOS_OK -eq $SERVICIOS_TOTAL ]; then
    exit 0
else
    exit 1
fi
