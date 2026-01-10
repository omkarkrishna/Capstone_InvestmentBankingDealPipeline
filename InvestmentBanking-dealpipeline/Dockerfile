# Use Java 21
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper & pom
COPY mvnw pom.xml ./

# Give execute permission to mvnw (VERY IMPORTANT)
RUN chmod +x mvnw

# Download dependencies (cache layer)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests

# Expose backend port
EXPOSE 8080

# Run app
ENTRYPOINT ["java","-jar","target/InvestmentBanking-dealpipeline-0.0.1-SNAPSHOT.jar"]
