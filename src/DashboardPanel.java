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

        tabbedPane.addTab("Kalender", createTabPanel(new CalendarPanel(username), "src\\calendarIcon.png"));
        tabbedPane.addTab("Input Data", createTabPanel(new ExpenseFormPanel(username), "src\\expenseIcon.png"));
        tabbedPane.addTab("Tabel", createTabPanel(new Categories(username), "src\\tableIcon.png"));
        tabbedPane.addTab("Laporan Bulanan", createTabPanel(new MonthlySummary(username), "src\\summaryIcon.png"));
        tabbedPane.addTab("Kalkulator", createTabPanel(new Calculator(), "src\\calculatorIcon.png"));

        tabbedPane.setBackground(new Color(245, 245, 245));
        tabbedPane.setForeground(new Color(70, 130, 180));
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        add(tabbedPane, BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(220, 220, 220));
    }

    private JPanel createTabPanel(JPanel panel, String iconPath) {
        ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(panel.getClass().getSimpleName(), icon, JLabel.LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(label, BorderLayout.NORTH);
        return panel;
    }
}
