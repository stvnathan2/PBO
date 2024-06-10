import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RegisterPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame parentFrame;

    public RegisterPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterAction());
        add(registerButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            parentFrame.setContentPane(new LoginPanel(parentFrame));
            parentFrame.validate();
        });
        add(backButton);
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Username and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserManager userManager = UserManager.getInstance();
            if (userManager.addUser(new User(username, password))) {
                JOptionPane.showMessageDialog(parentFrame, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                parentFrame.setContentPane(new LoginPanel(parentFrame));
                parentFrame.validate();
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
