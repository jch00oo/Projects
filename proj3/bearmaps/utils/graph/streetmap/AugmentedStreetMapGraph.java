package bearmaps.utils.graph.streetmap;

import bearmaps.utils.ps.KDTree;
import bearmaps.utils.ps.Point;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AugmentedStreetMapGraph extends StreetMapGraph {

    private List<Node> nodes;
    private List<Point> points;
    private HashMap<Point, Node> pointNodeMap;
    private KDTree searchTree;

    public AugmentedStreetMapGraph(String dbpath) {
        super(dbpath);
        nodes = this.getNodes();
        points = new LinkedList<>();
        pointNodeMap = new HashMap<>();
        for (Node node: nodes) {
            if (!this.neighbors(node.id()).isEmpty()) {
                double lon = node.lon();
                double lat = node.lat();
                Point point = new Point(lon, lat);
                points.add(point);
                pointNodeMap.put(point, node);
            }
        }
        searchTree = new KDTree(points);
    }

    public long closest(double lon, double lat) {
        Point point = searchTree.nearest(lon, lat);
        return pointNodeMap.get(point).id();
    }
}
