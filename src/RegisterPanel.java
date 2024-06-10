import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JComboBox<String> genderComboBox;
    private JFrame parentFrame;

    public RegisterPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        fullNameField = new JTextField(15);
        add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        emailField = new JTextField(15);
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        add(genderComboBox, gbc);

        ImageIcon registerIcon = new ImageIcon(new ImageIcon("src\\registerIcon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterAction());
        registerButton.setIcon(registerIcon);
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.WHITE);
        add(registerButton, gbc);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String gender = (String) genderComboBox.getSelectedItem();

            User user = new User(username, password, fullName, email, gender);

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Username and password are required", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (UserManager.getInstance().addUser(user)) {
                    JOptionPane.showMessageDialog(parentFrame, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    parentFrame.setContentPane(new LoginPanel(parentFrame));
                    parentFrame.validate();
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
