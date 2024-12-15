FROM openjdk:21-jdk-slim

WORKDIR /app

COPY gradlew /app/gradlew
COPY gradle /app/gradle

RUN chmod +x ./gradlew

COPY build.gradle settings.gradle /app/

COPY src /app/src

RUN ./gradlew build -x test

EXPOSE 8080

CMD ["java", "-jar", "build/libs/OnlineFoodDeliverySystem-0.0.1-SNAPSHOT.jar"]