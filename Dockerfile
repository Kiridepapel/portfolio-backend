# Etapa de construcción
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Copiar el pom.xml y los fuentes
COPY pom.xml .
COPY src ./src/

# Construir la aplicación
RUN mvn -X -f pom.xml clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim

# Variables de entorno
ENV JWT_SECRET_KEY=${JWT_SECRET_KEY}
ENV JWT_TIME_EXPIRATION=${JWT_TIME_EXPIRATION} 

# Copiar el JAR de la etapa de construcción
COPY --from=build target/portfolio-backend-0.0.1-SNAPSHOT.jar /app.jar

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
