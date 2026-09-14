package by.bsuir.excusegenerator.view;

import by.bsuir.excusegenerator.model.ExcuseInput;
import by.bsuir.excusegenerator.model.ExcuseModel;
import by.bsuir.excusegenerator.model.ExcuseState;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/** Главное окно приложения и представление активной модели. */
public final class MainView extends JFrame implements PropertyChangeListener {
    private static final long serialVersionUID = 1L;

    private final JTextArea inputSummary = createTextArea();
    private final JTextArea excuseArea = createTextArea();
    private final JButton inputButton = new JButton("Ввести данные");
    private final JButton generateButton = new JButton("Сгенерировать оправдание");

    public MainView() {
        super("Генератор оправданий — MVC");
        configureWindow();
        setContentPane(createContent());
        pack();
        setLocationRelativeTo(null);
    }

    public void onInputRequested(ActionListener listener) {
        inputButton.addActionListener(listener);
    }

    public void onGenerateRequested(ActionListener listener) {
        generateButton.addActionListener(listener);
    }

    public void open() {
        setVisible(true);
    }

    public void showError(Component owner, String message) {
        JOptionPane.showMessageDialog(owner, message, "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
    }

    public void render(ExcuseState state) {
        if (!state.hasInput()) {
            inputSummary.setText("Данные ещё не введены.");
            excuseArea.setText("Сначала нажмите «Ввести данные».");
            return;
        }

        ExcuseInput input = state.input();
        inputSummary.setText("Выбранная ситуация: " + input.situation());
        excuseArea.setText(state.hasExcuse()
                ? state.excuse()
                : "Данные сохранены. Нажмите «Сгенерировать оправдание».");
        excuseArea.setCaretPosition(0);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (!ExcuseModel.STATE_PROPERTY.equals(event.getPropertyName())) {
            return;
        }

        ExcuseState newState = (ExcuseState) event.getNewValue();
        if (SwingUtilities.isEventDispatchThread()) {
            render(newState);
        } else {
            SwingUtilities.invokeLater(() -> render(newState));
        }
    }

    private void configureWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(680, 430));
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(14, 14));
        content.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel title = new JLabel("Генератор правдоподобных оправданий");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 21f));
        content.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 1, 12, 12));
        center.add(createSection("Последние введённые данные", inputSummary));
        center.add(createSection("Результат", excuseArea));
        content.add(center, BorderLayout.CENTER);
        content.add(createButtons(), BorderLayout.SOUTH);
        return content;
    }

    private JPanel createSection(String title, JTextArea textArea) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBorder(BorderFactory.createTitledBorder(title));
        section.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return section;
    }

    private JPanel createButtons() {
        generateButton.setPreferredSize(new Dimension(230, 38));
        inputButton.setPreferredSize(new Dimension(150, 38));

        JPanel buttons = new JPanel();
        buttons.add(inputButton);
        buttons.add(generateButton);
        return buttons;
    }

    private static JTextArea createTextArea() {
        JTextArea area = new JTextArea(4, 36);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        area.setBackground(new Color(250, 250, 250));
        area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return area;
    }
}
