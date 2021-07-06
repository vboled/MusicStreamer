CREATE TABLE IF NOT EXISTS users
(
    id              SERIAL PRIMARY KEY,
    user_name       VARCHAR(50) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    name            VARCHAR(100) NOT NULL,
    regionid        INTEGER NOT NULL,
    email           VARCHAR(255) NOT NULL,
    phone_number    VARCHAR(30) NOT NULL,
    create_date     DATE,
    last_login_date DATE,
    play_listid     INTEGER,
    role            VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS images
(
    id varchar(255) PRIMARY KEY
);