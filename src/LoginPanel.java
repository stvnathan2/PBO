import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame parentFrame;

    public LoginPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        ImageIcon loginIcon = new ImageIcon(new ImageIcon("src\\loginIcon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction());
        loginButton.setIcon(loginIcon);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        add(loginButton, gbc);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Username and password are required", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (UserManager.getInstance().validateUser(username, password)) {
                    parentFrame.setContentPane(new DashboardPanel(parentFrame, username));
                    parentFrame.validate();
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
