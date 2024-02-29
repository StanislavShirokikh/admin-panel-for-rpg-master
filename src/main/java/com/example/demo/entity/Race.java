package com.example.demo.entity;

public enum Race {
    HUMAN(1),
    DWARF(2),
    ELF(3),
    GIANT(4),
    ORC(5),
    TROLL(6),
    HOBBIT(7);

    private final Integer id;

    Race(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}