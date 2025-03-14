import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatsFileTest {
    private static final int [] BIN_EDGES = {1, 2, 4, 6, 8, 10, 12, 14};

    //Instantiating statsfiles with different populated statsMaps
    private StatsFileDouble getSFD1(){
        //using dependency injection
        String[][] fakeData = {{"2025-02-24T20:10:29.110278500","3"}, {"2025-02-24T20:15:28.750793100","2"}};
        LocalDateTime today = LocalDateTime.parse("2025-02-24T20:10:29.110278500");
        StatsFileDouble sfd = new StatsFileDouble();
        LocalDateTime limit = today.minusDays(30);
        sfd.readCSV(limit, fakeData);
        return sfd;
    }

    private StatsFileDouble getSFD2(){
        String[][] fakeData = {{"2025-02-24T20:10:29.110278500","3"}, {"2025-02-24T20:15:28.750793100","2"}, {"2025-01-10T20:15:28.750793100","2"}, {"2025-07-10T20:15:28.750793100","2"}};
        LocalDateTime today = LocalDateTime.parse("2025-02-24T20:10:29.110278500");
        StatsFileDouble sfd = new StatsFileDouble();
        LocalDateTime limit = today.minusDays(30);
        sfd.readCSV(limit, fakeData);
        return sfd;
    }

    private StatsFileDouble getSFD3(){
        String[][] fakeData = {{"2025-02-24T20:10:29.110278500","3"}, {"2025-02-24T20:15:28.750793100","2"}, {"2025-02-10T20:15:28.750793100","4"}, {"2025-02-10T20:15:28.750793100","5"}};
        LocalDateTime today = LocalDateTime.parse("2025-02-24T20:10:29.110278500");
        StatsFileDouble sfd = new StatsFileDouble();
        LocalDateTime limit = today.minusDays(30);
        sfd.readCSV(limit, fakeData);
        return sfd;
    }

    private StatsFileDouble getSFD4(){
        String[][] fakeData = {{"2025-02-24T20:10:29.110278500","3"}, {"2025-02-24T20:15:28.750793100","2"}, {"2025-02-10T20:15:28.750793100","1000"}, {"2025-02-10T20:15:28.750793100","5"}};
        LocalDateTime today = LocalDateTime.parse("2025-02-24T20:10:29.110278500");
        StatsFileDouble sfd = new StatsFileDouble();
        LocalDateTime limit = today.minusDays(30);
        sfd.readCSV(limit, fakeData);
        return sfd;
    }

    @Test
    public void testReadLine(){
        StatsFileDouble sfd = getSFD1();
        assertEquals(2, sfd.getStatsLength());
    }

    @Test
    public void testReadLineWithNonIncludes(){
        StatsFileDouble sfd = getSFD2();
        assertEquals(2, sfd.getStatsLength());
    }

    @Test
    public void testMaxNumGuesses(){
        StatsFileDouble sfd = getSFD1();
        assertEquals(3, sfd.maxNumGuesses());
    }

    @Test
    public void testMaxNumGuessesEmpty(){
        StatsFileDouble sfd = new StatsFileDouble();
        assertEquals(0, sfd.maxNumGuesses());
    }

    @Test
    public void testNumGames(){
        StatsFileDouble sfd = getSFD1();
        assertEquals(1, sfd.numGames(3));
    }

    @Test
    public void testNumGamesWrong(){
        StatsFileDouble sfd = getSFD1();
        assertEquals(0, sfd.numGames(7));
    }

    @Test
    public void testNumBins(){
        StatsFileDouble sfd = getSFD1();
        assertEquals(2, sfd.numGamesInBounds(2, 3));
    }

    @Test
    public void testNumBinsNegativeBind(){
        StatsFileDouble sfd = getSFD1();
        assertEquals(0, sfd.numGamesInBounds(3, 0));
    }

    @Test
    public void testNumBinsEqual(){
        StatsFileDouble sfd = getSFD1();
        assertEquals(1, sfd.numGamesInBounds(3, 3));
    }

    @Test
    public void createBins(){
        StatsFileDouble sfd = getSFD1();
        int[] bins = sfd.constructBinEdgesArray(BIN_EDGES);
        assertEquals(2, bins[1]);
    }

    //Catches bug where boundaries are included in both bins
    //Guesses are {2, 3, 4, 5}
    @Test
    public void createBinsOnUpperEdge(){
        StatsFileDouble sfd = getSFD3();
        int[] bins = sfd.constructBinEdgesArray(BIN_EDGES);
        assertEquals(2, bins[1]);
        assertEquals(2, bins[2]);
    }

    @Test
    public void createBinsMaxBin(){
        StatsFileDouble sfd = getSFD4();
        int[] bins = sfd.constructBinEdgesArray(BIN_EDGES);
        assertEquals(2, bins[1]);
        assertEquals(1, bins[2]);
        assertEquals(1, bins[7]);
    }

    @Test
    public void testBadFileData(){
        String[][] fakeData = {{"2025-24T20:10:29.110278500","3"}, {"2025-02-24T20:15:28.750793100","2"}};
        LocalDateTime today = LocalDateTime.parse("2025-02-24T20:10:29.110278500");
        StatsFileDouble sfd = new StatsFileDouble();
        LocalDateTime limit = today.minusDays(30);
        assertThrows(DateTimeParseException.class, () -> sfd.readCSV(limit, fakeData));
    }

    @Test
    public void testNumberFormatException(){
        String[][] fakeData = {{"2025-02-24T20:10:29.110278500","2025-24T20:10:29.110278500"}, {"2025-02-24T20:15:28.750793100","2"}};
        LocalDateTime today = LocalDateTime.parse("2025-02-24T20:10:29.110278500");
        StatsFileDouble sfd = new StatsFileDouble();
        LocalDateTime limit = today.minusDays(30);
        assertThrows(NumberFormatException.class, () -> sfd.readCSV(limit, fakeData));
    }

}
