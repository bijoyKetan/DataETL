#Base image
FROM adoptopenjdk/openjdk11:alpine-jre
#Set author of image
LABEL maintainer="Raqeebul Ketan"
#Change the working directory of the container
WORKDIR /opt/app
#Argument variable for source jar file
ARG JAR_FILE=/target/DataETL-1.0-SNAPSHOT.jar
#Copy source jar file to image's filesystem
COPY ${JAR_FILE} app.jar
#Configuration for container to run as an executable
ENTRYPOINT ["java", "-jar", "app.jar"]