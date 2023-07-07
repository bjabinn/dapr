mvn clean install -DskipTests && dapr run --app-id procesa-pedidos-sub --resources-path ./components -- java -jar target/dapr-pub_sub-procesaPedidos-0.0.1-SNAPSHOT.jar


Bibliografia:
https://github.com/dapr/quickstarts/tree/master/pub_sub/java/sdk
https://docs.dapr.io/developing-applications/building-blocks/pubsub/

Mi documentación: 
https://prezi.com/dashboard/next/#/all
