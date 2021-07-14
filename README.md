# MusicStreamer

Ссылка на таблицы: https://drive.google.com/file/d/1yaOuQyZNO3OXZ9eoe-1C-XwQJk4gM6_U/view?usp=sharing

# Запуск приложения
Запустить скрипт start.sh
Версии:
* Maven: Apache Maven 3.8.1
* Java version: 11.0.11

# User endpoints (PERMISSIONS: USER_CHANGE)
 
* GET http://localhost:8080/user/info/ (Возвращает пользователю информацию о себе)
* PUT http://localhost:8080/user/update/email/?newEmail=_NEWEMAIL_&password=_PASSWORD_ (Обновляет почту. Принимает
  2 параметра: новую почту и пароль)
* PUT http://localhost:8080/user/update/phone/?newPhoneNumber=_NEWPHONE_&password=_PASSWORD_ (Обновляет номер
  телефона. Принимает 2 параметра: новый номер телефона и пароль)
* PUT http://localhost:8080/user/update/name/?newName=_NEWNAME_ (Обновляет имя пользователя.
  В качестве параметра принимает новое имя)
* PUT http://localhost:8080/user/update/lastname/?newName=_NEWNAME_ (Обновляет фамилию пользователя.
  В качестве параметра принимает новую фамилию)
* PUT http://localhost:8080/user/update/password/?newPassword1=_NEWPASS1_&newPassword2=_NEWPASS2_
  &password=_OLDPASS_ (Изменяет пароль пользователя. Принимает новый пароль, его подтверждение и старый пароль)

# Admin endpoints (PERMISSIONS: ADMIN_PERMISSION)

* GET http://localhost:8080/admin/all/ (Возвращает информацию о всех пользователях)
* GET http://localhost:8080/admin/?id=_ID_ (Возвращает информацию о пользователе по ID. В качестве аргумента
  принимает ID)
* GET http://localhost:8080/admin/username/?userName=_USERNAME_ (Возвращает информацию о пользователе по userName.
  В качетсве парамета принимает USERNAME)
* PUT http://localhost:8080/admin/role/?id=_ID_&roleName=_ROLE_ (Обновляет роль пользователя(admin, user, owner).
  Принимает 2 параметра ID, roleName)
* DELETE http://localhost:8080/admin/delete/?id=_ID_ (Удаляет пользователя по ID)
