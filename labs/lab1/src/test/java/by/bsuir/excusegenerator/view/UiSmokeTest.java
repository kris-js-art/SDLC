package by.bsuir.excusegenerator.view;

import by.bsuir.excusegenerator.model.ExcuseInput;
import by.bsuir.excusegenerator.model.ExcuseState;
import by.bsuir.excusegenerator.model.Situation;

import javax.swing.SwingUtilities;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.atomic.AtomicReference;

/** Проверяет создание обеих Swing-форм и восстановление введённых данных. */
public final class UiSmokeTest {
    private UiSmokeTest() {
    }

    public static void main(String[] args) throws Exception {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("UI smoke test skipped: headless environment.");
            return;
        }

        AtomicReference<Throwable> failure = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            MainView view = null;
            InputDialog dialog = null;
            try {
                ExcuseInput input = new ExcuseInput(Situation.FORGETFULNESS);

                view = new MainView();
                view.render(new ExcuseState(input, "Тестовое оправдание"));

                dialog = new InputDialog(view, input);
                check(dialog.getSelectedSituation() == input.situation(), "Situation was not restored");
            } catch (Throwable throwable) {
                failure.set(throwable);
            } finally {
                if (dialog != null) {
                    dialog.dispose();
                }
                if (view != null) {
                    view.dispose();
                }
            }
        });

        if (failure.get() != null) {
            throw new AssertionError("UI smoke test failed", failure.get());
        }
        System.out.println("Swing UI smoke test passed.");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
