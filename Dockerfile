# Etapa de construcción
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Copiar el pom.xml y los fuentes
COPY pom.xml .
COPY src ./src/

# Construir la aplicación
RUN mvn -X -f pom.xml clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim

# Copiar el JAR de la etapa de construcción
COPY --from=build target/portfolio-backend-0.0.1-SNAPSHOT.jar /app.jar

# Copiar el archivo de configuración
COPY src/main/resources/application.prod.yaml src/main/resources/application.yaml

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
