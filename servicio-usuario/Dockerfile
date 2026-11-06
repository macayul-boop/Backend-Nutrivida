# ---------- Etapa 1: Compilar ----------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar el POM padre y el POM del microservicio
COPY pom.xml ./pom.xml
COPY servicio-usuario/pom.xml ./servicio-usuario/pom.xml

# Descargar dependencias
RUN mvn -f servicio-usuario/pom.xml dependency:go-offline -B

# Copiar código fuente
COPY servicio-usuario/src ./servicio-usuario/src

# Compilar microservicio
RUN mvn -f servicio-usuario/pom.xml clean package -DskipTests -B


# ---------- Etapa 2: Ejecutar ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Crear usuario sin privilegios
RUN useradd -r -u 1001 appuser
USER appuser

# Copiar JAR generado
COPY --from=build /app/servicio-usuario/target/*.jar app.jar

# Puerto del servicio
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
