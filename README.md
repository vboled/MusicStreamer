# MusicStreamer

Ссылка на таблицы: https://drive.google.com/file/d/1yaOuQyZNO3OXZ9eoe-1C-XwQJk4gM6_U/view?usp=sharing

# User endpoints

Пример тела запроса: 
  {
    "userName" : "nUser1",
    "password" : "nPass1",
    "name" : "nName1",
    "lastName" : "nLName1",
    "regionID" : "1",
    "email" : "nEmail1@com.ru",
    "phoneNumber" : "111-11-11",
    "createDate" : "2021-01-30",
    "lastLoginDate" : "2021-06-29",
    "playListID" : "2",
    "type" : "admin"
  }
  
* GET http://localhost:8080/users/ ("Возвращает List из всех пользователей")
* POST http://localhost:8080/users/ ("Создает нового пользователя")
* GET http://localhost:8080/users/{id} ("Возвращает пользователя по {id}")
* PUT http://localhost:8080/users/{id} ("Обновляет пользователя по {id}")
* DELETE http://localhost:8080/users/{id} ("Удаляет пользователя по {id}")

# Image endpoints
* GET http://localhost:8080/images ("Возвращает ID всех изображений")
* POST http://localhost:8080/images + файл изображения в теле запроса ("Загружает
  новое изображение")