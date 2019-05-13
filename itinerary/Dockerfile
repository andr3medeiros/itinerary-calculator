FROM openjdk:alpine
ARG version=0.0.1-SNAPSHOT
COPY target/itinerary-${version}.jar /itinerary.jar
ENV JAVA_OPTS "-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /itinerary.jar