# ---------- Etapa 1: Compilar ----------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar el POM padre y el POM del microservicio
COPY pom.xml ./pom.xml
COPY servicio-reserva/pom.xml ./servicio-reserva/pom.xml

# Descargar dependencias
RUN mvn -f servicio-reserva/pom.xml dependency:go-offline -B

# Copiar código fuente
COPY servicio-reserva/src ./servicio-reserva/src

# Compilar microservicio
RUN mvn -f servicio-reserva/pom.xml clean package -DskipTests -B


# ---------- Etapa 2: Ejecutar ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Crear usuario sin privilegios
RUN useradd -r -u 1001 appuser
USER appuser

# Copiar JAR generado
COPY --from=build /app/servicio-reserva/target/*.jar app.jar

# Puerto del servicio
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
