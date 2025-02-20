import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Displays statistics about how many guesses the person took during past games
 * Loads data from the file and displays in a JPanel
 *
 * TODO: refactor this class
 */
public class StatsPanel extends JPanel {

    private final JPanel resultsPanel;

    // Stats will display the number of games in each "bin"
    // A bin goes from BIN_EDGES[i] through BIN_EDGES[i+1]-1, inclusive
    private static final int [] BIN_EDGES = {1, 2, 4, 6, 8, 10, 12, 14};
    private ArrayList<JLabel> resultsLabels;

    public StatsPanel(JPanel cardsPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        for(JLabel label : constructLabels()){
            this.add(label);
        }
        this.add(Box.createRigidArea(new Dimension(0,40)));
        resultsLabels = new ArrayList<>();
        resultsPanel = setupResultsPanel();
        this.add(resultsPanel);
        updateResultsPanel();
        this.add(Box.createVerticalGlue());
        this.add(constructQuitButton(cardsPanel));
        this.add(Box.createRigidArea(new Dimension(0,20)));
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e) {
                updateResultsPanel();
            }
        });
    }

    private JButton constructQuitButton(JPanel cardsPanel){
        JButton quit = new JButton("Back to Home");
        quit.addActionListener(e -> {
            // See itemStateChanged in https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/CardLayoutDemoProject/src/layout/CardLayoutDemo.java
            CardLayout cardLayout = (CardLayout) cardsPanel.getLayout();
            cardLayout.show(cardsPanel, ScreenID.HOME.name());
        });
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        return quit;
    }
    private ArrayList<JLabel> constructLabels(){
        ArrayList<JLabel> labels = new ArrayList<>();
        JLabel title = new JLabel("Your Stats");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        labels.add(title);
        JLabel subtitle = new JLabel("(Past 30 Days)");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        labels.add(subtitle);
        return labels;
    }

    private ArrayList<String> constructBinNames(){
        ArrayList<String> names = new ArrayList<>();
        for(int binIndex=0; binIndex<BIN_EDGES.length; binIndex++) {
            String binName;
            if (binIndex == BIN_EDGES.length - 1) {
                // last bin
                binName = BIN_EDGES[binIndex] + " or more";
            } else {
                int upperBound = BIN_EDGES[binIndex + 1] - 1;
                if (upperBound > BIN_EDGES[binIndex]) {
                    binName = BIN_EDGES[binIndex] + "-" + upperBound;
                } else {
                    binName = Integer.toString(BIN_EDGES[binIndex]);
                }
            }
            names.add(binName);
        }
        return names;
    }

    public JPanel setupResultsPanel(){
        JPanel setupPanel = new JPanel();
        setupPanel.setLayout(new GridLayout(0, 2));
        setupPanel.add(new JLabel("Guesses"));
        setupPanel.add(new JLabel("Games"));
        setupPanel.setMinimumSize(new Dimension(120, 120));
        for(String binName : constructBinNames()){
            setupPanel.add(new JLabel(binName));
            JLabel result = new JLabel("--");
            resultsLabels.add(result);
            setupPanel.add(result);
        }
        setupPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return setupPanel;
    }


    private void clearResults(){
        for(JLabel lbl : resultsLabels){
            lbl.setText("--");
        }
    }

    private void updateResultsPanel(){
        clearResults();
        GameStats stats = new StatsFile();
        for(int binIndex=0; binIndex<BIN_EDGES.length; binIndex++){
            final int lowerBound = BIN_EDGES[binIndex];
            int numGames = 0;
            if(binIndex == BIN_EDGES.length-1){
                // last bin
                // Sum all the results from lowerBound on up
                for(int numGuesses=lowerBound; numGuesses<stats.maxNumGuesses(); numGuesses++){
                    numGames += stats.numGames(numGuesses);
                }
            }
            else{
                int upperBound = BIN_EDGES[binIndex+1];
                for(int numGuesses=lowerBound; numGuesses <= upperBound; numGuesses++) {
                    numGames += stats.numGames(numGuesses);
                }
            }
            JLabel resultLabel = resultsLabels.get(binIndex);
            resultLabel.setText(Integer.toString(numGames));
        }
    }
}
