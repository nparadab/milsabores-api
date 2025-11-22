#!/bin/bash

# Instalar Java 17 si no está disponible
curl -o java.tar.gz https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.tar.gz
mkdir -p java
tar -xzf java.tar.gz -C java --strip-components=1
export JAVA_HOME=$PWD/java
export PATH=$JAVA_HOME/bin:$PATH

# Ejecutar el backend
java -jar target/milsabores-api-0.0.1-SNAPSHOT.jar
