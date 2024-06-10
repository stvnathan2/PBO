import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JPanel implements ActionListener {
    private JTextField display;
    private JPanel panel;
    private StringBuilder currentInput;

    private String operator;
    private double firstOperand;
    private boolean operatorSelected;

    public Calculator() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        currentInput = new StringBuilder();
        operatorSelected = false;

        display = new JTextField();
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 36));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(display, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.setBackground(Color.WHITE);

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setBackground(Color.LIGHT_GRAY);
            button.addActionListener(this);
            panel.add(button);
        }

        mainPanel.add(panel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789.".contains(command)) {
            if (operatorSelected) {
                currentInput.setLength(0);
                operatorSelected = false;
            }
            currentInput.append(command);
            display.setText(currentInput.toString());
        } else if ("+-*/".contains(command)) {
            operator = command;
            firstOperand = Double.parseDouble(currentInput.toString());
            operatorSelected = true;
        } else if ("=".equals(command)) {
            double secondOperand = Double.parseDouble(currentInput.toString());
            double result = calculate(firstOperand, secondOperand, operator);
            display.setText(String.valueOf(result));
            currentInput.setLength(0);
            currentInput.append(result);
        }
    }

    private double calculate(double firstOperand, double secondOperand, String operator) {
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "*":
                return firstOperand * secondOperand;
            case "/":
                if (secondOperand != 0) {
                    return firstOperand / secondOperand;
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
            default:
                return 0;
        }
    }
}
