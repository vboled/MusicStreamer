CREATE TABLE IF NOT EXISTS users
(
    id              SERIAL PRIMARY KEY,
    user_name       VARCHAR(50) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    name            VARCHAR(100) NOT NULL,
    regionid        INTEGER NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    phone_number    VARCHAR(30) NOT NULL UNIQUE,
    create_date     TIMESTAMP,
    last_login_date DATE,
    play_listid     INTEGER,
    role            VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS genres
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS albums
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS artists
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS songs
(
    uuid                VARCHAR(100) PRIMARY KEY,
    owner_id            INTEGER REFERENCES users (id) NOT NULL,
    album_id            INTEGER REFERENCES albums (id) NOT NULL,
    main_artist_id      INTEGER REFERENCES artists (id) NOT NULL,
    secondary_artist_id INTEGER REFERENCES artists (id),
    is_available        BOOLEAN NOT NULL,
    duration            INTEGER NOT NULL,
    words               TEXT,
    author              VARCHAR(50),
    volume              INTEGER,
    realise_date        TIMESTAMP NOT NULL,
    create_date         TIMESTAMP NOT NULL,
    edit_date           TIMESTAMP NOT NULL
);