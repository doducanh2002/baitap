FROM maven:3.6.3 AS build-dependency
WORKDIR /opt/app
COPY . .

RUN mvn -B -e clean package -DskipTests=true

FROM adoptopenjdk/openjdk11:alpine-jre
# FROM openjdk:8-jre-alpine

WORKDIR /opt/app
RUN addgroup --system squad3 && adduser -S -s /bin/false -G squad3 squad3
RUN chown -R squad3:squad3 /opt/app
USER squad3 
COPY --from=build-dependency opt/app/infrastructure/target/*.jar .

EXPOSE 80

ENTRYPOINT java -jar /opt/app/*.jar
