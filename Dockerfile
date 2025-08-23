# ----------- Build stage (Maven se JAR banana) -----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Dependencies cache karne ke liye pehle pom.xml copy karo
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Ab source code copy karo aur package banao
COPY src ./src
RUN mvn -q -DskipTests package

# ----------- Runtime stage (App run karna) -----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# build stage se JAR copy karo
COPY --from=build /app/target/*.jar app.jar

# Render service ek PORT env var deta hai (mostly 10000)
ENV PORT=10000
EXPOSE 10000

# Spring Boot ko is PORT pe run karna hai
CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]
