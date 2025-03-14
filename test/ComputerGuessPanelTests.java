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
    public void getDefaultMessage(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        assertEquals("I guess ___.", CGP.getGuessMessage());
    }

    @Test
    public void getFirstGuessMessage(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        CGP.makeGuess();
        assertEquals("I guess 501.", CGP.getGuessMessage());
    }

    // Brayden said this should fail but I don't see how it would
    // There might have been a bug we accidentally removed
    @Test
    public void TestComputerHighEndGuess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        for (int i = 0; i<9; i++) {
            CGP.respondToWrongGuess(true);
        }
        assertEquals("I guess 1000.", CGP.getGuessMessage());
    }

    @Test
    public void TestComputerLowEndGuess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        for (int i = 0; i<9; i++) {
            CGP.respondToWrongGuess(false);
        }
        assertEquals("I guess 1.", CGP.getGuessMessage());
    }

    // Will fail as the program is unable to test for these test cases
    @Test
    public void TestComputer250Guess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        int myNum = 250;
        int guess = 501;
        for (int i = 0; i<50; i++) {
            if(guess > myNum){
                guess = CGP.respondToWrongGuess(false, guess);
            }
            else if(guess < myNum){
                guess = CGP.respondToWrongGuess(true, guess);
            }
            else break;
        }
        assertEquals("I guess 250.", CGP.getGuessMessage());
    }

    // Will fail as the program is unable to test for these test cases
    @Test
    public void TestComputer500Guess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        int myNum = 500;
        int guess = 501;
        for (int i = 0; i<50; i++) {
            if(guess > myNum){
                guess = CGP.respondToWrongGuess(false, guess);
            }
            else if(guess < myNum){
                guess = CGP.respondToWrongGuess(true, guess);
            }
            else break;
        }
        assertEquals("I guess 500.", CGP.getGuessMessage());
    }

    // Will fail as the program is unable to test for these test cases
    @Test
    public void TestComputer750Guess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        int myNum = 750;
        int guess = 501;
        for (int i = 0; i<50; i++) {
            if(guess > myNum){
                guess = CGP.respondToWrongGuess(false, guess);
            }
            else if(guess < myNum){
                guess = CGP.respondToWrongGuess(true, guess);
            }
            else break;
        }
        assertEquals("I guess 750.", CGP.getGuessMessage());
    }

    @Test
    public void TestComputerCorrectGuess(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        CGP.makeGuess();
        assertEquals("I guess 501.", CGP.getGuessMessage());
    }

    //These 2 shows that the numGuesses will be 1 less than it should be
    //Not sure if this was a bug to begin with or if our refactoring caused it
    @Test
    public void getsCorrectNumGuesses(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        CGP.makeGuess();
        assertEquals("I guess 501.", CGP.getGuessMessage());
        assertEquals(1, CGP.getNumGuesses());
    }

    @Test
    public void getsCorrectNumGuesses2(){
        ComputerGuessesPanel CGP = new ComputerGuessesPanel(cardsPanel, gameFinishedCallback);
        CGP.makeGuess();
        CGP.respondToWrongGuess(false,  501);
        assertEquals("I guess 251.", CGP.getGuessMessage());
        assertEquals(2, CGP.getNumGuesses());
    }
}
