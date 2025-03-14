import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

public class StatsFileDouble extends StatsFile {

    private SortedMap<Integer, Integer> statsMap;

    public StatsFileDouble(){
        statsMap = new TreeMap<>();
    }
    public void readCSV(LocalDateTime limit, String[][] fakeData){
        for(String[] values : fakeData){
            super.readLine(values, limit, this);
        }
    }

    public void setStatsMap(SortedMap<Integer, Integer> toSet){
        statsMap.putAll(toSet);
    }

    public int getStatsLength(){
        return statsMap.size();
    }

    @Override
    public int maxNumGuesses(){
        return (statsMap.isEmpty() ? 0 : statsMap.lastKey());
    }

    @Override
    public int numGames(int numGuesses) {
        return statsMap.getOrDefault(numGuesses, 0);
    }

}
