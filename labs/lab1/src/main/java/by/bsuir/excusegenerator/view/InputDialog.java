package by.bsuir.excusegenerator.view;

import by.bsuir.excusegenerator.model.ExcuseInput;
import by.bsuir.excusegenerator.model.Situation;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

/** Модальное окно ввода данных. */
public final class InputDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JComboBox<Situation> situationBox = new JComboBox<>(Situation.values());
    private final JButton saveButton = new JButton("Сохранить");

    public InputDialog(Frame owner, ExcuseInput previousInput) {
        super(owner, "Ввод данных", true);
        configureWindow();
        setContentPane(createContent());
        restore(previousInput);
        pack();
        setMinimumSize(new Dimension(400, getHeight()));
        setLocationRelativeTo(owner);
    }

    public Situation getSelectedSituation() {
        return (Situation) situationBox.getSelectedItem();
    }

    public void onSubmit(Runnable action) {
        saveButton.addActionListener(event -> action.run());
    }

    public void open() {
        setVisible(true);
    }

    public void close() {
        dispose();
    }

    private void configureWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        getRootPane().setDefaultButton(saveButton);
        getRootPane().registerKeyboardAction(
                event -> dispose(),
                KeyStroke.getKeyStroke("ESCAPE"),
                javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 14, 16));
        content.add(createForm(), BorderLayout.CENTER);
        content.add(createButtons(), BorderLayout.SOUTH);
        return content;
    }

    private JPanel createForm() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.anchor = GridBagConstraints.LINE_START;

        addRow(form, constraints, 0, "Ситуация:", situationBox);

        JLabel hint = new JLabel("Выберите тематику будущего оправдания");
        hint.setForeground(java.awt.Color.GRAY);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        form.add(hint, constraints);
        return form;
    }

    private JPanel createButtons() {
        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(this::cancel);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.add(cancelButton);
        buttons.add(saveButton);
        return buttons;
    }

    private static void addRow(
            JPanel panel,
            GridBagConstraints constraints,
            int row,
            String label,
            java.awt.Component field
    ) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(label), constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, constraints);
    }

    private void restore(ExcuseInput previousInput) {
        if (previousInput == null) {
            situationBox.setSelectedIndex(-1);
            return;
        }
        situationBox.setSelectedItem(previousInput.situation());
    }

    private void cancel(ActionEvent event) {
        dispose();
    }
}
