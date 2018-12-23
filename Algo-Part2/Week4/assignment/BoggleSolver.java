import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.HashSet;

public class BoggleSolver {
    private final TST<Integer> tst;
    public BoggleSolver(String[] dictionary) {
        tst = new TST<Integer>();
        for(String s: dictionary) {
            Integer val = 0;
            int len = s.length();
            if(len <= 2) {
                val = 0;
            }
            else if(len <= 4) {
                val = 1;
            }
            else if(len <= 5) {
                val = 2;
            }
            else if(len <= 6) {
                val = 3;
            }
            else if(len <= 7) {
                val = 5;
            }
            else {
                val = 11;
            }
            tst.put(s, val);
        }
    }

    private void search(BoggleBoard board, String prefix, boolean[][] mark, int i, int j, HashSet<String> res) {
        int m = board.rows(), n = board.cols();
        if(i < 0 || i >= m || j < 0 || j >= n || mark[i][j]) {
            return;
        }
        if(prefix.length() != 0 && !tst.isPrefix(prefix)) {
            return;
        }
        mark[i][j] = true;
        for(int dx = -1; dx <= 1; dx++) {
            for(int dy = -1; dy <= 1; dy++) {
                String cur = prefix;
                char c = board.getLetter(i, j);
                if(c == 'Q') {
                    cur += "QU";
                }
                else {
                    cur += c;
                }
                if(cur.length() >= 3 && tst.contains(cur)) {
                    res.add(cur);
                }
                search(board, cur, mark, i + dx, j + dy, res);
            }
        }
        mark[i][j] = false;
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int m = board.rows(), n = board.cols();
        boolean[][] mark = new boolean[m][n];
        HashSet<String> res = new HashSet<String>();
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                search(board, "", mark, i, j, res);
            }
        }
        return res;
    }

    public int scoreOf(String word) {
        Integer res = tst.get(word);
        if(res == null) {
            return 0;
        }
        return res;
    }

    public static void main(String[] args) {
        /*
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        */
    }
}
