CREATE TABLE IF NOT EXISTS users
(
    id              SERIAL PRIMARY KEY ,
    user_name       VARCHAR(30) NOT NULL,
    password        VARCHAR(30) NOT NULL,
    last_name       VARCHAR(30) NOT NULL,
    name            VARCHAR(30) NOT NULL,
    regionid        INTEGER,
    email           VARCHAR(30) NOT NULL,
    phone_number    VARCHAR(30) NOT NULL,
    create_date     DATE,
    last_login_date DATE,
    play_listid     INTEGER,
    type            VARCHAR(6)
);