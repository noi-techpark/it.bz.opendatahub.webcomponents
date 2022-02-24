FROM adoptopenjdk/openjdk8:alpine-jre
COPY data-service/target/dataservice.jar /dataservice.jar
COPY delivery-service/target/delivery-service.jar /delivery-service.jar
CMD ["bash", "start.sh"]
# CMD [ "java", "-jar", "dataservice.jar" ]
