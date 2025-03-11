import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HumanGuessesGameTest {
    @Test
    public void testMakeGuess(){
        HumanGuessesGame hg = new HumanGuessesGame();
        hg.makeGuess(500);
        assertEquals(hg.getNumGuesses(), 1);
    }
    @Test
    public void testCorrectGuess(){
        HumanGuessesGame hg = new HumanGuessesGame(500);
        GuessResult result = hg.makeGuess(500);
        assertEquals(result, GuessResult.CORRECT);
    }
    @Test
    public void testHighGuess(){
        HumanGuessesGame hg = new HumanGuessesGame(500);
        GuessResult result = hg.makeGuess(501);
        assertEquals(result, GuessResult.HIGH);
    }
    @Test
    public void testLowGuess(){
        HumanGuessesGame hg = new HumanGuessesGame(500);
        GuessResult result = hg.makeGuess(499);
        assertEquals(result, GuessResult.LOW);
    }
    //This fails, because isDone never actually gets changed and isn't used for
    //everything, making it useless code that should be removed
    @Test
    public void isDoneUpdates(){
        HumanGuessesGame hg = new HumanGuessesGame(500);
        hg.makeGuess(500);
        assertTrue(hg.isDone());
    }
}
