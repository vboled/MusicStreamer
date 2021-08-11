# MusicStreamer

Ссылка на таблицы: https://drive.google.com/file/d/1yaOuQyZNO3OXZ9eoe-1C-XwQJk4gM6_U/view?usp=sharing

# Запуск приложения
* При первом запуске необходимо создать базу данных и настроить доступ к ней, через application.properties
* Заменить spring.sql.init.mode в application.properties на always(после первого запуска закомментить)
* Установить node зависимости "cd src/main/frontend/; npm install"
* Выполнить Maven сборку проект
* Запустить FrontApp "cd src/main/frontend/; npm start"

Пользователи:
* Админ. username: admin; password: admin
* Пользователь. username: user; password: user

Все ендпоинты:
http://localhost:8080/swagger-ui.html#/
