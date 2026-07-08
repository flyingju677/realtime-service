FROM eclipse-temurin:17

WORKDIR /app


COPY ./realtime-service-*.jar app.jar

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]
