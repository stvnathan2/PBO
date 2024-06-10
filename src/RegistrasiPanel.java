import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrasiPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JComboBox<String> genderComboBox;
    private JFrame parentFrame;

    public RegistrasiPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Full Name:"));
        fullNameField = new JTextField();
        add(fullNameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Gender:"));
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        add(genderComboBox);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterAction());
        add(registerButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> parentFrame.setContentPane(new LoginPanel(parentFrame)));
        add(backButton);
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String gender = (String) genderComboBox.getSelectedItem();

            // Create a User object with the additional fields
            User user = new User(username, password, fullName, email, gender);

            // Attempt to add the user to the UserManager
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

// Modifying the User class to accommodate new fields
class User {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String gender;

    public User(String username, String password, String fullName, String email, String gender) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
    }

    // Getters and setters for the new fields
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
