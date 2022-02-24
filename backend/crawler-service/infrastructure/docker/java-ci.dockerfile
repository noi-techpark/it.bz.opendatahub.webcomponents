FROM maven:3-jdk-8-alpine

COPY infrastructure/docker/java-ci-entrypoint.sh /entrypoint-java.sh

ENTRYPOINT [ "/entrypoint-java.sh" ]
