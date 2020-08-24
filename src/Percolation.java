import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    /*
    btUF - contains bottom and top
    tUF - contains only top
    */
    private WeightedQuickUnionUF btUf, tUF;
    private boolean[] opened;
    private int n, topNode, bottomNode, numberOfOpen;
    private int[] searchFields;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        btUf = new WeightedQuickUnionUF(n * n + 2);
        tUF = new WeightedQuickUnionUF(n * n + 1);
        opened = new boolean[n * n + 2]; // 1 for bottom and 1 for top
        topNode = n * n;
        bottomNode = n * n + 1;
        searchFields = new int[]{-1, 1, -n, n};
        this.n = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = getIndex(row, col);
        if (index < 0) {
            throw new IllegalArgumentException();
        }
        if (!opened[index]) {
            opened[index] = true;
            numberOfOpen++;

            if (index < n) {
                tUF.union(index, topNode);
                btUf.union(index, topNode);
            }

            if (index > n * (n - 1) && index < n * n ) {
                btUf.union(index, bottomNode);
            }

            for (int field : searchFields) {
                int curIndex = index + field;
                if ((field == -1 && col == 1) || (field == 1 && col == n) || (field == -n && row == 1)
                        || (field == n && row == n)) {
                    continue;
                }
                if (opened[curIndex]) {
                    tUF.union(curIndex, index);
                    btUf.union(curIndex, index);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = getIndex(row, col);
        return opened[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = getIndex(row, col);
        return opened[index] && tUF.connected(index, topNode);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return btUf.connected(topNode, bottomNode);
    }

    private int getIndex(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            return -1;
        }
        else {
            return n *(row-1)+(col-1);
        }
    }
}


