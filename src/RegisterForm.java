import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterForm extends JFrame implements ActionListener {

    // Initializations

    // UI
    JLabel quizTimeHeader = new JLabel("Register");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    JLabel labelEnterName = new JLabel("Enter username");
    JLabel labelEnterPassword = new JLabel("Enter password");
    JTextField textEnterName = new JTextField();
    JLabel labelSelectRole = new JLabel("Select Role");
    JComboBox<String> roleComboBox = new JComboBox<>(new String[] {"User", "Admin"});
    JPasswordField textEnterPassword = new JPasswordField();
    JButton startQuiz = new JButton("Register");
    JButton startQuiz1 = new JButton("Login");

    JLabel invalidUsername = new JLabel("Please enter a username!");
    JLabel invalidPassword = new JLabel("Please enter a password!");

    // Database
    String url = "jdbc:postgresql://localhost:5432/quizapp";
    String dbUsername = "postgres";
    String dbPassword = "suyash123";
    Connection c;
    Statement st;
    

    RegisterForm() {
        // Window Customization
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#333333"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setLayout(null);

        // Quiz Time header

        quizTimeHeader.setBounds((screenWidth / 2) - 150, 80, 400, 100);
        quizTimeHeader.setFont(new Font("Inter", Font.BOLD, 64));
        quizTimeHeader.setForeground(Color.decode("#5BBA6F"));
        add(quizTimeHeader);

        // Enter username

        labelEnterName.setBounds(240, 340, 280, 50);
        labelEnterName.setForeground(Color.WHITE);
        labelEnterName.setFont(new Font("Inter", Font.PLAIN, 36));
        add(labelEnterName);

        textEnterName.setBounds(640, 340, 420, 50);
        textEnterName.setForeground(Color.WHITE);
        textEnterName.setBackground(Color.decode("#333333"));
        textEnterName.setFont(new Font("Inter", Font.PLAIN, 36));
        textEnterName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        textEnterName.requestFocus();
        textEnterName.setCaretColor(Color.WHITE);
        add(textEnterName);

        // Enter your password
        labelEnterPassword.setBounds(240, 492, 280, 50);
        labelEnterPassword.setForeground(Color.WHITE);
        labelEnterPassword.setFont(new Font("Inter", Font.PLAIN, 36));
        add(labelEnterPassword);

        textEnterPassword.setBounds(640, 492, 420, 50);
        textEnterPassword.setForeground(Color.WHITE);
        textEnterPassword.setBackground(Color.decode("#333333"));
        textEnterPassword.setFont(new Font("Inter", Font.PLAIN, 36));
        textEnterPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        textEnterPassword.setCaretColor(Color.WHITE);
        textEnterPassword.setEchoChar('*');
        add(textEnterPassword);
        // Role 
        labelSelectRole.setBounds(240, 570, 280, 50);
        labelSelectRole.setForeground(Color.WHITE);
        labelSelectRole.setFont(new Font("Inter", Font.PLAIN, 36));
        add(labelSelectRole);

        roleComboBox.setBounds(640, 570, 200, 40);
        roleComboBox.setFont(new Font("Inter", Font.PLAIN, 20));
        add(roleComboBox);

        // Start quiz button
        startQuiz.setBounds(240, 644, 198, 50);
        startQuiz.setForeground(Color.decode("#333333"));
        startQuiz.setBackground(Color.decode("#5BBA6F"));
        startQuiz.setFont(new Font("Inter", Font.BOLD, 32));
        startQuiz.setBorder(null);
        startQuiz.setFocusable(false);
        add(startQuiz);
        startQuiz.addActionListener(this);
        startQuiz1.setBounds(650, 644, 198, 50);
        startQuiz1.setForeground(Color.decode("#333333"));
        startQuiz1.setBackground(Color.decode("#5BBA6F"));
        startQuiz1.setFont(new Font("Inter", Font.BOLD, 32));
        startQuiz1.setBorder(null);
        startQuiz1.setFocusable(false);
        add(startQuiz1);
        startQuiz1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
               new LoginForm();
            }
        
        });
        // Empty username password

        invalidUsername.setBounds(640, 380, 420, 50);
        invalidUsername.setForeground(Color.RED);
        invalidUsername.setFont(new Font("Inter", Font.PLAIN, 18));
        invalidUsername.setVisible(false);
        add(invalidUsername);

        invalidPassword.setBounds(640, 532, 420, 50);
        invalidPassword.setForeground(Color.RED);
        invalidPassword.setFont(new Font("Inter", Font.PLAIN, 18));
        invalidPassword.setVisible(false);
        add(invalidPassword);

    }
    private String getSelectedRole() {
        Object selectedRoleObj = roleComboBox.getSelectedItem();
        if (selectedRoleObj != null) {
            return (String) selectedRoleObj;
        } else {
            // Handle the case when nothing is selected (optional)
            return "DefaultRole"; // Provide a default value or handle as needed
        }
    }
    public void actionPerformed(ActionEvent e) {
        String username = textEnterName.getText();
        char[] passwordChars = textEnterPassword.getPassword();
        String password = new String(passwordChars);
    
        // Assuming you have a way to determine the user's role, e.g., from a JComboBox or a radio button
        String selectedRole = getSelectedRole();
        // private String selectedRole() {
        //     Object selectedRoleObj = roleComboBox.getSelectedItem();
        //     if (selectedRoleObj != null) {
        //         return (String) selectedRoleObj;
        //     } else {
        //         // Handle the case when nothing is selected (optional)
        //         return "DefaultRole"; // Provide a default value or handle as needed
        //     }
        // }// You should set this based on user input
    
        if (username.isEmpty() || passwordChars.length == 0) {
            invalidUsername.setVisible(true);
            invalidPassword.setVisible(true);
        } else {
            invalidUsername.setVisible(false);
            invalidPassword.setVisible(false);
            try {
                c = DriverManager.getConnection(url, dbUsername, dbPassword);
                String insertQuery = "INSERT INTO users (password, username, role) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = c.prepareStatement(insertQuery);
                preparedStatement.setString(1, password);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, selectedRole); // Store the selected role
                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("User Added");
                // Optionally, redirect to the login screen after successful registration
                new LoginForm();
            } catch (SQLException ex) {
                System.err.println("Database error: " + ex.getMessage());
            } finally {
                try {
                    if (c != null) {
                        c.close();
                    }
                } catch (SQLException ex) {
                    System.err.println("Error closing database connection: " + ex.getMessage());
                }
            }
        }
    }
    
    public static void main(String[] args) {
       new RegisterForm();
    }
}