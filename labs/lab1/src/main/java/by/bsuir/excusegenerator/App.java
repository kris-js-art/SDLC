package by.bsuir.excusegenerator;

import by.bsuir.excusegenerator.controller.ExcuseController;
import by.bsuir.excusegenerator.model.ExcuseModel;
import by.bsuir.excusegenerator.view.MainView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/** Точка входа в приложение. */
public final class App {
    private App() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            useSystemLookAndFeel();

            ExcuseModel model = new ExcuseModel();
            MainView view = new MainView();
            ExcuseController controller = new ExcuseController(model, view);
            controller.start();
        });
    }

    private static void useSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ignored) {
            // Стандартное оформление Swing остаётся безопасным запасным вариантом.
        }
    }
}

