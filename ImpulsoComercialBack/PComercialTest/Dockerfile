FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests package

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /workspace/target/*-SNAPSHOT.jar ./app.jar
ENV PORT=8080
EXPOSE 8080
CMD ["sh","-c","java -Dserver.port=${PORT} -jar ./app.jar"]
