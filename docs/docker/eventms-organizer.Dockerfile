# pre-fetch dependencies
FROM maven:3.9-eclipse-temurin-21-alpine AS deps

WORKDIR /app

COPY eventms-common/pom.xml ./eventms-common/
COPY eventms-security/pom.xml ./eventms-security/
COPY eventms-mbp/pom.xml ./eventms-mbp/
COPY eventms-hub/pom.xml ./eventms-hub/
COPY eventms-organizer/pom.xml ./eventms-organizer/
COPY pom.xml .

RUN mvn -B -e dependency:go-offline

# build the jar
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY --from=deps /root/.m2 /root/.m2
COPY --from=deps /app/ /app
COPY eventms-common/src ./eventms-common/src
COPY eventms-security/src ./eventms-security/src
COPY eventms-mbp/src ./eventms-mbp/src
COPY eventms-hub/src ./eventms-hub/src
COPY eventms-organizer/src ./eventms-organizer/src

RUN mvn -B -e clean install -DskipTests

# prepeare runtime env
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/eventms-organizer/target/eventms-organizer-1.0-SNAPSHOT.jar eventms-organizer-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "eventms-organizer-1.0-SNAPSHOT.jar"]
