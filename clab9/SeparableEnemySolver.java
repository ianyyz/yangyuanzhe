import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SeparableEnemySolver {

    Graph g;

    /**
     * Creates a SeparableEnemySolver for a file with name filename. Enemy
     * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
     */
    SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
        this.g = graphFromFile(filename);
    }

    /** Alterntive constructor that requires a Graph object. */
    SeparableEnemySolver(Graph g) {
        this.g = g;
    }

    /**
     * Returns true if input is separable, false otherwise.
     */
    public boolean isSeparable() {
        Set<String> Group1 = new HashSet<>();
        Set<String> Group2 = new HashSet<>();
        int belongToSet = 0; // initialize a int to indicate which table its neighbor should belong to
        for (String label: g.labels()){ // loop over all the labels a graph can have
            if(Group1.isEmpty() && Group2.isEmpty()){ // if both set is empty, put the first label in set1
                Group1.add(label);
                belongToSet = 1;
            }else if(Group1.contains(label) && !Group2.contains(label)){ // if the label is in set1 but not set2
                belongToSet = 1;
            }else if(!Group1.contains(label) && Group2.contains(label)){ // if the label is in set2 but not set1, we do not put the same label again, we only update the belongToSet
                belongToSet = 2;
            }else if(!Group1.contains(label) && !Group2.contains(label)){
                Group1.add(label);
                belongToSet = 1;
            }
            for (String neighbors: g.neighbors(label)){
                if (belongToSet == 1){
                    Group2.add(neighbors);
                }else if(belongToSet == 2){
                    Group1.add(neighbors);
                }
                if (Group2.contains(neighbors) && Group1.contains(neighbors)){
                    return false;
                }
            }

        }
        return true;
    }


    /* HELPERS FOR READING IN CSV FILES. */

    /**
     * Creates graph from filename. File should be comma-separated. The first line
     * contains comma-separated names of all people. Subsequent lines each have two
     * comma-separated names of enemy pairs.
     */
    private Graph graphFromFile(String filename) throws FileNotFoundException {
        List<List<String>> lines = readCSV(filename);
        Graph input = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                for (String name : lines.get(i)) {
                    input.addNode(name);
                }
                continue;
            }
            assert(lines.get(i).size() == 2);
            input.connect(lines.get(i).get(0), lines.get(i).get(1));
        }
        return input;
    }

    /**
     * Reads an entire CSV and returns a List of Lists. Each inner
     * List represents a line of the CSV with each comma-seperated
     * value as an entry. Assumes CSV file does not contain commas
     * except as separators.
     * Returns null if invalid filename.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        return records;
    }

    /**
     * Reads one line of a CSV.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next().trim());
        }
        return values;
    }

    /* END HELPERS  FOR READING IN CSV FILES. */

}
