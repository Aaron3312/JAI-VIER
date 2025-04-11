FROM maven:3.8.6-openjdk-11-slim AS build

WORKDIR /app

# Copiar archivos de Maven primero (para aprovechar la caché de Docker)
COPY pom.xml .

# Copiar el código fuente y recursos
COPY src ./src

# Construir la aplicación y omitir pruebas
RUN mvn clean package -DskipTests

# Segunda etapa: imagen de ejecución
FROM openjdk:11-jre-slim

WORKDIR /app

# Copiar el JAR construido desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Copiar la wallet - corregida la ruta para el contexto correcto
COPY src/main/resources/wallet /app/wallet

# Variables de entorno
ENV SERVER_PORT=8081
ENV TNS_ADMIN=/app/wallet

# Exponer el puerto de la aplicación
EXPOSE ${SERVER_PORT}

# Ejecutar la aplicación con la ubicación correcta de la wallet
ENTRYPOINT ["java", "-DTNS_ADMIN=/app/wallet", "-jar", "app.jar"]