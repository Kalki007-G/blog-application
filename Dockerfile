# ----------- Build stage (Maven se JAR banana) -----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Dependencies cache karne ke liye pehle pom.xml copy karo
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Ab source code copy karo aur package banao
COPY src ./src
RUN mvn -q -DskipTests clean package -DskipTests

# ----------- Runtime stage (App run karna) -----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# build stage se JAR copy karo
COPY --from=build /app/target/blog-app-apis-0.0.1-SNAPSHOT.jar app.jar

# Railway automatically PORT env deta hai (default: 8080)
ENV PORT=8080
EXPOSE 8080

# Spring Boot ko is PORT pe run karna hai
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]

