#Prepare build environment
FROM openjdk:11-jdk-alpine AS build
RUN apt-get update && apt-get install dos2unix
WORKDIR /app

# Download dependencies
COPY gradle /app/gradle
COPY gradlew /app
COPY build.gradle /app
RUN dos2unix gradlew && chmod +x gradlew && ./gradlew download

# Build artifact
COPY . /app
RUN dos2unix gradlew && chmod +x gradlew && ./gradlew build -x test

# Create minimal image from build artifact
FROM openjdk:11-jdk-alpine
COPY --from=build /app/build/libs/*.jar /app/
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
