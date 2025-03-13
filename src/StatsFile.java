import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * File-backed implementation of GameStats
 *
 * Returns the number of games *within the last 30 days* where the person took a given number of guesses
 */
public class StatsFile extends GameStats {
    public static final String FILENAME = "guess-the-number-stats.csv";


    // maps the number of guesses required to the number of games within
    // the past 30 days where the person took that many guesses
    private SortedMap<Integer, Integer> statsMap;

    public StatsFile(){
        statsMap = new TreeMap<>();
        LocalDateTime limit = LocalDateTime.now().minusDays(30);
        readCSV(limit);
    }

    public void readCSV(LocalDateTime limit){
        try (CSVReader csvReader = new CSVReader(new FileReader(FILENAME))) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                readLine(values, limit);
            }
        } catch (CsvValidationException e) {
            // NOTE: In a full implementation, we would log this error and alert the user
            // NOTE: For this project, you do not need unit tests for handling this exception.
        } catch (IOException e) {
            // NOTE: In a full implementation, we would log this error and alert the user
            // NOTE: For this project, you do not need unit tests for handling this exception.
        }
    }

    public void readLine(String[] csvLine, LocalDateTime limit){
        // values should have the date and the number of guesses as the two fields
        try {
            LocalDateTime timestamp = LocalDateTime.parse(csvLine[0]);
            int numGuesses = Integer.parseInt(csvLine[1]);
            if (timestamp.isAfter(limit)) {
                statsMap.put(numGuesses, 1 + statsMap.getOrDefault(numGuesses, 0));
            }
        }
        catch(NumberFormatException nfe){
            // NOTE: In a full implementation, we would log this error and possibly alert the user
            throw nfe;
        }
        catch(DateTimeParseException dtpe){
            // NOTE: In a full implementation, we would log this error and possibly alert the user
            throw dtpe;
        }
    }

    public void readLine(String[] csvLine, LocalDateTime limit, StatsFileDouble child){
        this.statsMap = new TreeMap<>();
        readLine(csvLine, limit);
        child.setStatsMap(this.statsMap);
    }

    @Override
    public int numGames(int numGuesses) {
        return statsMap.getOrDefault(numGuesses, 0);
    }

    @Override
    public int maxNumGuesses(){
        return (statsMap.isEmpty() ? 0 : statsMap.lastKey());
    }

    public int numGamesInBounds(int lowBound, int highBound){
        int numGames = 0;
        for(int i = lowBound; i <= highBound; i++){
            numGames += numGames(i);
        }
        return numGames;
    }

}
