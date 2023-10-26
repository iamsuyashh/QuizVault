import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame implements ActionListener {

    // Initializations

    // UI
    JLabel quizTimeHeader = new JLabel("QUIZZ TIME");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    JLabel labelEnterName = new JLabel("Enter username");
    JLabel labelEnterPassword = new JLabel("Enter password");
    JTextField textEnterName = new JTextField();
    JPasswordField textEnterPassword = new JPasswordField();
    JButton startQuiz = new JButton("Take Quiz");
    JLabel invalidUsername = new JLabel("Please enter a username!");
    JLabel invalidPassword = new JLabel("Please enter a password!");

    // Database
    String url = "jdbc:postgresql://localhost:5432/quizapp";
    String dbUsername = "postgres";
    String dbPassword = "suyash123";
    Connection c;
    Statement st;
    

    LoginForm() {
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

        // Start quiz button
        startQuiz.setBounds(240, 644, 198, 50);
        startQuiz.setForeground(Color.decode("#333333"));
        startQuiz.setBackground(Color.decode("#5BBA6F"));
        startQuiz.setFont(new Font("Inter", Font.BOLD, 32));
        startQuiz.setBorder(null);
        startQuiz.setFocusable(false);
        add(startQuiz);
        startQuiz.addActionListener(this);

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

    public void actionPerformed(ActionEvent e) {

        String username = textEnterName.getText();
        String password = textEnterPassword.getPassword().toString();
        System.out.println(username);
        System.out.println(password);

        if (username.isEmpty()) {
            invalidUsername.setVisible(true);
            System.out.println("Empty");
            repaint();
        } else if (textEnterPassword.getPassword().length == 0) {
            invalidPassword.setVisible(true);
            System.out.println("Empty");
            repaint();
        } else {
            System.out.println("Initiated");
            invalidUsername.setVisible(false);
            invalidPassword.setVisible(false);
            try {
                c = DriverManager.getConnection(url, dbUsername, dbPassword);
                Statement st = c.createStatement();
                st.executeUpdate("INSERT INTO users" + " VALUES ('"+ username + "', '" +  password+"')");
                System.out.println("User Added");
                new QuizForm();
                
            } catch (Exception de) {
                System.out.println(de);
            }
        }
    }
    public static void main(String[] args) {
        new LoginForm();
    }
}

