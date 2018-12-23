import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }
        Iterator<String> iter = rq.iterator();
        while(k > 0) {
            k -= 1;
            String cur = iter.next();
            StdOut.println(cur);
        }
    }
}
