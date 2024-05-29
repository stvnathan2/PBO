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
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction());
        add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> parentFrame.setContentPane(new RegisterPanel(parentFrame)));
        add(registerButton);
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (UserManager.getInstance().validateUser(username, password)) {
                parentFrame.setContentPane(new DashboardPanel(parentFrame, username));
                parentFrame.validate();
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
