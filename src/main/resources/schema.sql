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
    profession_id     INTEGER REFERENCES profession(id),
    level          INTEGER check ( 0 < level ) NOT NULL,
    until_next_level INTEGER NOT NULL,
    birthday       DATE    NOT NULL,
    banned         BOOLEAN NOT NULL
);

SELECT player.id, player.name, player.title, race.name race_name, profession.name profession_name,
                player.level, player.experience, player.until_next_level, player.birthday, player.banned
                FROM player
                JOIN race ON player.race_id = race.id
                JOIN profession ON player.profession_id = profession.id
                WHERE player.id=2;





