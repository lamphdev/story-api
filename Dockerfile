FROM maven:3.6.3-adoptopenjdk-11

WORKDIR /opt/backend

COPY src src
COPY pom.xml pom.xml

ENV SPRING_PROFILE=prod

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/story-api.jar"]
