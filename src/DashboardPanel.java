import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JFrame parentFrame;
    private String username;

    public DashboardPanel(JFrame parentFrame, String username) {
        this.parentFrame = parentFrame;
        this.username = username;
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Calendar", new CalendarPanel(username));
        tabbedPane.addTab("Add Expense", new ExpenseFormPanel(username));
        tabbedPane.addTab("Summary", new MonthlySummary(username));

        add(tabbedPane, BorderLayout.CENTER);
    }
}
