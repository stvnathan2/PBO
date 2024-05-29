import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CalendarPanel extends JPanel {
    private String username;

    public CalendarPanel(String username) {
        this.username = username;
        setLayout(new GridLayout(5, 7));

        List<Expense> expenses = ExpenseManager.getInstance().getExpensesByUser(username);
        for (int i = 1; i <= 30; i++) {
            add(new JLabel("Day " + i));
        }
    }
}
