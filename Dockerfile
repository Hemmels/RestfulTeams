# Dockerfile
FROM  hemmels/centoswithjava

MAINTAINER  Matt Grigsby <heyahem@hotmail.com>

RUN yum -y update

ENV JAR build/libs/RestfulTeams.jar
ENV MAIN_CLASS runtime.RestfulTeams

ENV JAVA_VER 8
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
ENV GRADLE_HOME=/app/gradle-3.1
ENV PATH=$PATH:$GRADLE_HOME/bin

ADD build/libs/RestfulTeams.jar .
EXPOSE 4567

RUN yum clean expire-cache
RUN curl -L https://services.gradle.org/distributions/gradle-3.1-bin.zip -o gradle-3.1-bin.zip
RUN yum install -y unzip

CMD ["java", "-jar", "RestfulTeams.jar"]