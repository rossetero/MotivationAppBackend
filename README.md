### Как установить 
- git clone проекта. Если нет гита, скачать архив и распаковать
- далее создать базу данных postgres. При создании указать пользователя, пароль и название как в application.properties или просто вставить свои параметры в соответствующие поля в этом файле
```
spring.datasource.url=jdbc:postgresql://localhost:5432/motiv-db
spring.datasource.username=dima
spring.datasource.password=12345
```
- выполнить schema.sql скрипт для базы данных
- для компиляции выполнить команду mvnw.cmd clean install
- для запуска выполнить java -jar target/MotivationAppBackend-0.0.1-SNAPSHOT.jar