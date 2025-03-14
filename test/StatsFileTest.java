import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatsFileTest {

    private StatsFileDouble getSFD1(){
        //using dependency injection
        String[][] fakeData = {{"2025-02-24T20:10:29.110278500","3"}, {"2025-02-24T20:15:28.750793100","2"}};
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
