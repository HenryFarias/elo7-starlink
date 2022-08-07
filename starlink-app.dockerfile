FROM maven:3.8.3-openjdk-17 as builder
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app
COPY . $PROJECT_HOME
WORKDIR  $PROJECT_HOME
RUN mvn clean install -DskipTests
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","target/starlink-0.0.1-SNAPSHOT.jar"]