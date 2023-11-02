import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.*;

import java.sql.ResultSet;
import java.sql.Statement;

public class AdminPage extends JFrame {
    private JTable userScoresTable;
    private JTable questionsTable;
    private DefaultTableModel userScoresTableModel;
    private DefaultTableModel questionsTableModel;
    public AdminPage() {
        // Set up the JFrame and other UI components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the background color of the content pane to #333333
        getContentPane().setBackground(Color.decode("#333333"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode("#333333"));
        setContentPane(mainPanel); 
     
    
        // Create a table to display user scores
        userScoresTableModel = new DefaultTableModel();
        userScoresTableModel.addColumn("Username");
        userScoresTableModel.addColumn("Score (%)");
        userScoresTable = new JTable(userScoresTableModel);
        userScoresTable.setForeground(Color.decode("#3"));
        JScrollPane userScoresScrollPane = new JScrollPane(userScoresTable);
        
    
        // Add the table to the JFrame
        add(userScoresScrollPane);
    
        // Fetch and display user scores from the database
        fetchUserScores();
    
        questionsTableModel = new DefaultTableModel();
        questionsTableModel.addColumn("Question");
        questionsTableModel.addColumn("Option 1");
        questionsTableModel.addColumn("Option 2");
        questionsTableModel.addColumn("Option 3");
        questionsTableModel.addColumn("Option 4");
        questionsTableModel.addColumn("Correct Option");
        questionsTable = new JTable(questionsTableModel);
        JScrollPane questionsScrollPane = new JScrollPane(questionsTable);
        
    
        // Add the table to the JFrame
        JPanel tablePanel = new JPanel(new GridLayout(2, 1));
        tablePanel.add(userScoresScrollPane);
        tablePanel.add(questionsScrollPane);
    
        // Set the background color of the tablePanel to white
        tablePanel.setBackground(Color.decode("#333333"));
    
        // Add the table panel to the JFrame
        add(tablePanel, BorderLayout.CENTER);
        fetchquestions();
    }
    

    private void fetchUserScores() {
        try {
            // Establish a database connection
            String url = "jdbc:postgresql://localhost:5432/quizapp";
            String dbUsername = "postgres";
            String dbPassword = "suyash123";
            Connection c = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Execute a query to fetch user scores
            String query = "SELECT * from users";
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Populate the table with user scores
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("percentage");
                userScoresTableModel.addRow(new Object[]{username, score});
            }

            // Close resources
            resultSet.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fetchquestions() {
        try {
            // Establish a database connection
            String url = "jdbc:postgresql://localhost:5432/quizapp";
            String dbUsername = "postgres";
            String dbPassword = "suyash123";
            Connection c = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Execute a query to fetch user scores
            String query = "SELECT * from questions";
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Populate the table with user scores
            while (resultSet.next()) {
                String ques = resultSet.getString("question");
                int ans = resultSet.getInt("correctanswer");
                String op1 = resultSet.getString("option1");
                String op2 = resultSet.getString("option2");
                String op3 = resultSet.getString("option3");
                String op4 = resultSet.getString("option4");


                
                questionsTableModel.addRow(new Object[]{ques, op1, op2,op3 ,op4,ans });
            }

            // Close resources
            resultSet.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
