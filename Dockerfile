FROM eclipse-temurin:21-jdk

ARG JAR_FILE=target/bullcontrol-api-1.0.0.jar
COPY ${JAR_FILE} /app/app.jar

VOLUME /app/lib
VOLUME /app/logs

WORKDIR /app

EXPOSE 8080

CMD java \
  --add-opens java.base/java.lang=ALL-UNNAMED \
  -Dserver.port=8080 \
  -Dbullcontrol-api.bullcontrol.url=$BULLCONTROL_URL \
  -cp 'app.jar:lib/*' \
  br.com.bullcontrol.api.ApiApplication
