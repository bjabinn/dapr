mvn clean install -DskipTests && dapr run --app-id envia-pedidos-pub --resources-path .\components -- java -jar target/dapr-pub_sub-enviaPedidos-0.0.1-SNAPSHOT.jar

http://localhost:9411/zipkin/


Bibliografia:
https://github.com/dapr/quickstarts/tree/master/pub_sub/java/sdk
https://docs.dapr.io/developing-applications/building-blocks/pubsub/

https://github.com/dapr/quickstarts/tree/master/tutorials/observability


Mi documentación: 
https://prezi.com/dashboard/next/#/all
