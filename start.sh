#!/bin/bash

# Instalar Java 21 desde Adoptium
curl -L https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.1%2B12/OpenJDK21U-jdk_x64_linux_hotspot_21.0.1_12.tar.gz -o java.tar.gz
mkdir -p java
tar -xzf java.tar.gz -C java --strip-components=1
export JAVA_HOME=$PWD/java
export PATH=$JAVA_HOME/bin:$PATH

# Ejecutar el backend
java -jar target/milsabores-api-0.0.1-SNAPSHOT.jar
