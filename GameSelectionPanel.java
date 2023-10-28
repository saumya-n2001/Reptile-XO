


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameSelectionPanel extends JPanel {


    public GameSelectionPanel() {
        setLayout(new GridLayout(8, 8));
        setBackground(Color.white);


        JButton snakeGameButton = new JButton("Snake Game");
        snakeGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSnakeGame();
            }
        });


        JButton ticTacToeButton = new JButton("Tic-Tac-Toe");
        ticTacToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTicTacToe();
            }
        });


        add(snakeGameButton);
        add(ticTacToeButton);
    }


    private void showSnakeGame() {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new SnakeGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void showTicTacToe() {
        new TicTacToeGUI();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Selection");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new GameSelectionPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}


class SnakeGame extends JPanel implements ActionListener {


    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 600;
    private final int UNIT_SIZE = 25;
    private final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    private final int DELAY = 100;
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int bodyParts = 6;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;


    public SnakeGame() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startGame();
    }


    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g) {
        if (running) {
            // Draw the snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }


            // Draw the apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);


            // Draw the score
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + applesEaten, 10, 20);
        } else {
            showGameOverScreen(g);
        }
    }


    public void newApple() {
        appleX = (int) (Math.random() * (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = (int) (Math.random() * (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }


    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }


        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }


    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }


    public void checkCollisions() {
        // Check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }


        // Check if head touches left border
        if (x[0] < 0) {
            running = false;
        }


        // Check if head touches right border
        if (x[0] >= SCREEN_WIDTH) {
            running = false;
        }


        // Check if head touches top border
        if (y[0] < 0) {
            running = false;
        }


        // Check if head touches bottom border
        if (y[0] >= SCREEN_HEIGHT) {
            running = false;
        }


        if (!running) {
            timer.stop();
            int option = JOptionPane.showOptionDialog(
                    this,
                    "Game Over! Score: " + applesEaten + "\nDo you want to restart?",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Restart", "Quit"},
                    "Restart"
            );


            if (option == JOptionPane.YES_OPTION) {
                restartGame();
            } else {
                System.exit(0);
            }
        }
    }


    private void restartGame() {
        int confirmOption = JOptionPane.showOptionDialog(
                this,
                "Are you sure you want to restart the game?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Yes", "No"},
                "Yes"
        );
    
        if (confirmOption == JOptionPane.YES_OPTION) {
            bodyParts = 6;
            applesEaten = 0;
            direction = 'R';
            running = true;
            x[0] = 0;
            y[0] = 0;
            newApple();
            
    
            String[] questions = {
                "What is the correct way to declare a constant variable in Java?",
                "What will be the output of the following code?\n\nint x = 5;\nSystem.out.println(x++ + ++x);",
                "Which of the following is NOT a primitive data type in Java?",
                "What is the purpose of the final keyword in Java?",
                "What is the output of the following code?\n\nString str1 = \"Hello\";\nString str2 = new String(\"Hello\");\nSystem.out.println(str1 == str2);"
            };
    
            String[][] options = {
                {"a) final int constantVar = 10;", "b) const int constantVar = 10;", "c) static int constantVar = 10;", "d) final static int constantVar = 10;"},
                {"a) 10", "b) 11", "c) 12", "d) 13"},
                {"a) byte", "b) int", "c) float", "d) string"},
                {"a) It is used to prevent a class from being inherited.", "b) It is used to prevent a method from being overridden.", "c) It is used to prevent a variable from being modified.", "d) All of the above."},
                {"a) true", "b) false", "c) Compile error", "d) Runtime error"}
            };
    
            int numQuestions = 5;
            int correctAnswers = 0;
    
            for (int i = 0; i < numQuestions; i++) {
                int selectedOption = JOptionPane.showOptionDialog(
                        this,
                        questions[i],
                        "Java MCQ",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options[i],
                        options[i][0]
                );
                
    
                // Check if the selected option is correct
                if (selectedOption == getCorrectAnswerIndex(i)) {
                    correctAnswers++;
                   
                }
            }
            timer.restart();
    
            JOptionPane.showMessageDialog(
                    this,
                    "You answered " + correctAnswers + " out of " + numQuestions + " questions correctly.",
                    "Quiz Results",
                    JOptionPane.INFORMATION_MESSAGE
                    
            );
        } else {
            System.exit(0);
            
        }
    }
    
    // Helper method to get the index of the correct answer for each question
    private int getCorrectAnswerIndex(int questionIndex) {
        // Add your logic here to return the correct index based on the question
        switch (questionIndex) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 3;
            case 4:
                return 1;
            default:
                return 0;
        }
    }
    
    

    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }


    private class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if (!running) {
                        restartGame();
                    }
                    break;
            }
        }
    }


    private void showGameOverScreen(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Over", SCREEN_WIDTH / 2 - 140, SCREEN_HEIGHT / 2 - 50);


        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Press Enter to Restart", SCREEN_WIDTH / 2 - 105, SCREEN_HEIGHT / 2 + 10);
    }
}


class TicTacToeGUI extends JFrame implements ActionListener {


    private final int BOARD_SIZE = 3;
    private final JButton[][] buttons;
    private boolean player1Turn = true;
    private int moves;


    public TicTacToeGUI() {
        setTitle("Tic-Tac-Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 80));
                buttons[row][col].addActionListener(this);
                panel.add(buttons[row][col]);
            }
        }


        add(panel);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().isEmpty()) {
            if (player1Turn) {
                button.setText("X");
            } else {
                button.setText("O");
            }
            moves++;
            if (checkWin()) {
                String winner = player1Turn ? "Player 1 (X)" : "Player 2 (O)";
                JOptionPane.showMessageDialog(this, "Congratulations! " + winner + " wins!");
                restartGame();
            } else if (moves == BOARD_SIZE * BOARD_SIZE) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                restartGame();
            } else {
                player1Turn = !player1Turn;
            }
        }
    }


    private boolean checkWin() {
        String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = buttons[row][col].getText();
            }
        }


        // Check rows
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row][0].equals(board[row][1]) && board[row][0].equals(board[row][2]) && !board[row][0].isEmpty()) {
                return true;
            }
        }


        // Check columns
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[0][col].equals(board[1][col]) && board[0][col].equals(board[2][col]) && !board[0][col].isEmpty()) {
                return true;
            }
        }


        // Check diagonals
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].isEmpty()) {
            return true;
        }


        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].isEmpty()) {
            return true;
        }


        return false;
    }


    private void restartGame() {
        int confirmOption = JOptionPane.showOptionDialog(
                this,
                "Are you sure you want to restart the game?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Yes", "No"},
                "Yes"
        );
    
        if (confirmOption == JOptionPane.YES_OPTION) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    buttons[row][col].setText("");
                }
            }
            player1Turn = true;
            moves = 0;
            
            if (confirmOption == JOptionPane.YES_OPTION) {
                String[] questions = {
                    "What is the correct way to declare a constant variable in Java?",
                    "What will be the output of the following code?\n\nint x = 5;\nSystem.out.println(x++ + ++x);",
                    "Which of the following is NOT a primitive data type in Java?",
                    "What is the purpose of the final keyword in Java?",
                    "What is the output of the following code?\n\nString str1 = \"Hello\";\nString str2 = new String(\"Hello\");\nSystem.out.println(str1 == str2);"
                };
    
                String[][] options = {
                    {"a) final int constantVar = 10;", "b) const int constantVar = 10;", "c) static int constantVar = 10;", "d) final static int constantVar = 10;"},
                    {"a) 10", "b) 11", "c) 12", "d) 13"},
                    {"a) byte", "b) int", "c) float", "d) string"},
                    {"a) It is used to prevent a class from being inherited.", "b) It is used to prevent a method from being overridden.", "c) It is used to prevent a variable from being modified.", "d) All of the above."},
                    {"a) true", "b) false", "c) Compile error", "d) Runtime error"}
                };
    
                int numQuestions = 5;
                int correctAnswers = 0;
    
                for (int i = 0; i < numQuestions; i++) {
                    int selectedOption = JOptionPane.showOptionDialog(
                            this,
                            questions[i],
                            "Java MCQ",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options[i],
                            options[i][0]
                    );
    
                    // Check if the selected option is correct
                    if (selectedOption == getCorrectAnswerIndex(i)) {
                        correctAnswers++;
                    }
                }
    
                JOptionPane.showMessageDialog(
                        this,
                        "You answered " + correctAnswers + " out of " + numQuestions + " questions correctly.",
                        "Quiz Results",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } else {
            System.exit(0); // Quit the game
        }
    }
    
    // Helper method to get the index of the correct answer for each question
    private int getCorrectAnswerIndex(int questionIndex) {
        // Add your logic here to return the correct index based on the question
        switch (questionIndex) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 3;
            case 4:
                return 1;
            default:
                return 0;
        }
    }
    
   
   
}



