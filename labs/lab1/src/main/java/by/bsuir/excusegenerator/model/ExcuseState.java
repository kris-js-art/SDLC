package by.bsuir.excusegenerator.model;

import java.util.Objects;

/** Неизменяемый снимок состояния активной модели. */
public record ExcuseState(ExcuseInput input, String excuse) {
    public ExcuseState {
        excuse = Objects.requireNonNullElse(excuse, "");
    }

    public static ExcuseState empty() {
        return new ExcuseState(null, "");
    }

    public boolean hasInput() {
        return input != null;
    }

    public boolean hasExcuse() {
        return !excuse.isBlank();
    }
}

