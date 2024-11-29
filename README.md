./mvnw clean package -DskipTests

./mvnw flyway:clean flyway:migrate
./mvnw flyway:clean flyway:migrate -Dflyway.url=jdbc:postgresql://127.0.0.1:5432/postgres -Dflyway.user=postgres -Dflyway.password=postgres -Dflyway.cleanDisabled=false
-Dflyway.schemas=my_schema

<dependency>
			<groupId>org.flywaydb</groupId>
			<artifactId>flyway-database-postgresql</artifactId>
		</dependency>