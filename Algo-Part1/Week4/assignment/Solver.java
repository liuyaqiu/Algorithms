import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BST;
import java.util.Iterator;
import java.util.Stack;
import java.util.Collections;

public class Solver {

    private class Node implements Comparable<Node> {
        final Board board;
        final int moves;
        final Node previous;
        final int priority;
        Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = moves + this.board.manhattan();
        }
        public int compareTo(Node that) {
            if(this.priority < that.priority) {
                return -1;
            }
            else if(this.priority > that.priority) {
                return 1;
            }
            else {
                return 0;
            }
        }
    };

    private boolean isSolvable;
    private int moves;
    private Stack<Board> res;

    public Solver(Board initial) {
        if(initial == null) {
            throw new java.lang.IllegalArgumentException();
        }
        MinPQ<Node> searchQueue = new MinPQ<Node>();
        MinPQ<Node> twinQueue = new MinPQ<Node>();
        searchQueue.insert(new Node(initial, 0, null));
        twinQueue.insert(new Node(initial.twin(), 0, null));
        while(!searchQueue.min().board.isGoal() && !twinQueue.min().board.isGoal()) {
            Node min = searchQueue.delMin();
            Node twin_min = twinQueue.delMin();
            Iterable<Board> neighbors = min.board.neighbors();
            Iterable<Board> twinNeighbors = twin_min.board.neighbors();
            for(Board nb: neighbors) {
                if(min.previous != null && nb.equals(min.previous.board)) {
                    continue;
                }
                Node next = new Node(nb, min.moves + 1, min);
                searchQueue.insert(next);
            }
            for(Board tnb: twinNeighbors) {
                if(twin_min.previous != null && tnb.equals(twin_min.previous.board)) {
                    continue;
                }
                Node twin_next = new Node(tnb, twin_min.moves + 1, twin_min);
                twinQueue.insert(twin_next);
            }
        }
        if(searchQueue.min().board.isGoal()) {
            moves = searchQueue.min().moves;
            isSolvable = true;
            res = new Stack<Board>();
            Stack<Board> reverse = new Stack<Board>();
            Node node = searchQueue.min();
            while(node != null) {
                reverse.push(node.board);
                node = node.previous;
            }
            while(!reverse.empty()) {
                Board top = reverse.pop();
                res.push(top);
            }
        }
        if(twinQueue.min().board.isGoal()) {
            moves = -1;
            isSolvable = false;
            res = null;
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return res;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
