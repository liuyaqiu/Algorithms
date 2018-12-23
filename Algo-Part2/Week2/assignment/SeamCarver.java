import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    public SeamCarver(Picture picture) {
        if(picture == null) {
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
    }
    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        int h = height();
        int w = width();
        if(x < 0 || x >= w) {
            throw new IllegalArgumentException();
        }
        if(y < 0 || y >= h) {
            throw new IllegalArgumentException();
        }
        if(x == 0 || x == w - 1) {
            return 1000;
        }
        if(y == 0 || y == h - 1) {
            return 1000;
        }
        Color h1 = picture.get(x - 1, y);
        int h1r = h1.getRed();
        int h1g = h1.getGreen();
        int h1b = h1.getBlue();
        Color h2 = picture.get(x + 1, y);
        int h2r = h2.getRed();
        int h2g = h2.getGreen();
        int h2b = h2.getBlue();
        double dx = Math.pow(h2r - h1r, 2) + Math.pow(h2g - h1g, 2) + Math.pow(h2b - h1b, 2);
        Color v1 = picture.get(x, y - 1);
        int v1r = v1.getRed();
        int v1g = v1.getGreen();
        int v1b = v1.getBlue();
        Color v2 = picture.get(x, y + 1);
        int v2r = v2.getRed();
        int v2g = v2.getGreen();
        int v2b = v2.getBlue();
        double dy = Math.pow(v2r - v1r, 2) + Math.pow(v2g - v1g, 2) + Math.pow(v2b - v1b, 2);
        return Math.sqrt(dx + dy);
    }

    public int[] findHorizontalSeam() {
        int h = height();
        int w = width();
        double[] sums = new double[h];
        for(int j = 0; j < h; j++) {
            sums[j] = 1000;
        }
        int[][] prev = new int[h][w];
        for(int j = 0; j < h; j++) {
            prev[j][0] = -1;
        }
        for(int i = 1; i < w; i++) {
            double[] cur = new double[h];
            for(int j = 0; j < h; j++) {
                int k = -1;
                double min = -1;
                for(int d = -1; d <= 1; d++) {
                    if(j + d < 0 || j + d >= h) {
                        continue;
                    }
                    if(min < 0 || sums[j + d] < min) {
                        min = sums[j + d];
                        k = j + d;
                    }
                }
                cur[j] = energy(i, j) + sums[k];
                prev[j][i] = k;
            }
            for(int j = 0; j < h; j++) {
                sums[j] = cur[j];
            }
        }
        Stack<Integer> stk = new Stack<Integer>();
        double min_sum = -1;
        int tail = -1;
        for(int j = 0; j < h; j++) {
            if(min_sum < 0 || sums[j] < min_sum) {
                min_sum = sums[j];
                tail = j;
            }
        }
        stk.push(tail);
        for(int i = w - 1; i > 0; i--) {
            tail = prev[tail][i];
            stk.push(tail);
        }
        int[] index = new int[w];
        for(int i = 0; i < w; i++) {
            index[i] = stk.pop();
        }
        return index;
    }
    public int[] findVerticalSeam() {
        int h = height();
        int w = width();
        double[] sums= new double[w];
        for(int i = 0; i < w; i++) {
            sums[i] = 1000;
        }
        int[][] prev = new int[h][w];
        for(int i = 0; i < w; i++) {
            prev[0][i] = -1;
        }
        for(int j = 1; j < h; j++) {
            double[] cur = new double[w];
            for(int i = 0; i < w; i++) {
                int k = -1;
                double min = -1;
                for(int d = -1; d <= 1; d++) {
                    if(i + d < 0 || i + d >= w) {
                        continue;
                    }
                    if(min < 0 || sums[i + d] < min) {
                        min = sums[i + d];
                        k = i + d;
                    }
                }
                cur[i] = energy(i, j) + sums[k];
                prev[j][i] = k;
            }
            for(int i = 0; i < w; i++) {
                sums[i] = cur[i];
            }
        }
        Stack<Integer> stk = new Stack<Integer>();
        int tail = -1;
        double min_sum = -1;
        for(int i = 0; i < w; i++) {
            if(min_sum < 0 || sums[i] < min_sum) {
                min_sum = sums[i];
                tail = i;
            }
        }
        stk.push(tail);
        for(int j = h - 1; j > 0; j--) {
            tail = prev[j][tail];
            stk.push(tail);
        }
        int[] index = new int[h];
        for(int j = 0; j < h; j++) {
            index[j] = stk.pop();
        }
        return index;
    }

    public void removeHorizontalSeam(int[] seam) {
        if(seam == null) {
            throw new IllegalArgumentException();
        }
        int h = height();
        int w = width();
        if(h <= 1) {
            throw new IllegalArgumentException();
        }
        if(seam.length != w) {
            throw new IllegalArgumentException();
        }
        Picture sp = new Picture(w, h - 1);
        for(int i = 0; i < w; i++) {
            if(i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException();
            }
            if(seam[i] < 0 || seam[i] >= h) {
                throw new IllegalArgumentException();
            }
            for(int j = 0; j < h - 1; j++) {
                if(j < seam[i]) {
                    sp.set(i, j, picture.get(i, j));
                }
                else {
                    sp.set(i, j, picture.get(i, j + 1));
                }
            }
        }
        picture = sp;
    }

    public void removeVerticalSeam(int[] seam) {
        if(seam == null) {
            throw new IllegalArgumentException();
        }
        int h = height();
        int w = width();
        if(w <= 1) {
            throw new IllegalArgumentException();
        }
        if(seam.length != h) {
            throw new IllegalArgumentException();
        }
        Picture sp = new Picture(w - 1, h);
        for(int j = 0; j < h; j++) {
            if(j > 0 && Math.abs(seam[j] - seam[j - 1]) > 1) {
                throw new IllegalArgumentException();
            }
            if(seam[j] < 0 || seam[j] >= w) {
                throw new IllegalArgumentException();
            }
            for(int i = 0; i < w - 1; i++) {
                if(i < seam[j]) {
                    sp.set(i, j, picture.get(i, j));
                }
                else {
                    sp.set(i, j, picture.get(i + 1, j));
                }
            }
        }
        picture = sp;
    }

    public static void main(String[] args) {
        String filename = args[0];
        Picture p = new Picture(filename);
        SeamCarver sc = new SeamCarver(p);
        sc.picture().show();
        /*
        for(int i = 0; i < 80; i++) {
            int[] h_seam = sc.findHorizontalSeam();
            sc.removeHorizontalSeam(h_seam);
        }
        sc.picture().show();
        */
        for(int i = 0; i < 150; i++) {
            int[] v_seam = sc.findVerticalSeam();
            sc.removeVerticalSeam(v_seam);
        }
        sc.picture().show();
    }
}

