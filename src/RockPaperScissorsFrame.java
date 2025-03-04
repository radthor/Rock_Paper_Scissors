import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
/**
 * Rock Paper Scissors Game implementation with GUI.
 * This class creates the main game interface and handles game logic.
 *
 * @author Andrew Joslin
 * @version 1.0
 */
public class RockPaperScissorsFrame extends JFrame {

    // Panels
    JPanel mainPnl;
    JPanel titlePnl;
    JPanel gamePnl;
    JPanel statsPnl;
    JPanel resultsPnl;

    // Title
    JLabel titleLbl;

    // Game
    JButton rockBtn;
    JButton paperBtn;
    JButton scissorsBtn;
    JButton quitBtn;

    // Stats
    JLabel playerWinsLbl;
    JTextField playerWinsTF;
    JLabel computerWinsLbl;
    JTextField computerWinsTF;
    JLabel tiesLbl;
    JTextField tiesTF;

    // Results
    JTextArea resultsTA;
    JScrollPane scroller;

    // Game
    int playerWins;
    int computerWins;
    int ties;

    // Random for computer choice
    Random rnd;

    public RockPaperScissorsFrame() {

        JFrame frame = new JFrame();
        frame.setTitle("Rock Paper Scissors Game");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = (int)(screenSize.width * 0.75);
        int height = (int)(screenSize.height * 0.75);
        setSize(width, height);
        frame.setLocationRelativeTo(null); // Center

        playerWins = 0;
        computerWins = 0;
        ties = 0;
        rnd = new Random();

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        createTitlePanel();
        createGamePanel();
        createStatsPanel();
        createResultsPanel();

        mainPnl.add(titlePnl, BorderLayout.NORTH);
        mainPnl.add(gamePnl, BorderLayout.WEST);
        mainPnl.add(statsPnl, BorderLayout.EAST);
        mainPnl.add(resultsPnl, BorderLayout.CENTER);

        add(mainPnl);
        setVisible(true);
    }
    /**
     * Creates the title panel with game title
     */
    private void createTitlePanel() {
        titlePnl = new JPanel();
        titleLbl = new JLabel("Rock Paper Scissors Game");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 24));
        titlePnl.add(titleLbl);
    }
    /**
     * Creates the game panel with Rock, Paper, Scissors and Quit buttons
     */
    private void createGamePanel() {
        gamePnl = new JPanel();
        gamePnl.setLayout(new GridLayout(4, 1, 10, 10));
        gamePnl.setBorder(BorderFactory.createTitledBorder("Choose your move:"));

        // Create buttons with standard sized icons
        ImageIcon originalIcon = new ImageIcon("src/rock.png");
        Image originalImg = originalIcon.getImage();
        Image resizedImg = originalImg.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        rockBtn = new JButton("Rock", new ImageIcon(resizedImg));

        ImageIcon originalIcon1 = new ImageIcon("src/paper.png");
        Image originalImg1 = originalIcon1.getImage();
        Image resizedImg1 = originalImg1.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        paperBtn = new JButton("Paper", new ImageIcon(resizedImg1));

        ImageIcon originalIcon2 = new ImageIcon("src/scissors.png");
        Image originalImg2 = originalIcon2.getImage();
        Image resizedImg2 = originalImg2.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        scissorsBtn = new JButton("Scissors", new ImageIcon(resizedImg2));

        quitBtn = new JButton("Quit");

        // Button Position
        rockBtn.setVerticalTextPosition(JButton.BOTTOM);
        rockBtn.setHorizontalTextPosition(JButton.CENTER);
        paperBtn.setVerticalTextPosition(JButton.BOTTOM);
        paperBtn.setHorizontalTextPosition(JButton.CENTER);
        scissorsBtn.setVerticalTextPosition(JButton.BOTTOM);
        scissorsBtn.setHorizontalTextPosition(JButton.CENTER);

        rockBtn.addActionListener((ActionEvent ae) -> playGame("Rock"));
        paperBtn.addActionListener((ActionEvent ae) -> playGame("Paper"));
        scissorsBtn.addActionListener((ActionEvent ae) -> playGame("Scissors"));
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));

        gamePnl.add(rockBtn);
        gamePnl.add(paperBtn);
        gamePnl.add(scissorsBtn);
        gamePnl.add(quitBtn);
    }
    /**
     * Creates the stats panel with labels and text fields for wins/losses/ties
     */
    private void createStatsPanel() {
        statsPnl = new JPanel();
        statsPnl.setLayout(new GridLayout(3, 2, 10, 10));
        statsPnl.setBorder(BorderFactory.createTitledBorder("Statistics:"));

        // Create labels and text fields
        playerWinsLbl = new JLabel("Player Wins:");
        playerWinsTF = new JTextField("0", 10);
        playerWinsTF.setEditable(false);

        computerWinsLbl = new JLabel("Computer Wins:");
        computerWinsTF = new JTextField("0", 10);
        computerWinsTF.setEditable(false);

        tiesLbl = new JLabel("Ties:");
        tiesTF = new JTextField("0", 10);
        tiesTF.setEditable(false);

        statsPnl.add(playerWinsLbl);
        statsPnl.add(playerWinsTF);
        statsPnl.add(computerWinsLbl);
        statsPnl.add(computerWinsTF);
        statsPnl.add(tiesLbl);
        statsPnl.add(tiesTF);
    }
    /**
     * Creates the results panel with a text area and scroll pane for game results
     */
    private void createResultsPanel() {
        resultsPnl = new JPanel();
        resultsPnl.setBorder(BorderFactory.createTitledBorder("Game Results:"));

        resultsTA = new JTextArea(15, 30);
        resultsTA.setEditable(false);
        scroller = new JScrollPane(resultsTA);

        resultsPnl.add(scroller);
    }
    /**
     * Handles game logic when player makes a choice
     * @param playerChoice The player's choice (Rock, Paper, or Scissors)
     */
    private void playGame(String playerChoice) {

        String[] choices = {"Rock", "Paper", "Scissors"};
        String computerChoice = choices[rnd.nextInt(choices.length)];

        String result;

        if (playerChoice.equals(computerChoice)) {
            result = playerChoice + " vs " + computerChoice + " (It's a tie!)";
            ties++;
        } else if ((playerChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                (playerChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                (playerChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            result = getWinMessage(playerChoice, computerChoice, true);
            playerWins++;
        } else {
            result = getWinMessage(computerChoice, playerChoice, false);
            computerWins++;
        }

        resultsTA.append(result + "\n");

        // Update stats
        playerWinsTF.setText(Integer.toString(playerWins));
        computerWinsTF.setText(Integer.toString(computerWins));
        tiesTF.setText(Integer.toString(ties));
    }
    /**
     * Generates a formatted win message based on the winning and losing choices.
     * Creates a message string that describes the winning action and identifies the winner.
     *
     * @param winningChoice The gesture that won the round
     * @param losingChoice The gesture that lost the round
     * @param isPlayerWin Whether the player was the winner (true) or computer (false)
     * @return A formatted string describing the win condition
     */
    private String getWinMessage(String winningChoice, String losingChoice, boolean isPlayerWin) {
        String actionMsg;

        if (winningChoice.equals("Rock") && losingChoice.equals("Scissors")) {
            actionMsg = "Rock breaks Scissors";
        } else if (winningChoice.equals("Paper") && losingChoice.equals("Rock")) {
            actionMsg = "Paper covers Rock";
        } else {
            actionMsg = "Scissors cuts Paper";
        }
        return actionMsg + " (" + (isPlayerWin ? "Player" : "Computer") + " wins)";
    }
}