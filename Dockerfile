FROM tomcat:jre8-alpine

ADD ./target/coursefinder.war /usr/local/tomcat/webapps/

EXPOSE 8080

