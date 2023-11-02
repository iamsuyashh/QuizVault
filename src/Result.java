import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Result extends JFrame {
    // Initializations
    JPanel container = new JPanel();
    JLabel remark = new JLabel("Excellent");
    String statusRemark = "";
    String statusColor = "";
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    JLabel rightAnswers = new JLabel("Number of correct answers");
    JLabel wrongAnswers = new JLabel("Number of wrong answers");
    JLabel notAnswered = new JLabel("Number of answers not attempted");
    JLabel numRight = new JLabel();
    JLabel numWrong = new JLabel();
    JLabel numNotAnswered = new JLabel("0");
    JLabel percent = new JLabel();
    String url = "jdbc:postgresql://localhost:5432/quizapp";
    String dbUsername = "postgres";
    String dbPassword = "suyash123";
    Connection c;
    Statement st;
    Result(int correct, int wrong, int notAttempted) {
        // window customization
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#333333"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setLayout(null);

        // Remark Container

        if (correct >= 4) {
            statusRemark = "Excellent";
            statusColor = "#3DDC97";
        } else if (correct < 4 && correct >= 2) {
            statusRemark = "Well Done";
            statusColor = "#256EFF";
        } else {
            statusRemark = "Nice Try";
            statusColor = "#FF495C";
        }
        container.setBounds(0, 0, screenWidth, 300);
        container.setBackground(Color.decode(statusColor));
        container.setLayout(null);
        add(container);
        remark.setBounds((screenWidth / 2 - 300), 90, 760, 150);
        remark.setForeground(Color.WHITE);
        remark.setFont(new Font("Inter", Font.BOLD, 128));
        remark.setText(statusRemark);
        container.add(remark);

        // status
        rightAnswers.setBounds(180, 400, 420, 40);
        rightAnswers.setFont(new Font("Inter", Font.BOLD, 32));
        rightAnswers.setForeground(Color.decode("#3DDC97"));
        add(rightAnswers);

        wrongAnswers.setBounds(180, 500, 420, 40);
        wrongAnswers.setFont(new Font("Inter", Font.BOLD, 32));
        wrongAnswers.setForeground(Color.decode("#FF495C"));
        add(wrongAnswers);

        notAnswered.setBounds(180, 600, 540, 40);
        notAnswered.setFont(new Font("Inter", Font.BOLD, 32));
        notAnswered.setForeground(Color.decode("#FFFFFF"));
        add(notAnswered);

        numRight.setBounds(1110, 400, 420, 40);
        numRight.setFont(new Font("Inter", Font.BOLD, 32));
        numRight.setForeground(Color.decode("#3DDC97"));
        numRight.setText(Integer.toString(correct));
        add(numRight);

        numWrong.setBounds(1110, 500, 420, 40);
        numWrong.setFont(new Font("Inter", Font.BOLD, 32));
        numWrong.setForeground(Color.decode("#FF495C"));
        numWrong.setText(Integer.toString(wrong));
        add(numWrong);

        numNotAnswered.setBounds(1110, 600, 540, 40);
        numNotAnswered.setFont(new Font("Inter", Font.BOLD, 32));
        numNotAnswered.setForeground(Color.decode("#FFFFFF"));
        numNotAnswered.setText(Integer.toString(notAttempted));
        add(numNotAnswered);

        percent.setBounds(screenWidth / 2, 670, 540, 100);
        percent.setFont(new Font("Inter", Font.BOLD, 64));
        percent.setForeground(Color.decode(statusColor));
        int numPercent = (int)(((double) correct / 5) * 100);
        percent.setText(Integer.toString(numPercent) + "%");
        System.out.print(numPercent);
        add(percent);
        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String insertQuery = "INSERT INTO users (percentage) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setDouble(1, numPercent); // Set the percentage value
            preparedStatement.executeUpdate();
            preparedStatement.close();
                System.out.println("Percentage updated");
                // Optionally, redirect to the login screen after successful registration
                // new LoginForm();
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

    public void result() {

    }
}
