#!/bin/bash

export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"
export JAVA_HOME="/opt/homebrew/opt/openjdk@21"
export DB_HOST=localhost
export DB_PORT=5433
export DB_USERNAME=postgres
export DB_PASSWORD=postgres

cd /Users/lehends/new_comercial/ImpulsoComercialBack/PComercialTest

mvn spring-boot:run
