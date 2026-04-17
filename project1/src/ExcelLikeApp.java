import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ExcelLikeApp extends JFrame {
    private JTextField numberNField;
    private JTextField timeField;
    private JTextField resultField;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private List<PerformanceData> dataList;

    public ExcelLikeApp() {
        setTitle("Упрощённый Excel — Фиксация производительности");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        numberNField = new JTextField(15);
        timeField = new JTextField(15);
        resultField = new JTextField(15);

        tableModel = new DefaultTableModel(
                new Object[]{"№", "Число N", "Время (сек)", "Получившееся число"}, 0
        );
        dataTable = new JTable(tableModel);
        dataTable.setRowHeight(25);
        dataList = new ArrayList<>();
    }

    private void setupLayout() {
        // Основной контейнер
        setLayout(new BorderLayout(10, 10));


        // Панель ввода данных
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Ввод данных"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Число N:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(numberNField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Время (сек):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(timeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Получившееся число:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(resultField, gbc);

        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addButton = new JButton("Добавить");
        JButton deleteButton = new JButton("Удалить");
        JButton clearButton = new JButton("Очистить");

        addButton.addActionListener(this::addData);
        deleteButton.addActionListener(this::deleteData);
        clearButton.addActionListener(e -> clearFields());

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // Таблица с прокруткой
        JScrollPane tableScroll = new JScrollPane(dataTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Данные производительности"));

        // Добавляем компоненты в окно
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tableScroll, BorderLayout.SOUTH);
    }

    private void addData(ActionEvent e) {
        try {
            int numberN = Integer.parseInt(numberNField.getText());
            double timeSeconds = Double.parseDouble(timeField.getText());
            long resultNumber = Long.parseLong(resultField.getText());

            if (timeSeconds <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Время должно быть положительным числом!",
                        "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                timeField.requestFocus();
                return;
            }

            PerformanceData data = new PerformanceData(numberN, timeSeconds, resultNumber);
            dataList.add(data);

            Object[] row = {dataList.size(), numberN, String.format("%.3f", timeSeconds), resultNumber};
            tableModel.addRow(row);

            clearFields();
            numberNField.requestFocus();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Пожалуйста, введите корректные числовые значения!",
                    "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteData(ActionEvent e) {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            dataList.remove(selectedRow);

            // Обновляем нумерацию
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                tableModel.setValueAt(i + 1, i, 0);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Выберите строку для удаления!",
                    "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearFields() {
        numberNField.setText("");
        timeField.setText("");
        resultField.setText("");
        numberNField.requestFocus();
    }
}
