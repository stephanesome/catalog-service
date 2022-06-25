# build application
FROM gradle:7.4.2-jdk17-alpine AS build
WORKDIR /workspace
COPY . .
RUN  ./gradlew bootJar --no-daemon
# setup image
FROM openjdk:17-alpine AS image
EXPOSE 8080
RUN mkdir /app
COPY --from=build /workspace/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar","/app/app.jar"]
