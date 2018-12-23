import java.util.Comparator;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private String origin;
    private Integer[] index;
    public CircularSuffixArray(String s) {
        if(s == null) {
            throw new IllegalArgumentException();
        }
        origin = s;
        int n = origin.length();
        index = new Integer[n];
        for(int i = 0; i < n; i++) {
            index[i] = i;
        }
        Arrays.sort(index, 0, n, new Comparator<Integer>() {
                public int compare(Integer e1, Integer e2) {
                    int cnt = 0;
                    while(cnt < n && origin.charAt(e1) == origin.charAt(e2)) {
                        cnt += 1;
                        e1 = (e1 + 1) % n;
                        e2 = (e2 + 1) % n;
                    }
                    if(origin.charAt(e1) < origin.charAt(e2)) {
                        return -1;
                    }
                    else if(origin.charAt(e1) > origin.charAt(e2)) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            });
    }

    public int length() {
        return origin.length();
    }
    public int index(int i) {
        if(i < 0 || i >= index.length) {
            throw new IllegalArgumentException();
        }
        return index[i];
    }
    public static void main(String[] args) {
        /*
        String s = StdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        for(int i = 0; i < csa.length(); i++) {
            StdOut.printf("%d ", csa.index[i]);
        }
        StdOut.println();
        */
    }
}
