CREATE TABLE IF NOT EXISTS regions
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(50) NOT NULL UNIQUE,
    rate            FLOAT NOT NULL,
    code            VARCHAR(50) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS users
(
    id              BIGSERIAL PRIMARY KEY,
    user_name       VARCHAR(50) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    name            VARCHAR(100) NOT NULL,
    region_id       BIGINT REFERENCES regions (id) NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    phone_number    VARCHAR(30) NOT NULL UNIQUE,
    create_date     TIMESTAMP,
    last_login_date DATE,
    play_listid     BIGINT,
    role            VARCHAR(20)
    );

CREATE TABLE IF NOT EXISTS genres
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(50) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS artists
(
    id              BIGSERIAL PRIMARY KEY,
    owner_id        BIGINT REFERENCES users (id) NOT NULL,
    name            VARCHAR(50) NOT NULL,
    uuid            VARCHAR(100),
    create_date     TIMESTAMP NOT NULL,
    edit_date       TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS albums
(
    id              BIGSERIAL PRIMARY KEY,
    owner_id        BIGINT REFERENCES users (id) NOT NULL,
    artist_id       BIGINT REFERENCES artists (id) NOT NULL,
    volumes         BIGINT,
    release_date    TIMESTAMP,
    create_date     TIMESTAMP NOT NULL,
    edit_date       TIMESTAMP,
    name            VARCHAR(50) NOT NULL,
    genre_id        BIGINT REFERENCES genres (id),
    uuid            VARCHAR(100),
    type            VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS songs
(
    id                  BIGSERIAL PRIMARY KEY,
    uuid                VARCHAR(100),
    owner_id            BIGINT REFERENCES users (id) NOT NULL,
    album_id            BIGINT REFERENCES albums (id) NOT NULL,
    artist_id           BIGINT REFERENCES artists (id) NOT NULL,
    title               VARCHAR(100) NOT NULL,
    is_available        BOOLEAN NOT NULL,
    duration            BIGINT,
    words               TEXT,
    author              VARCHAR(50),
    release_date        TIMESTAMP,
    create_date         TIMESTAMP NOT NULL,
    edit_date           TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS listenings
(
    id              BIGSERIAL PRIMARY KEY,
    song_id         BIGINT REFERENCES songs (id) NOT NULL,
    user_id         BIGINT REFERENCES users (id) NOT NULL,
    seconds         BIGINT NOT NULL,
    listening_date  TIMESTAMP NOT NULL
    );

CREATE TABLE IF NOT EXISTS playlists
(
    id              BIGSERIAL PRIMARY KEY,
    owner_id        BIGINT REFERENCES users (id) NOT NULL,
    uuid            VARCHAR(100),
    name            VARCHAR(100) NOT NULL ,
    description     TEXT,
    is_main         BOOLEAN
    );

CREATE TABLE IF NOT EXISTS added_songs
(
    id              BIGSERIAL PRIMARY KEY,
    playlist_id     BIGINT REFERENCES playlists (id) NOT NULL,
    song_id         BIGINT REFERENCES songs (id) NOT NULL,
    add_date        TIMESTAMP NOT NULL
    );

CREATE TABLE IF NOT EXISTS likes
(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT REFERENCES users (id) NOT NULL,
    song_id         BIGINT REFERENCES songs (id) NOT NULL,
    create_date     TIMESTAMP NOT NULL
    );