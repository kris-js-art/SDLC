package by.bsuir.excusegenerator.model;

/** Последние корректные данные пользователя. */
public record ExcuseInput(Situation situation) {
    public ExcuseInput {
        if (situation == null) {
            throw new IllegalArgumentException("Ситуация не выбрана");
        }
    }
}
