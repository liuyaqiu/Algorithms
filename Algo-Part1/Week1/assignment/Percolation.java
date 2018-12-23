import java.lang.Math;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF percolation;
    private WeightedQuickUnionUF fullness;
    private boolean[] isOpen;
    private int count;
    private int gridLength;
    private int virtualTopIndex;
    private int virtualBottomIndex;

    private void checkIndex(int row, int col) {
        if(row > gridLength || row < 1) {
            throw new IllegalArgumentException("row index out of index");
        }
        if(col > gridLength || col < 1) {
            throw new IllegalArgumentException("col index out of index");
        }
    }

    private int siteIndex(int row, int col) {
        checkIndex(row, col);
        return (row - 1) * gridLength + col - 1;
    }

    public Percolation(int n) {
        if(n < 1) {
            throw new IllegalArgumentException();
        }
        gridLength = n;
        int array_size = n * n + 2;
        virtualTopIndex = n * n;
        virtualBottomIndex = n * n + 1;
        isOpen = new boolean[array_size];
        isOpen[virtualTopIndex] = true;
        isOpen[virtualBottomIndex] = true;
        percolation = new WeightedQuickUnionUF(array_size);
        fullness = new WeightedQuickUnionUF(array_size);
        for(int j = 1; j <= gridLength; j++) {
            int topIndex = siteIndex(1, j);
            percolation.union(virtualTopIndex, topIndex);
            fullness.union(virtualTopIndex, topIndex);
            int bottomIndex = siteIndex(gridLength, j);
            percolation.union(virtualBottomIndex, bottomIndex);
        }
        count = 0;
    }

    public void open(int row, int col) {
        int index = siteIndex(row, col);
        if(isOpen[index]) {
            return;
        }
        isOpen[index] = true;
        count += 1;
        if(row - 1 >= 1 && isOpen(row - 1, col)) {
            int top = siteIndex(row - 1, col);
            percolation.union(index, top);
            fullness.union(index, top);
        }
        if(row + 1 <= gridLength && isOpen(row + 1, col)) {
            int bottom = siteIndex(row + 1, col);
            percolation.union(index, bottom);
            fullness.union(index, bottom);
        }
        if(col - 1 >= 1 && isOpen(row, col - 1)) {
            int left = siteIndex(row, col - 1);
            percolation.union(index, left);
            fullness.union(index, left);
        }
        if(col + 1 <= gridLength && isOpen(row, col + 1)) {
            int right = siteIndex(row, col + 1);
            percolation.union(index, right);
            fullness.union(index, right);
        }
    }

    public boolean isOpen(int row, int col) {
        int index = siteIndex(row, col);
        return isOpen[index];
    }

    public boolean isFull(int row, int col) {
        int index = siteIndex(row, col);
        return isOpen[index] && fullness.connected(virtualTopIndex, index);
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        if(gridLength > 1)
            return percolation.connected(virtualTopIndex, virtualBottomIndex);
        else
            return isOpen(1, 1);
    }

    public static void main(String[] args) {
    }
}