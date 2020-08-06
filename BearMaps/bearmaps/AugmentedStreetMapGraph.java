package bearmaps;

import bearmaps.utils.Constants;
import bearmaps.utils.MyTrieSet;
import bearmaps.utils.graph.streetmap.Node;
import bearmaps.utils.graph.streetmap.StreetMapGraph;
import bearmaps.utils.ps.KDTree;
import bearmaps.utils.ps.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private List<Point> points;
    private HashMap<Point, Long> pointNodeMap;
    private KDTree searchTree;

    /** extra credit instance variables */
    private MyTrieSet trie;
    private HashMap<String, LinkedList<String>> trieMap = new HashMap<>();
    private HashMap<String, LinkedList<Node>> nodeTrieMap = new HashMap<>();


    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        points = new LinkedList<>();
        pointNodeMap = new HashMap<>();

        for (Node node: nodes) {
            if (neighbors(node.id()).size() > 0) {
                double x = projectToX(node.lon(), node.lat());
                double y = projectToY(node.lon(), node.lat());
                Point point = new Point(x,y);
//                Point point = new Point(node.lon(), node.lat());
                pointNodeMap.put(point, node.id());
                points.add(point);
            }
        }
        searchTree = new KDTree(points);

        /** for extra credit portion */
        /** change nodes list to trie */
        trie = new MyTrieSet();
        for (Node node: nodes) {
            if (node.name() != null) {
                String cleanedVer = cleanString(node.name());
                if (trieMap.containsKey(cleanedVer)) {
                    trieMap.get(cleanedVer).add(node.name());
                    nodeTrieMap.get(cleanedVer).add(node);
                } else {
                    LinkedList<String> values = new LinkedList<>();
                    LinkedList<Node> nodeList = new LinkedList<>();

                    values.add(node.name());
                    trieMap.put(cleanedVer, values);

                    nodeList.add(node);
                    nodeTrieMap.put(cleanedVer, nodeList);
                }
                trie.add(cleanedVer);
            }
        }
    }


    /**
     * For Project Part III
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        double x = projectToX(lon, lat);
        double y = projectToY(lon, lat);
        Point closestPoint = searchTree.nearest(x,y);
//        Point closestPoint = searchTree.nearest(lon, lat);
        return pointNodeMap.get(closestPoint);
    }

    /**
     * Return the Euclidean x-value for some point, p, in Berkeley. Found by computing the
     * Transverse Mercator projection centered at Berkeley.
     * @param lon The longitude for p.
     * @param lat The latitude for p.
     * @return The flattened, Euclidean x-value for p.
     * @source https://en.wikipedia.org/wiki/Transverse_Mercator_projection
     */
    static double projectToX(double lon, double lat) {
        double dlon = Math.toRadians(lon - ROOT_LON);
        double phi = Math.toRadians(lat);
        double b = Math.sin(dlon) * Math.cos(phi);
        return (K0 / 2) * Math.log((1 + b) / (1 - b));
    }

    /**
     * Return the Euclidean y-value for some point, p, in Berkeley. Found by computing the
     * Transverse Mercator projection centered at Berkeley.
     * @param lon The longitude for p.
     * @param lat The latitude for p.
     * @return The flattened, Euclidean y-value for p.
     * @source https://en.wikipedia.org/wiki/Transverse_Mercator_projection
     */
    static double projectToY(double lon, double lat) {
        double dlon = Math.toRadians(lon - ROOT_LON);
        double phi = Math.toRadians(lat);
        double con = Math.atan(Math.tan(phi) / Math.cos(dlon));
        return K0 * (con - Math.toRadians(ROOT_LAT));
    }


    /**
     * For Project Part IV (extra credit)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> curr = new LinkedList<>();
        LinkedList<String> result = new LinkedList<>();

        curr.addAll(trie.keysWithPrefix(prefix));

        for (String key: curr) {
            result.addAll(trieMap.get(key));
        }
        return result;
    }

    /**
     * For Project Part IV (extra credit)
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
        return new LinkedList<>();
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

        
    /**
     * Scale factor at the natural origin, Berkeley. Prefer to use 1 instead of 0.9996 as in UTM.
     * @source https://gis.stackexchange.com/a/7298
     */
    private static final double K0 = 1.0;
    /** Latitude centered on Berkeley. */
    private static final double ROOT_LAT = (Constants.ROOT_ULLAT + Constants.ROOT_LRLAT) / 2;
    /** Longitude centered on Berkeley. */
    private static final double ROOT_LON = (Constants.ROOT_ULLON + Constants.ROOT_LRLON) / 2;

}
