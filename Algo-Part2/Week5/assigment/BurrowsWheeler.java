import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class BurrowsWheeler {
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int n = csa.length();
        for(int i = 0; i < n; i++) {
            if(csa.index(i) == 0) {
                BinaryStdOut.write(i);
            }
        }
        for(int i = 0; i < n; i++) {
            char c = s.charAt((csa.index(i) + (n - 1)) % n);
            BinaryStdOut.write(c, 8);
        }
        BinaryStdOut.close();
    }

    public static void inverseTransform() {
        int first = BinaryStdIn.readInt(32);
        ArrayList<Character> t = new ArrayList<Character>();
        int[] count = new int[256];
        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            count[(int)c] += 1;
            t.add(c);
        }
        int n = t.size();
        char[] sorted = new char[n];
        int j = 0;
        for(int i = 0; i < 256; i++) {
            for(int k = 0; k < count[i]; k++) {
                sorted[j] = (char)i;
                j += 1;
            }
        }
        int sum = 0;
        for(int i = 0; i < 256; i++) {
            if(count[i] > 0) {
                int tmp = sum;
                sum += count[i];
                count[i] = tmp;
            }
        }
        int[] reverseIndex = new int[n];
        for(int i = 0; i < n; i++) {
            char c = t.get(i);
            reverseIndex[i] = count[(int)c]++;
        }
        int[] next = new int[n];
        for(int i = 0; i < n; i++) {
            next[reverseIndex[i]] = i;
        }
        int cnt = 0;
        while(cnt < n) {
            BinaryStdOut.write(sorted[first], 8);
            first = next[first];
            cnt += 1;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if(args[0].equals("-")) {
            transform();
        }
        else if(args[0].equals("+")){
            inverseTransform();
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
