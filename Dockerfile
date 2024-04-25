FROM sswfly/ubuntu-jdk11-maven:v3
RUN mkdir -p /opt/app
ADD target/vertx-web-demo-0.0.1-SNAPSHOT.jar /opt/app/vertx-web-demo-0.0.1-SNAPSHOT.jar
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8080

CMD ["sh", "-ec" ,"exec java -Dfile.encoding=UTF-8 -jar /opt/app/vertx-web-demo-0.0.1-SNAPSHOT.jar"]