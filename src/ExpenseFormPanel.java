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

public class ExpenseFormPanel extends JPanel {
    private JTextField amountField;
    private JComboBox<String> typeField;
    private JTextField categoryField;
    private JDatePickerImpl datePicker;
    private JTextField descriptionField;
    private JComboBox<String> paymentMethodField;
    private JTextField accountField;
    private String username;

    public ExpenseFormPanel(String username) {
        this.username = username;
        setLayout(new GridLayout(8, 2));

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("Type:"));
        typeField = new JComboBox<>(new String[]{"pemasukan", "pengeluaran"});
        add(typeField);

        add(new JLabel("Category:"));
        categoryField = new JTextField();
        add(categoryField);

        add(new JLabel("Date:"));
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        add(datePicker);

        add(new JLabel("Description:"));
        descriptionField = new JTextField();
        add(descriptionField);

        add(new JLabel("Payment Method:"));
        paymentMethodField = new JComboBox<>(new String[]{"cash", "digital"});
        add(paymentMethodField);

        add(new JLabel("Account:"));
        accountField = new JTextField();
        add(accountField);

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(new AddExpenseAction());
        add(addButton);
    }

    private class AddExpenseAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        double amount = Double.parseDouble(amountField.getText());
        String type = (String) typeField.getSelectedItem();
        String category = categoryField.getText();
        // Ambil tanggal dari JDatePicker dan ubah menjadi tipe java.sql.Date
        Date date = (Date) datePicker.getModel().getValue();
        String description = descriptionField.getText();
        String paymentMethod = (String) paymentMethodField.getSelectedItem();
        String account = accountField.getText();

        Expense expense = new Expense(0, username, amount, type, category, date, description, paymentMethod, account);
        ExpenseManager.getInstance().addExpense(expense);

        JOptionPane.showMessageDialog(ExpenseFormPanel.this, "Input telah ditambahkan", "Success", JOptionPane.INFORMATION_MESSAGE);
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
}
