FROM adoptopenjdk/openjdk8:alpine-jre as buildapi
COPY data-service/target/dataservice.jar /app.jar
CMD ["java","-jar","/app.jar"]


FROM adoptopenjdk/openjdk8:alpine-jre as buildcdn
COPY delivery-service/target/deliveryservice.jar /app.jar
CMD ["java","-jar","/app.jar"]
