import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.util.Properties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExpenseFormPanel extends JPanel {
    private JTextField amountField;
    private JComboBox<String> typeField;
    private JComboBox<String> categoryField; 
    private JDatePickerImpl datePicker;
    private JTextField descriptionField;
    private JComboBox<String> paymentMethodField;
    private JTextField accountField;
    private String username;

    public ExpenseFormPanel(String username) {
        this.username = username;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));

        formPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        formPanel.add(amountField);

        formPanel.add(new JLabel("Type:"));
        typeField = new JComboBox<>(new String[]{"Pemasukan", "Pengeluaran"});
        typeField.addActionListener(new TypeSelectionListener());
        formPanel.add(typeField);

        formPanel.add(new JLabel("Category:"));
        categoryField = new JComboBox<>();
        categoryField.addItem("Gaji");
        formPanel.add(categoryField);

        formPanel.add(new JLabel("Date:"));
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        formPanel.add(datePicker);

        formPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        formPanel.add(descriptionField);

        formPanel.add(new JLabel("Payment Method:"));
        paymentMethodField = new JComboBox<>(new String[]{"Cash", "Digital"});
        formPanel.add(paymentMethodField);

        formPanel.add(new JLabel("Account:"));
        accountField = new JTextField();
        formPanel.add(accountField);

        add(formPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("Masukkan Data");
        addButton.addActionListener(new AddExpenseAction());
        add(addButton, BorderLayout.SOUTH);
    }

    private class AddExpenseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String type = (String) typeField.getSelectedItem();
                String category = (String) categoryField.getSelectedItem();
                java.util.Date dateUtil = (java.util.Date) datePicker.getModel().getValue();
                java.sql.Date date = new java.sql.Date(dateUtil.getTime());
                String description = descriptionField.getText();
                String paymentMethod = (String) paymentMethodField.getSelectedItem();
                String account = accountField.getText();

                Expense expense = new Expense(username, amount, type, category, date, description, paymentMethod, account);
                ExpenseManager.getInstance().addExpense(expense);

                JOptionPane.showMessageDialog(ExpenseFormPanel.this, "Input has been added", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ExpenseFormPanel.this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    private class TypeSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedType = (String) typeField.getSelectedItem();
            updateCategoryOptions(selectedType);
        }
    }

    private void updateCategoryOptions(String selectedType) {
        if (selectedType.equals("Pengeluaran")) {
            categoryField.removeAllItems();
            categoryField.addItem("Hiburan");
            categoryField.addItem("Transportasi");
            categoryField.addItem("Makan");
            categoryField.addItem("Kesehatan");
            categoryField.addItem("Belanja");
        } else if (selectedType.equals("Pemasukan")) {
            categoryField.removeAllItems();
            categoryField.addItem("Gaji");
        } 
    }
}
