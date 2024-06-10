import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Finance Management");
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTabbedPane tabbedPane = new JTabbedPane();

            tabbedPane.addTab("Login", createTabPanel(new LoginPanel(frame), "src\\loginIcon.png"));
            tabbedPane.addTab("Register", createTabPanel(new RegisterPanel(frame), "src\\registerIcon.png"));

            frame.add(tabbedPane);

            frame.setVisible(true);
        });
    }

    private static JPanel createTabPanel(JPanel panel, String iconPath) {
        ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JPanel tabPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(panel.getClass().getSimpleName(), icon, JLabel.LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabPanel.add(label, BorderLayout.NORTH);
        tabPanel.add(panel, BorderLayout.CENTER);
        return tabPanel;
    }
}
