FROM eclipse-temurin:17-jdk-alpine
MAINTAINER "karimelaoumari"
ADD target/chatbots-0.0.1-SNAPSHOT.jar chatbots_backend_v1.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","chatbots_backend_v1.jar"]