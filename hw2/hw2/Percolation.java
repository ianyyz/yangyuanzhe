package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public WeightedQuickUnionUF uf;
    public int[][] grid;
    public int rownum;
    public int colnum;
    public int size;
    public int virtualTop;
    public int virtualBot;

    public int twoD_to_oneD(int r, int c){
        int idx = r* colnum + c;
        return idx;
    }

    // create a percolation grid by initializing a N by N 2d array
    public Percolation(int N){
        if (N <= 0){
            throw new IllegalArgumentException("invalid argument for creating a percolation grid.");
        }
        grid = new int[N][N];
        virtualTop = N*N;
        virtualBot = N*N +1;
        rownum = N;
        colnum = N;
        uf = new WeightedQuickUnionUF(rownum*colnum+2);
        for (int i = 0; i < colnum; i++){
            uf.union(virtualTop,twoD_to_oneD(0,i));
        }
        for (int i = 0; i < colnum; i++){
            uf.union(virtualBot,twoD_to_oneD(rownum-1,i));
        }
        size = 0;
    }

    public void open(int row, int col){
        if(Math.max(row,col) >= rownum || Math.min(row,col) < 0) {
            throw new IndexOutOfBoundsException("invalid row and column input.");
        }
        if (grid[row][col] != 0){
            return;
        }else {
            grid[row][col] = 1;
            size += 1;
        }
        if((row >= 1) && (grid[row - 1][col] != 0)){
            uf.union(twoD_to_oneD(row,col),twoD_to_oneD(row-1,col));
        }
        if((row < (rownum - 1)) && (grid[row + 1][col] != 0)){
            uf.union(twoD_to_oneD(row,col),twoD_to_oneD(row+1,col));
        }
        if((col >= 1) && (grid[row][col - 1] != 0)){
            uf.union(twoD_to_oneD(row,col),twoD_to_oneD(row,col-1));
        }
        if((col < (colnum - 1)) && (grid[row][col + 1] != 0)){
            uf.union(twoD_to_oneD(row,col),twoD_to_oneD(row,col+1));
        }



    }

    public boolean isOpen(int row, int col){
        if(Math.max(row,col) >= rownum || Math.min(row,col) < 0) {
            throw new IndexOutOfBoundsException("invalid row and column input.");
        }
        return grid[row][col] != 0;
    }

    public boolean isFull(int row, int col){
        if(Math.max(row,col) >= rownum || Math.min(row,col) < 0) {
            throw new IndexOutOfBoundsException("invalid row and column input.");
        }
        return uf.connected(virtualTop,twoD_to_oneD(row,col));
    }

    public int numberOfOpenSites(){
        return size;
    }

    public boolean percolates(){
        return uf.connected(virtualBot,virtualTop);
    }

    public static void main(String[] args){
        Percolation p = new Percolation(5);
        p.open(2,3);
        p.open(3,2);
        System.out.println(p.isFull(2,3));
        p.open(0,3);
        p.open(0,3);
        p.open(1,3);
        p.isOpen(4,4);
        System.out.println(p.isFull(2,3));
        p.open(2,2);
        p.open(4,2);
        System.out.println(p.percolates());


    }



}
