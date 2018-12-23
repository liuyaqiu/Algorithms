import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    private final LineSegment lines[];
    public BruteCollinearPoints(Point[] points) {
        if(points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for(Point p: points) {
            if(p == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        int n = points.length;
        Point[] copy_points = points.clone();
        Arrays.sort(copy_points);
        for(int i = 0; i < n - 1; i++) {
            if(copy_points[i].compareTo(copy_points[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        ArrayList<LineSegment> res = new ArrayList<LineSegment>();
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                for(int k = j + 1; k < n; k++) {
                    if(copy_points[i].slopeTo(copy_points[j]) != copy_points[i].slopeTo(copy_points[k])) {
                        continue;
                    }
                    for(int p = k + 1; p < n; p++) {
                        if(copy_points[i].slopeTo(copy_points[k]) == copy_points[i].slopeTo(copy_points[p])) {
                            LineSegment ls = new LineSegment(copy_points[i], copy_points[p]);
                            res.add(ls);
                        }
                    }
                }
            }
        }
        LineSegment[] ans = new LineSegment[0];
        lines = res.toArray(ans);
    }

    public int numberOfSegments() {
        return lines.length;
    }

    public LineSegment[] segments() {
        LineSegment[] res = lines.clone();
        return res;
    }

    /*
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    */
}
