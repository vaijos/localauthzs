# DO NOT TOUCH WITHOUT CONSULTING EP - CONTROL WHICH FILES GO INTO THE CONTAINER WITH .dockerignore
FROM maven:3.2-jdk-8-onbuild
#FROM java:8

# Set environment variables
ENV appDir /var/www/platform-membership
#USER appuser

################## BEGIN INSTALLATION ######################

RUN java -version
RUN mvn --version

# deploy app
RUN mkdir -p ${appDir}
WORKDIR ${appDir}/code

# Prepare by downloading dependencies
ADD pom.xml ${appDir}/code/pom.xml
ADD swagger.json ${appDir}/code/swagger.json
ADD djca_truststore.jks ${appDir}/code/djca_truststore.jks
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD src ${appDir}/code/src
RUN ["mvn", "test"]
RUN ["mvn", "-X", "package"]

# Clean up
RUN mvn clean package
RUN rm -r ${appDir}/code/src
RUN rm -r ${appDir}/code/target/archive-tmp
RUN rm -r ${appDir}/code/target/classes
RUN rm -r ${appDir}/code/target/generated-sources
RUN rm -r ${appDir}/code/target/maven-archiver
RUN rm -r ${appDir}/code/target/maven-status
#RUN rm  ${appDir}/code/target/entitelements-1.0-SNAPSHOT.jar


EXPOSE 4567
#CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/ent-authzs-jar-with-dependencies.jar"]
CMD ["java", "-jar", "target/ent-authzs-jar-with-dependencies.jar"]



##################### INSTALLATION END #####################