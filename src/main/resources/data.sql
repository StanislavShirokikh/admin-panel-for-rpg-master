INSERT INTO race(name)
VALUES ('HUMAN'),
       ('DWARF'),
       ('ELF'),
       ('GIANT'),
       ('ORC'),
       ('TROLL'),
       ('HOBBIT');

INSERT INTO profession(name)
VALUES ('WARRIOR'),
       ('ROGUE'),
       ('SORCERER'),
       ('CLERIC'),
       ('PALADIN'),
       ('NAZGUL'),
       ('WARLOCK'),
       ('DRUID');

INSERT INTO player(name, title, race_id, profession_id, birthday, banned, experience, level, until_next_level)
VALUES ('Деракт', 'Эльфёнок Красное Ухо', 1, 3, '2010-06-22', false, 156630, 55, 2970)
     , ('Архилл', 'Смертоносный', 3, 1, '2005-01-12', false, 76010, 38, 1990)
     , ('Эндарион', 'Маленький эльфенок', 2, 4, '2001-04-24', false, 103734, 45, 4366)
     , ('Фаэрвин', 'Темный Идеолог', 4, 2, '2010-09-06', false, 7903, 12, 1197)
     , ('Харидин', 'Бедуин', 3, 5, '2009-09-08', false, 114088, 47, 3512)
     , ('Джур', 'БоРец с жАжДой', 5, 3, '2009-07-14', false, 29573, 23, 427)
     , ('Грон', 'Воин обреченный на бой', 4, 6, '2005-04-28', false, 174414, 58, 2586)
     , ('Морвиел', 'Копье Калимы', 6, 4, '2010-03-15', false, 49872, 31, 2928)