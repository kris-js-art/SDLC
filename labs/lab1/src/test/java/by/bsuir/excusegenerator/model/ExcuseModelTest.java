package by.bsuir.excusegenerator.model;

import java.util.concurrent.atomic.AtomicInteger;

/** Простые автономные проверки модели без внешних тестовых библиотек. */
public final class ExcuseModelTest {
    private ExcuseModelTest() {
    }

    public static void main(String[] args) throws Exception {
        storesInput();
        notifiesViewAboutChanges();
        rejectsInvalidFields();
        requiresInputBeforeGeneration();
        containsTenGeneralTemplatesPerSituation();
        generatesForEverySituation();
        System.out.println("ExcuseModel checks passed.");
    }

    private static void storesInput() throws Exception {
        ExcuseModel model = new ExcuseModel();
        model.saveInput(Situation.STUDY);

        ExcuseInput input = model.getLastInput().orElseThrow();
        check(input.situation() == Situation.STUDY, "Ситуация должна сохраняться");
    }

    private static void notifiesViewAboutChanges() throws Exception {
        ExcuseModel model = new ExcuseModel();
        AtomicInteger events = new AtomicInteger();
        model.addPropertyChangeListener(event -> events.incrementAndGet());

        model.saveInput(Situation.WORK);
        model.generateExcuse();

        check(events.get() == 2, "Активная модель должна отправить два события");
    }

    private static void rejectsInvalidFields() {
        expectValidation(() -> new ExcuseModel().saveInput(null));
    }

    private static void requiresInputBeforeGeneration() {
        expectValidation(() -> new ExcuseModel().generateExcuse());
    }

    private static void containsTenGeneralTemplatesPerSituation() {
        for (Situation situation : Situation.values()) {
            var templates = ExcuseModel.templatesFor(situation);
            check(templates.size() == 10, "Каждая ситуация должна содержать 10 оправданий");
            check(templates.stream().noneMatch(text -> text.startsWith("Здравствуйте")),
                    "Шаблоны не должны содержать прямое обращение");
            check(templates.stream().noneMatch(text -> text.contains("%")),
                    "Шаблоны не должны содержать подстановочные значения");
        }
    }

    private static void generatesForEverySituation() throws Exception {
        for (Situation situation : Situation.values()) {
            ExcuseModel model = new ExcuseModel();
            model.saveInput(situation);
            model.generateExcuse();

            String result = model.getState().excuse();
            check(!result.isBlank(), "Оправдание не должно быть пустым");
            check(!result.startsWith("Здравствуйте"), "В оправдании не должно быть обращения");
        }
    }

    private static void expectValidation(CheckedAction action) {
        try {
            action.run();
            throw new AssertionError("Ожидалась ошибка валидации");
        } catch (ValidationException expected) {
            check(!expected.getMessage().isBlank(), "Ошибка должна содержать понятное сообщение");
        } catch (Exception exception) {
            throw new AssertionError("Получено неожиданное исключение", exception);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    @FunctionalInterface
    private interface CheckedAction {
        void run() throws Exception;
    }
}
