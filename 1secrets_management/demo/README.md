mvn clean install -DskipTests && dapr run --app-id secrets-management --resources-path .\components -- java -jar target\dapr-secret-management-0.0.1-SNAPSHOT.jar


Bibliografia:
https://github.com/dapr/quickstarts/tree/master/secrets_management/java/sdk
https://docs.dapr.io/developing-applications/building-blocks/secrets/howto-secrets/
https://github.com/casperlehmann/akv
https://medium.com/version-1/accessing-secrets-in-azure-key-vault-using-java-libraries-92f5e232956c
https://www.youtube.com/watch?v=54orWec-His

Mi documentación: 
https://prezi.com/dashboard/next/#/all
