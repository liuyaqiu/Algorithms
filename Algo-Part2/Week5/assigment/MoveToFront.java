import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;

public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    private static int R = 256;
    public static void encode() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for(int i = 0; i < R; i++) {
            list.add(i);
        }
        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int item = (int)(c);
            int index = list.indexOf(item);
            //StdOut.printf("%c %d %h\n", c, item, index);
            list.remove(index);
            list.addFirst(item);
            BinaryStdOut.write(index, 8);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for(int i = 0; i < R; i++) {
            list.add(i);
        }
        while(!BinaryStdIn.isEmpty()) {
            int index = BinaryStdIn.readInt(8);
            int item = list.get(index);
            char c = (char)(item);
            list.remove(index);
            list.addFirst(item);
            BinaryStdOut.write(c, 8);
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if(args[0].equals("-")) {
            encode();
        }
        else if(args[0].equals("+")) {
            decode();
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
