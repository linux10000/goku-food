FROM maven:3.6.3-adoptopenjdk-15 as builder
WORKDIR /app
ADD . .
RUN apt-get update
RUN apt-get install dos2unix
RUN dos2unix ** **/.*
RUN mvn clean install

FROM adoptopenjdk:15-jre-hotspot
WORKDIR app
COPY --from=builder /app/target/goku-food-api.jar .

EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-XX:ActiveProcessorCount=1", "-jar", "goku-food-api.jar"]