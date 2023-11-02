import javax.swing.*;
import java.awt.*;
// import java.awt.event.*; 
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizForm extends JFrame {
    JLabel quizTimeHeader = new JLabel("QUIZZ TIME");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    JLabel question = new JLabel("What is the capital of India");
    JRadioButton option1 = new JRadioButton("Mumbai");
    JRadioButton option2 = new JRadioButton("New Delhi");
    JRadioButton option3 = new JRadioButton("Pune");
    JRadioButton option4 = new JRadioButton("Nashik");
    JRadioButton option5 = new JRadioButton("Nashik");
    ButtonGroup bg = new ButtonGroup();
    JButton save = new JButton("Save");
    JButton next = new JButton("Next");

    String url = "jdbc:postgresql://localhost:5432/quizapp";
    String dbUsername = "postgres";
    String dbPassword = "suyash123";
    Connection c;
    Statement st;
    ResultSet rs;
    // ResultSet rs2;

    static int[] userAnswer = new int[10];
    int questionNum = 0;
    int correct = 0;
    int wrong = 0;
    int notAnswered = 0;

    private JLabel timerLabel;
    private int timeInSeconds = 30; // Initial time in seconds (5 minutes)
    private Timer timer;

    public void updateTimerLabel() {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        if (timeInSeconds <= 5) {
            timerLabel.setForeground(Color.RED);
        } else {
            timerLabel.setForeground(Color.decode("#FFFFFF"));
        }
        timerLabel.setText("Time: " + minutes + ":" + String.format("%02d", seconds));
    }

    QuizForm() {

        try {
            c = DriverManager.getConnection(url, dbUsername, dbPassword);
            st = c.createStatement();
            rs = st.executeQuery("SELECT * from questions");
            System.out.println(rs);
            // rs2 = st.executeQuery("Select * from questions");
            rs.next();
            // rs2.next();
            question.setText(rs.getString(6));
            option1.setText(rs.getString(2));
            option2.setText(rs.getString(3));
            option3.setText(rs.getString(4));
            option4.setText(rs.getString(5));
        } catch (Exception e) {
            System.out.println(e);
        }

        // Window Customization
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#333333"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setLayout(null);

        // Quiz Time header

        timerLabel = new JLabel("Time: " + timeInSeconds / 60 + ":" + String.format("%02d", timeInSeconds % 60));
        timerLabel.setBounds(screenWidth - 200, 80, 180, 30);
        timerLabel.setFont(new Font("Inter", Font.BOLD, 20));
        timerLabel.setForeground(Color.decode("#FFFFFF"));
        add(timerLabel);
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeInSeconds--;
                if (timeInSeconds < 0) {
                    // Time's up - handle this event
                    timer.stop();

                    if (questionNum < 9) { // Assuming you have 10 questions
                        questionNum++;
                        // Load the next question and options
                        try {
                            if (rs.next()) {
                                question.setText(rs.getString(6));
                                option1.setText(rs.getString(2));
                                option2.setText(rs.getString(3));
                                option3.setText(rs.getString(4));
                                option4.setText(rs.getString(5));

                                // Reset the timer for the next question
                                timeInSeconds = 30;
                                updateTimerLabel();
                                timer.start();
                            } else {
                                // Handle the case where there are no more questions
                                new Result(correct, wrong, notAnswered);
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    } else {
                        // Handle the case where all questions are answered
                        new Result(correct, wrong, notAnswered);
                    }
                } else {
                    updateTimerLabel();
                }
            }
        });

        timer.start();

        // Question
        question.setBounds(90, 320, 3000, 35);
        question.setFont(new Font("Inter", Font.BOLD, 22));
        question.setForeground(Color.decode("#FFFFFF"));
        add(question);

        //
        option1.setBounds(90, 400, 1000, 35);
        option2.setBounds(90, 450, 1000, 35);
        option3.setBounds(90, 500, 1000, 35);
        option4.setBounds(90, 550, 1000, 35);

        option1.setForeground(Color.WHITE);
        option2.setForeground(Color.WHITE);
        option3.setForeground(Color.WHITE);
        option4.setForeground(Color.WHITE);

        option1.setBackground(Color.decode("#333333"));
        option2.setBackground(Color.decode("#333333"));
        option3.setBackground(Color.decode("#333333"));
        option4.setBackground(Color.decode("#333333"));

        option1.setFont(new Font("Inter", Font.BOLD, 28));
        option2.setFont(new Font("Inter", Font.BOLD, 28));
        option3.setFont(new Font("Inter", Font.BOLD, 28));
        option4.setFont(new Font("Inter", Font.BOLD, 28));

        option1.setBorder(null);
        option2.setBorder(null);
        option3.setBorder(null);
        option4.setBorder(null);

        option1.setFocusPainted(false);
        option2.setFocusPainted(false);
        option3.setFocusPainted(false);
        option4.setFocusPainted(false);

        option5.setVisible(false);

        bg.add(option1);
        bg.add(option2);
        bg.add(option3);
        bg.add(option4);
        bg.add(option5);
        add(option1);
        add(option2);
        add(option3);
        add(option4);
        add(option5);

        // buttons
        save.setBounds(90, 650, 200, 50);
        save.setForeground(Color.decode("#333333"));
        save.setBackground(Color.decode("#5BBA6F"));
        save.setFont(new Font("Inter", Font.BOLD, 32));
        save.setBorder(null);
        save.setFocusable(false);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (option1.isSelected())
                    userAnswer[questionNum] = 1;
                else if (option2.isSelected())
                    userAnswer[questionNum] = 2;
                else if (option3.isSelected())
                    userAnswer[questionNum] = 3;
                else if (option4.isSelected())
                    userAnswer[questionNum] = 4;
                else
                    userAnswer[questionNum] = 0;
            }
        });
        add(save);
        System.out.println(userAnswer[questionNum]);
        next.setBounds(1200, 650, 200, 50);
        next.setForeground(Color.decode("#333333"));
        next.setBackground(Color.decode("#5BBA6F"));
        next.setFont(new Font("Inter", Font.BOLD, 32));
        next.setBorder(null);
        next.setFocusable(false);
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ne) {
                try {
                    if (userAnswer[questionNum] == rs.getInt(1)) {
                        System.out.println(rs);
                        correct++;
                    } else if (userAnswer[questionNum] == 0) {
                        notAnswered++;
                    } else {
                        wrong++;
                    }
                    if (rs.next()) {
                        option5.setSelected(true);
                        question.setText(rs.getString(6));
                        option1.setText(rs.getString(2));
                        option2.setText(rs.getString(3));
                        option3.setText(rs.getString(4));
                        option4.setText(rs.getString(5));
                        questionNum++;

                        if (questionNum == 5) {
                            next.setText("See Result");
                        }
                    } else {
                        new Result(correct, wrong, notAnswered);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        add(next);
    }

}
