import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
    private final LineSegment[] lines;
    public FastCollinearPoints(Point[] points) {
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
        for(Point p: copy_points) {
            Point[] cur = copy_points.clone();
            Arrays.sort(cur, p.slopeOrder());
            int k = 0;
            while(k < n) {
                int r = k;
                double t = p.slopeTo(cur[k]);
                int comp = 0;
                Point end = p;
                while(r < n && p.slopeTo(cur[r]) == t) {
                    if(p.compareTo(cur[r]) < 0) {
                        comp += 1;
                    }
                    if(end.compareTo(cur[r]) < 0) {
                        end = cur[r];
                    }
                    r += 1;
                }
                if(r - k >= 3 && comp == r - k) {
                    res.add(new LineSegment(p, end));
                }
                k = r;
            }
        }
        LineSegment[] tmp = new LineSegment[0];
        lines = res.toArray(tmp);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    */
}
