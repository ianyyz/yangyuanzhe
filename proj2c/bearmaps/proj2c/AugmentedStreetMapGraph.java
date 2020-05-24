package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;

import javax.naming.Name;
import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private Map<Point,Node> PointToNode;
    private Map<String,List<Node>> NameToNode;
    private List<Point> points;
    private MyTrieSet tries;
    public Map<Long,Node> IdToNode;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        points = new ArrayList<>();
        PointToNode = new HashMap<>();
        NameToNode = new HashMap<>();
        tries = new MyTrieSet();
        IdToNode = new HashMap<>();
        for (Node n: nodes){
            if(n.name() != null){
                String cleanString = this.cleanString(n.name());
                tries.add(cleanString);
                if (!NameToNode.containsKey(cleanString)) {
                    NameToNode.put(cleanString, new LinkedList<>());
                }
                NameToNode.get(cleanString).add(n);
            }

            if(neighbors(n.id()).size() >0){ // only if the node has some neighbor, we count this into our points.
                Point p = new Point(n.lon(),n.lat());
                PointToNode.put(p,n);
                points.add(p);
                IdToNode.put(n.id(),n);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        WeirdPointSet tree = new WeirdPointSet(points);
        Point nearestPoint = tree.nearest(lon,lat);
        Node nearestNode =  PointToNode.get(nearestPoint);
        return nearestNode.id();
    }

    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> cleanNames = tries.keysWithPrefix(cleanString(prefix));
        List<String> fullNames = new LinkedList<>();
        for (String name: cleanNames) {
            for (Node n: NameToNode.get(name)) {
                if (!fullNames.contains(n.name())) {
                    fullNames.add(n.name());
                }
            }
        }
        return fullNames;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanName = this.cleanString(locationName);
        List<Map<String,Object>> resultMap = new ArrayList<>();
        for(Node n: NameToNode.get(cleanName)){
            Map<String,Object> spot = new HashMap<>();
            spot.put("lat",n.lat());
            spot.put("lon",n.lon());
            spot.put("name",n.name());
            spot.put("id",n.id());
            resultMap.add(spot);
        }
        return resultMap;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
