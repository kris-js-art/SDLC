package by.bsuir.excusegenerator.controller;

import by.bsuir.excusegenerator.model.ExcuseInput;
import by.bsuir.excusegenerator.model.ExcuseModel;
import by.bsuir.excusegenerator.model.ValidationException;
import by.bsuir.excusegenerator.view.InputDialog;
import by.bsuir.excusegenerator.view.MainView;

/** Связывает действия пользователя с активной моделью. */
public final class ExcuseController {
    private final ExcuseModel model;
    private final MainView view;

    public ExcuseController(ExcuseModel model, MainView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        model.addPropertyChangeListener(view);
        view.onInputRequested(event -> openInputDialog());
        view.onGenerateRequested(event -> generateExcuse());
        view.render(model.getState());
        view.open();
    }

    private void openInputDialog() {
        ExcuseInput previousInput = model.getLastInput().orElse(null);
        InputDialog dialog = new InputDialog(view, previousInput);

        dialog.onSubmit(() -> {
            try {
                model.saveInput(
                        dialog.getSelectedSituation()
                );
                dialog.close();
            } catch (ValidationException exception) {
                view.showError(dialog, exception.getMessage());
            }
        });

        dialog.open();
    }

    private void generateExcuse() {
        try {
            model.generateExcuse();
        } catch (ValidationException exception) {
            view.showError(view, exception.getMessage());
        }
    }
}
