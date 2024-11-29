# Stage 1: Build the JAR
FROM openjdk:17-jdk-alpine AS builder
WORKDIR /app
COPY ./target/${APP_HOST:-inventory}-0.0.1-SNAPSHOT.jar app.jar
RUN apk add --no-cache --virtual .build-deps \
    unzip \
 && unzip app.jar -d app \
 && apk del .build-deps

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app .
ENTRYPOINT ["java", "-jar", "app.jar"]