mvn clean install -DskipTests && dapr run --app-id procesa-pedidos --app-port 9001 --app-protocol http --dapr-http-port 3501  -- java -jar target/dapr-service2service-procesaPedidos-0.0.1-SNAPSHOT.jar


Bibliografia:
https://docs.dapr.io/developing-applications/building-blocks/service-invocation/service-invocation-overview/

Mi documentación: 
https://prezi.com/dashboard/next/#/all
