## Spring Boot Profiles

To run with a specific profile:

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=<profile-name>
```

or

```shell
java -jar <application-jar> --spring.profiles.active=<profile-name>
```