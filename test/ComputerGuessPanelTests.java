import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.function.Consumer;
public class ComputerGuessPanelTests {
    JPanel cardsPanel;
    Consumer<GameResult> gameFinishedCallback;

    @Test
    public void getGuessMessage(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        assertEquals("I guess 501", CGP.getGuessMessage());
    }

    // Will fail as the program is unable to test for these test cases
    @Test
    public void TestComputerHighEndGuess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        for (int i = 0; i<9; i++) {
            CGP.greaterSim();
        }
        assertEquals("I guess 999.", CGP.getGuessMessage());
        CGP.greaterSim();
        assertEquals("I guess 1300", CGP.getGuessMessage());
    }

    // Will fail as the program is unable to test for these test cases
    @Test
    public void TestComputerLowEndGuess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        for (int i = 0; i<9; i++) {
            CGP.lesserSim();
        }
        assertEquals("I guess 2.", CGP.getGuessMessage());
        CGP.lesserSim();
        assertEquals("I guess -50", CGP.getGuessMessage());
    }

    // Will fail as the program is unable to test for these test cases
    @Test
    public void TestComputer250Guess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        CGP.lesserSim();
        CGP.lesserSim();
        for (int i = 0; i<6; i++) {
            CGP.greaterSim();
        }
        assertEquals("I guess 250.", CGP.getGuessMessage());
    }

    // Will fail as the program is unable to test for these test cases
    @Test
    public void TestComputer500Guess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        CGP.lesserSim();
        for (int i = 0; i<7; i++) {
            CGP.greaterSim();
        }
        assertEquals("I guess 500.", CGP.getGuessMessage());
    }

    // Will fail as the program is unable to test for these test cases
    @Test
    public void TestComputer750Guess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        CGP.greaterSim();
        CGP.lesserSim();
        for (int i = 0; i<7; i++) {
            CGP.greaterSim();
        }
        assertEquals("I guess 750.", CGP.getGuessMessage());
    }

    @Test
    public void TestComputerCorrectGuess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        assertEquals("I guess 501.", CGP.getGuessMessage());
    }

    @Test
    public void TestComputerLowerGuess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        CGP.lesserSim();
        assertEquals("I guess 251.", CGP.getGuessMessage());
    }

    @Test
    public void TestComputerHigherGuess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        CGP.greaterSim();
        assertEquals("I guess 751.", CGP.getGuessMessage());
    }

    @Test
    public void TestComputerScoreUpdates(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        String csvFile = "guess-the-number-stats.csv";
        int rowCount = 0;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        CGP.equalSim(cardsPanel, gameFinishedCallback);

        int NewRowCount = 0;
        String NewLine;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((NewLine = br.readLine()) != null) {
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(rowCount, NewRowCount);

    }
}
