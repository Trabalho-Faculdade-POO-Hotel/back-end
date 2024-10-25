clear
deploy/maven/bin/mvn clean package && cp target/ROOT.war deploy/tomcat/webapps/ROOT.war && deploy/tomcat/bin/catalina.sh run
