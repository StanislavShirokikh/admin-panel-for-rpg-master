package com.example.demo.entity;

public enum Profession {
    WARRIOR(1),
    ROGUE(2),
    SORCERER(3),
    CLERIC(4),
    PALADIN(5),
    NAZGUL(6),
    WARLOCK(7),
    DRUID(8);

    private final Integer id;

    Profession(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
