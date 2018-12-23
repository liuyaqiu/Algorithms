import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.TreeSet;
import java.util.Set;
import java.util.Comparator;
import java.util.ArrayList;

public class PointSET {
    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<Point2D>(new Comparator<Point2D>() {
                public int compare(Point2D p1, Point2D p2) {
                    if(p1.x() != p2.x()) {
                        if(p1.x() < p2.x()) {
                            return -1;
                        }
                        else {
                            return 1;
                        }
                    }
                    else {
                        if(p1.y() < p2.y()) {
                            return -1;
                        }
                        else if(p1.y() > p2.y()) {
                            return 1;
                        }
                        else {
                            return 0;
                        }
                    }
                }
            });
    }

    public boolean isEmpty() {
        return points.size() == 0;
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        for (Point2D p : points) {
            p.draw();
        }
        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Point2D> res = new ArrayList<Point2D>();
        for(Point2D p: points) {
            if(p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax()) {
                res.add(p);
            }
        }
        return res;
    }

    public Point2D nearest(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        Point2D res = null;
        for(Point2D point: points) {
            if(res == null || p.distanceTo(point) < p.distanceTo(res)) {
                res = point;
            }
        }
        return res;
    }

    public static void main(String[] args) {
    }
}