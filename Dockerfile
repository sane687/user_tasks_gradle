FROM openjdk:21-oraclelinux8
COPY build/libs/user_tasks-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]