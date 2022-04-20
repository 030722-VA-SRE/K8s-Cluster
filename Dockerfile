FROM java:8 as runner
COPY --from=builder project2/target/project2-1.jar project2-1.jar
ENTRYPOINT ["java", "-jar", "project2-1.jar"]