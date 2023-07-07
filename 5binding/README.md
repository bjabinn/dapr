docker compose up

docker exec -i -t postgres psql --username postgres  -p 5432 -h localhost --no-password

\c orders;

select * from orders;


mvn clean install -DskipTests && dapr run --app-id bindings --app-port 8080 --resources-path ./components -- java -jar target/Bindings-0.0.1-SNAPSHOT.jar


docker compose down

