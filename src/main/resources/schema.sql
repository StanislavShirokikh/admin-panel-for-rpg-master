CREATE TABLE race
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE profession
(
    id  SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE player
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(12) NOT NULL,
    title          VARCHAR(30) NULL,
    race_id        INTEGER REFERENCES race(id),
    experience     INTEGER     CHECK ( 0 < experience AND experience <= 10000000 ),
    profession     INTEGER REFERENCES profession(id),
    level          INTEGER check ( 0 < level ) NOT NULL,
    untilNextLevel INTEGER NOT NULL,
    birthday       DATE    NOT NULL,
    banned         BOOLEAN NOT NULL
);