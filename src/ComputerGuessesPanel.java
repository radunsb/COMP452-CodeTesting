import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * UI screen for when the computer is guessing a number
 *
 * Displays the computer's guesses and processes human's answers
 * Tracks the computer's guesses
 *
 *
 */
public class ComputerGuessesPanel extends JPanel {

    private int numGuesses;
    private int lastGuess;

    // upperBound and lowerBound track the computer's knowledge about the correct number
    // They are updated after each guess is made
    private int upperBound; // correct number is <= upperBound
    private int lowerBound; // correct number is >= lowerBound

    private JLabel guessMessage;

    public String getGuessMessage(){
        return guessMessage.getText();
    }


    public void LessListener(){
        JButton lowerBtn = new JButton("Lower");
        lowerBtn.addActionListener(e -> {
            respondToWrongGuess(false);
        });
        this.add(lowerBtn);
        lowerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0,10)));
    }

    public void EqualListener(JPanel cardsPanel, Consumer<GameResult> gameFinishedCallback) {
        JButton correctBtn = new JButton("Equal");
        correctBtn.addActionListener(e -> {
            guessMessage.setText("I guess ___.");
            // Send the result of the finished game to the callback
            GameResult result = new GameResult(false, lastGuess, numGuesses);
            gameFinishedCallback.accept(result);

            CardLayout cardLayout = (CardLayout) cardsPanel.getLayout();
            cardLayout.show(cardsPanel, ScreenID.GAME_OVER.name());
        });
        this.add(correctBtn);
        correctBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0,10)));
    }

    public void MoreListener(){
        JButton higherBtn = new JButton("Higher");
        higherBtn.addActionListener(e -> {
            respondToWrongGuess(true);
        });
        this.add(higherBtn);
        higherBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void respondToWrongGuess(boolean greater){
        if (greater) lowerBound = Math.max(lowerBound, lastGuess + 1);
        else upperBound = Math.min(upperBound, lastGuess);
        numGuesses += 1;
        makeGuess();
    }

    public int respondToWrongGuess(boolean greater, int lastGuess){
        if (greater) lowerBound = Math.max(lowerBound, lastGuess + 1);
        else upperBound = Math.min(upperBound, lastGuess);
        numGuesses += 1;
        return makeGuess();
    }

    public int makeGuess(){
        lastGuess = (lowerBound + upperBound + 1) / 2;
        guessMessage.setText("I guess " + lastGuess + ".");
        return lastGuess;
    }

    public int getNumGuesses(){
        return numGuesses;
    }

    public ComputerGuessesPanel(JPanel cardsPanel, Consumer<GameResult> gameFinishedCallback){
        numGuesses = 0;
        upperBound = 1000;
        lowerBound = 1;

        guessMessage = new JLabel("I guess ___.");
        guessMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(guessMessage);
        guessMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createRigidArea(new Dimension(0, 40)));

        JLabel prompt = new JLabel("Your number is...");
        this.add(prompt);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0,10)));

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        LessListener();

        EqualListener(cardsPanel, gameFinishedCallback);

        MoreListener();


        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e) {
                makeGuess();
            }
        });
    }

}
