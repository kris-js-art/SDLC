package by.bsuir.excusegenerator.model;

/** Ошибка проверки пользовательского ввода. */
public final class ValidationException extends Exception {
    private static final long serialVersionUID = 1L;

    public ValidationException(String message) {
        super(message);
    }
}
