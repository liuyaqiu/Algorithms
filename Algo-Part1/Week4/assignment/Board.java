import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int N;
    private final int hamm;
    private final int manh;
    private final int[][] board;
    public Board(int[][] blocks) {
        N = blocks.length;
        board = new int[N][N];
        int tmp_hamm = 0;
        int tmp_manh = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                board[i][j] = blocks[i][j];
                if(board[i][j] == 0) {
                    continue;
                }
                if(board[i][j] != i * N + j + 1) {
                    tmp_hamm += 1;
                }
                int x = (board[i][j] - 1) / N;
                int y  = (board[i][j] - 1) % N;
                tmp_manh += Math.abs(x - i) + Math.abs(y - j);
            }
        }
        hamm = tmp_hamm;
        manh = tmp_manh;
    }
    public int dimension() {
        return N;
    }
    public int hamming() {
        return hamm;
    }
    public int manhattan() {
        return manh;
    }
    public boolean isGoal() {
        return hamm == 0;
    }
    public Board twin() {
        int x = 0, y = 0;
        int[][] new_board = new int[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                new_board[i][j] = board[i][j];
                if(board[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }
        int x1 = x, y1 = y, x2 = x, y2 = y;
        if(x == 0) {
            x1 = x + 1;
        }
        else {
            x1 = x - 1;
        }
        if(y == 0) {
            y2 = y + 1;
        }
        else {
            y2 = y - 1;
        }
        int t = new_board[x1][y1];
        new_board[x1][y1] = new_board[x2][y2];
        new_board[x2][y2] = t;
        return new Board(new_board);
    }
    public boolean equals(Object y) {
        if(y == this) {
            return true;
        }
        if(y == null) {
            return false;
        }
        if(y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board)y;
        if(that.dimension() != N || that.hamming() != hamm || that.manhattan()!= manh) {
            return false;
        }
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(that.board[i][j] != this.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    private class BoardIter implements Iterator<Board> {
        private ArrayList<Integer> index;
        private final int[][] keep;
        private int x;
        private int y;
        private int k;
        public BoardIter(int[][] board) {
            keep = new int[board.length][board.length];
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board.length; j++) {
                    keep[i][j] = board[i][j];
                }
            }
            x = 0;
            y = 0;
            k = 0;
            index = new ArrayList<Integer>();
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(board[i][j] == 0) {
                        x = i;
                        y = j;
                        break;
                    }
                }
            }
            if(x - 1 >= 0) {
                index.add((x - 1) * N + y);
            }
            if(x + 1 < N) {
                index.add((x + 1) * N + y);
            }
            if(y - 1 >= 0) {
                index.add(x * N + (y - 1));
            }
            if(y + 1 < N) {
                index.add(x * N + (y + 1));
            }
        }
        public boolean hasNext() {
            return k < index.size();
        }
        public Board next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            int[][] tmp = new int[keep.length][keep.length];
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    tmp[i][j] = keep[i][j];
                }
            }
            int x1 = index.get(k) / N;
            int y1 = index.get(k) % N;
            int t = tmp[x][y];
            tmp[x][y] = tmp[x1][y1];
            tmp[x1][y1] = t;
            k += 1;
            return new Board(tmp);
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new BoardIter(board);
            }
        };
    }
    public String toString() {
        String res = Integer.toString(N) + "\n";
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                res = res + Integer.toString(board[i][j]);
                if(j != N - 1) {
                    res = res + " ";
                }
                else {
                    res = res + "\n";
                }
            }
        }
        return res;
    }
    public static void main(String[] args) {
    }
}
