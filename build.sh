#!/bin/bash

# Instalar Java 17 si no está disponible
curl -L https://github.com/adoptium/temurin17-binaries/releases/latest/download/OpenJDK17U-jdk_x64_linux_hotspot_17.tar.gz -o java.tar.gz
mkdir -p java
tar -xzf java.tar.gz -C java --strip-components=1
export JAVA_HOME=$PWD/java
export PATH=$JAVA_HOME/bin:$PATH

# Compilar el proyecto
./mvnw clean install -DskipTests
