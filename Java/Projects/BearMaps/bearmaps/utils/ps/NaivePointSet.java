package bearmaps.utils.ps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    public Point nearest(double x, double y) {
        double nearest = Double.MAX_VALUE;
        Point target = new Point(x,y);
        Point nearestPt = null;

        for (Point pt: points) {
            double distance = Point.distance(target, pt);
            if (distance < nearest) {
                nearest = distance;
                nearestPt = pt;
            }
        }
        return nearestPt;
    }
}
