/**
 * An interface for a data structure, DB API, file reader, etc. that
 * tells us how many games were played that took some number of guesses
 * (e.g., How many games took 8 guesses? --> 17)
 *
 * You can edit this file, but the two abstract methods listed below must remain
 */
public abstract class GameStats {
    /**
     * @return the number of games played that took numGuesses
     */
    public abstract int numGames(int numGuesses);

    /**
     * @return the maximum number of guesses that any game took
     */
    public abstract int maxNumGuesses();

    public abstract int numGamesInBounds(int lowBound, int highBound);

    public abstract int[] constructBinEdgesArray(int[] edges);
}
