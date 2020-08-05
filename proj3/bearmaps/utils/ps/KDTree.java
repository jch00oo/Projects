package bearmaps.utils.ps;

import java.util.List;

public class KDTree implements PointSet {

    private KDTreeNode root;

    //* implement KDTreeNode w point, left tree, right tree
    //* need insert method to implement KDTree
    //* implement helper method for nearest by going down good side by comparing distance

    public KDTree(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return;
        }
        for (Point point: points) {
            insert(point);
        }
    }

    public void insert(Point point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        root = insertHelper(root, point, 0);
    }

    public KDTreeNode insertHelper(KDTreeNode node, Point pt, int depth) {
        if (node == null) {
            return new KDTreeNode(pt, depth);
        }

        boolean goLeft = (node.depth % 2 == 0);

        double ptPos = goLeft ? pt.getX() : pt.getY();
        double nodePos = goLeft ? node.point.getX() : node.point.getY();

        if (ptPos < nodePos) {
            node.left = insertHelper(node.left, pt, depth + 1);
        } else {
            node.right = insertHelper(node.right, pt, depth + 1);
        }

        return node;
    }

    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        KDTreeNode bestNode = nearestHelper(root, target, root);
        return bestNode.point;
    }

    private KDTreeNode nearestHelper(KDTreeNode curr, Point targetPt, KDTreeNode opt) {
        if (curr == null) {
            return opt;
        }

        double currDis = Point.distance(curr.point, targetPt);
        double optDis = Point.distance(opt.point, targetPt);

        if (currDis < optDis) {
            opt = curr;
        }

        KDTreeNode good = null;
        KDTreeNode bad = null;

        boolean goLeft = (curr.depth % 2 == 0);
        double tarPos = goLeft ? targetPt.getX() : targetPt.getY();
        double currPos = goLeft ? curr.point.getX() : curr.point.getY();

        if (tarPos < currPos) {
            good = curr.left;
            bad = curr.right;
        } else {
            good = curr.right;
            bad = curr.left;
        }

        opt = nearestHelper(good, targetPt, opt);

        Point tracker = goLeft ? new Point(curr.point.getX(), targetPt.getY()) : new Point(targetPt.getX(), curr.point.getY());
        double vDis = Point.distance(tracker, targetPt);
        double bDis = Point.distance(opt.point, targetPt);
        boolean toPrune = (vDis < bDis);

        if (toPrune) {
            opt = nearestHelper(bad, targetPt, opt);
        }
        return opt;
    }

    private class KDTreeNode {

        private Point point;
        private KDTreeNode left;
        private KDTreeNode right;
        private int depth;

        //* root node ig
        KDTreeNode(Point point) {
            this.point = point;
        }

        //* actual tree
        KDTreeNode(Point point, KDTreeNode left, KDTreeNode right) {
            this.point = point;
            this.left = left;
            this.right = right;
        }

        KDTreeNode(Point point, int depth) {
            this.point = point;
            this.depth = depth;
            left = null;
            right = null;
        }

        Point getPoint() {
            return point;
        }

        KDTreeNode getLeft() {
            return left;
        }

        KDTreeNode getRight() {
            return right;
        }

    }

}
