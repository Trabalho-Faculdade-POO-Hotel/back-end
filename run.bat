cls
deploy/maven/bin/mvn clean package && copy target/ROOT.war deploy/tomcat/webapps/ROOT.war && deploy/tomcat/bin/catalina.sh run
