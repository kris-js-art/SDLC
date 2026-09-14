package by.bsuir.excusegenerator.model;

/** Ситуации из варианта лабораторной работы. */
public enum Situation {
    STUDY("Учёба"),
    WORK("Работа"),
    LATE("Опоздание"),
    FORGETFULNESS("Забывчивость");

    private final String displayName;

    Situation(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

