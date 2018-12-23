import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Comparator;

public class KdTree {
    private class Node {
        private boolean direction;
        private Point2D point;
        private Node left;
        private Node right;
        public Node(Point2D p, boolean d) {
            point = p;
            direction = d;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;
    private Point2D nearest_node;

    public KdTree() {
        root = null;
        size = 0;
        nearest_node = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        Node parent = root;
        boolean isleft = true;
        while(parent != null) {
            if(parent.point.equals(p)) {
                return;
            }
            if(parent.direction) {
                if(p.x() < parent.point.x()) {
                    if(parent.left != null) {
                        parent = parent.left;
                    }
                    else {
                        break;
                    }
                }
                else {
                    if(parent.right != null) {
                        parent = parent.right;
                    }
                    else {
                        isleft = false;
                        break;
                    }
                }
            }
            else {
                if(p.y() < parent.point.y()) {
                    if(parent.left != null) {
                        parent = parent.left;
                    }
                    else {
                        break;
                    }
                }
                else {
                    if(parent.right != null) {
                        parent = parent.right;
                    }
                    else {
                        isleft = false;
                        break;
                    }
                }
            }
        }
        size += 1;
        if(parent == null) {
            root = new Node(p, true);
        }
        else {
            boolean d = !parent.direction;
            Node node = new Node(new Point2D(p.x(), p.y()), d);
            if(isleft) {
                parent.left = node;
            }
            else {
                parent.right = node;
            }
        }
    }

    public boolean contains(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        Node cur = root;
        while(cur != null) {
            if(cur.point.equals(p)) {
                return true;
            }
            if(cur.direction) {
                if(p.x() < cur.point.x()) {
                    cur = cur.left;
                }
                else {
                    cur = cur.right;
                }
            }
            else {
                if(p.y() < cur.point.y()) {
                    cur = cur.left;
                }
                else {
                    cur = cur.right;
                }
            }
        }
        return false;
    }

    private void drawNode(Node cur, double xmin, double xmax, double ymin, double ymax) {
        if(cur == null) {
            return;
        }
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(cur.point.x(), cur.point.y());
        StdDraw.setPenRadius();
        if(cur.direction) {
            //vertical line
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(cur.point.x(), ymin, cur.point.x(), ymax);
            drawNode(cur.left, xmin, cur.point.x(), ymin, ymax);
            drawNode(cur.right, cur.point.x(), xmax, ymin, ymax);
        }
        else {
            //horizontal line
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin, cur.point.y(), xmax, cur.point.y());
            drawNode(cur.left, xmin, xmax, ymin, cur.point.y());
            drawNode(cur.right, xmin, xmax, cur.point.y(), ymax);
        }
    }

    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        drawNode(root, 0, 1, 0, 1);
        StdDraw.show();
    }

    private void search(Node cur, RectHV rect, ArrayList<Point2D> res, double xmin, double xmax, double ymin, double ymax) {
        if(cur == null || xmax < rect.xmin() || xmin > rect.xmax() || ymax < rect.ymin() || ymin > rect.ymax()) {
            return;
        }
        if(rect.contains(cur.point)) {
            res.add(cur.point);
        }
        if(cur.direction) {
            if(cur.point.x() <= rect.xmin()) {
                search(cur.right, rect, res, cur.point.x(), xmax, ymin, ymax);
            }
            else if(cur.point.x() > rect.xmax()) {
                search(cur.left, rect, res, xmin, cur.point.x(), ymin, ymax);
            }
            else {
                search(cur.left, rect, res, xmin, cur.point.x(), ymin, ymax);
                search(cur.right, rect, res, cur.point.x(), xmax, ymin, ymax);
            }
        }
        else {
            if(cur.point.y() <= rect.ymin()) {
                search(cur.right, rect, res, xmin, xmax, cur.point.y(), ymax);
            }
            else if(cur.point.y() > rect.ymax()) {
                search(cur.left, rect, res, xmin, xmax, ymin, cur.point.y());
            }
            else {
                search(cur.left, rect, res, xmin, xmax, ymin, cur.point.y());
                search(cur.right, rect, res, xmin, xmax, cur.point.y(), ymax);
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Point2D> res = new ArrayList<Point2D>();
        search(root, rect, res, 0, 1, 0, 1);
        return res;
    }

    private void nearestNode(Node cur, Point2D p, double xmin, double xmax, double ymin, double ymax) {
        if(cur == null) {
            return;
        }
        if(nearest_node == null || p.distanceSquaredTo(cur.point) < p.distanceSquaredTo(nearest_node)) {
            nearest_node = cur.point;
        }
        if(cur.direction) {
            RectHV left_rect = new RectHV(xmin, ymin, cur.point.x(), ymax);
            RectHV right_rect = new RectHV(cur.point.x(), ymin, xmax, ymax);
            if(p.x() < cur.point.x()) {
                if(left_rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest_node)) {
                    nearestNode(cur.left, p, xmin, cur.point.x(), ymin, ymax);
                }
                if(right_rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest_node)) {
                    nearestNode(cur.right, p, cur.point.x(), xmax, ymin, ymax);
                }
            }
            else {
                if(right_rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest_node)) {
                    nearestNode(cur.right, p, cur.point.x(), xmax, ymin, ymax);
                }
                if(left_rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest_node)) {
                    nearestNode(cur.left, p, xmin, cur.point.x(), ymin, ymax);
                }
            }
        }
        else {
            RectHV left_rect = new RectHV(xmin, ymin, xmax, cur.point.y());
            RectHV right_rect = new RectHV(xmin, cur.point.y(), xmax, ymax);
            if(p.y() < cur.point.y()) {
                if(left_rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest_node)) {
                    nearestNode(cur.left, p, xmin, xmax, ymin, cur.point.y());
                }
                if(right_rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest_node)) {
                    nearestNode(cur.right, p, xmin, xmax, cur.point.y(), ymax);
                }
            }
            else {
                if(right_rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest_node)) {
                    nearestNode(cur.right, p, xmin, xmax, cur.point.y(), ymax);
                }
                if(left_rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest_node)) {
                    nearestNode(cur.left, p, xmin, xmax, ymin, cur.point.y());
                }
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        nearestNode(root, p, 0, 1, 0, 1);
        if(nearest_node == null) {
            return null;
        }
        Point2D res = new Point2D(nearest_node.x(), nearest_node.y());
        nearest_node = null;
        return res;
    }

    public static void main(String[] args) {
    }
}