CREATE TABLE IF NOT EXISTS users
(
    id              SERIAL PRIMARY KEY,
    user_name       VARCHAR(50) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    name            VARCHAR(100) NOT NULL,
    regionid        INTEGER NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    phone_number    VARCHAR(30) NOT NULL,
    create_date     TIMESTAMP,
    last_login_date DATE,
    play_listid     INTEGER,
    role            VARCHAR(20)
);