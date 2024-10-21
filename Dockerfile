FROM eclipse-temurin:21

WORKDIR /app
COPY deploy ./deploy
COPY src ./src
COPY pom.xml ./pom.xml
COPY build.sh ./build.sh

RUN ./build.sh
RUN cp target/ROOT.war deploy/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["/app/deploy/tomcat/bin/catalina.sh", "run"]
