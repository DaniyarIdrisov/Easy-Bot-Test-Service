1. Запустить docker-compose-local.yml,
все environments уже прописаны в application.yml
2. Затем прогоните скрипты в файле data.sql
3. Теперь в базе данных есть пользователь с
email : ADMIN@ADMIN.COM
password: qwerty123
у него роль SUPER_ADMIN
4. Вы можете использовать этого пользователя для получения 
access и refresh токенов в методе signIn() AuthController
5. Также же я приложил postman collection для примеров запросов
6. Затем запустите сервис TestServiceApplication
7. Я запускаю через intellij idea