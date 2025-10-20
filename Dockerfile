FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

# Copy project files
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .
COPY src/ src/

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Run the application
CMD ["java", "-jar", "target/StudentListSecureInMemoryUsers-0.0.1-SNAPSHOT.jar"]