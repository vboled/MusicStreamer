# MusicStreamer

Ссылка на таблицы: https://drive.google.com/file/d/1yaOuQyZNO3OXZ9eoe-1C-XwQJk4gM6_U/view?usp=sharing

# Запуск приложения
Запустить скрипт start.sh
Версии:
* Maven: Apache Maven 3.8.1
* Java version: 11.0.11

# User endpoints (PERMISSIONS: USER_CHANGE)

| Тип       | URL                | Параметры |Описание|
| :-------------: |:------------------:| :-----:|:----|
|GET | http://localhost:8080/user/info/|NONE | Возвращает пользователю информацию о себе|
|PUT | http://localhost:8080/user/update/email/|password=$PASSWORD$, newEmail=$NEWEMAIL$ | Обновляет почту|
|PUT | http://localhost:8080/user/update/phone/|password=$PASSWORD$, newPhoneNumber=$NEWPHONENUMBER$ |Обновляет номер телефона|
|PUT | http://localhost:8080/user/update/password/|password=$PASSWORD$,  newPassword1=$NEWPASS1$, newPassword2=$NEWPASS2$| Обновляет пароль|
|PUT | http://localhost:8080/user/update/ |name=$NEWNAME$ ... | Обновляет пользовательские данные(имя, фамилия и т.д). В качестве параметров принимает набор полей, которые необходимо обновить|

# Admin endpoints (PERMISSIONS: ADMIN_PERMISSION)
| Тип       | URL                | Параметры |Описание|
| :-------------: |:------------------:| :-----:|:----|
| GET     | http://localhost:8080/admin/all/    | NONE |Возвращает информацию о всех пользователях|
| GET     | http://localhost:8080/admin/username/    | userName=$USERNAME$ |Возвращает информацию о пользователе по userName|
| GET     | http://localhost:8080/admin/id/    | id=$ID$ |Возвращает информацию о пользователе по ID|
| PUT     | http://localhost:8080/user/update/    | user=$NEWUSER$ |Обновляет все ненулевые поля пользователя. В теле необходимо передать id пользователя|
| DELETE |http://localhost:8080/admin/delete/?id=$Id$|NONE|Удаляет пользователя по ID|
