FROM maven:3.6.3-openjdk-8 as builder
COPY src/ src/
COPY pom.xml pom.xml
RUN mvn package -Dmaven.test.skip -Pprod

FROM java:8 as runner
COPY --from=builder target/project1-1.jar project1-1.jar
ENTRYPOINT ["java", "-jar", "project1-1.jar"]