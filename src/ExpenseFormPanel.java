import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseFormPanel extends JPanel {
    private JTextField amountField;
    private JTextField typeField;
    private JTextField categoryField;
    private JTextField dateField;
    private JTextField descriptionField;
    private JTextField paymentMethodField;
    private JTextField sourceField;
    private String username;

    public ExpenseFormPanel(String username) {
        this.username = username;
        setLayout(new GridLayout(8, 2));

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("Type:"));
        typeField = new JTextField();
        add(typeField);

        add(new JLabel("Category:"));
        categoryField = new JTextField();
        add(categoryField);

        add(new JLabel("Date:"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Description:"));
        descriptionField = new JTextField();
        add(descriptionField);

        add(new JLabel("Payment Method:"));
        paymentMethodField = new JTextField();
        add(paymentMethodField);

        add(new JLabel("Source:"));
        sourceField = new JTextField();
        add(sourceField);

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(new AddExpenseAction());
        add(addButton);
    }

    private class AddExpenseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double amount = Double.parseDouble(amountField.getText());
            String type = typeField.getText();
            String category = categoryField.getText();
            String date = dateField.getText();
            String description = descriptionField.getText();
            String paymentMethod = paymentMethodField.getText();
            String source = sourceField.getText();

            Expense expense = new Expense(0, username, amount, type, category, date, description, paymentMethod, source);
            ExpenseManager.getInstance().addExpense(expense);

            JOptionPane.showMessageDialog(ExpenseFormPanel.this, "Expense added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
