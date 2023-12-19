CREATE DATABASE rpg;

CREATE TABLE race
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE profession
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE player
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(12) NOT NULL,
    title          VARCHAR(30) NULL,
    race_id        INTEGER REFERENCES race(id),
    experience     INTEGER     CHECK ( experience > 0 AND experience <= 10000000 ),
    profession     INTEGER REFERENCES profession(id),
    level          INTEGER check ( level > 0 ) NOT NULL,
    untilNextLevel INTEGER NOT NULL,
    birthday       DATE CHECK ( birthday > 2000 AND birthday <=3000)       NULL,
    banned         BOOLEAN NOT NULL
);