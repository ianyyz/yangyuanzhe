public class BubbleGrid {
    public int[][] Bubbles;
    int rownum;
    int colnum;
    UnionFind ufbefore = new UnionFind(rownum*colnum);
    UnionFind ufafter = new UnionFind((rownum*colnum));

    public BubbleGrid(int[][] bubbles){
        Bubbles = bubbles;
        rownum = Bubbles.length;
        colnum = Bubbles[0].length;
    }

    public int twoD_to_oneD(int r, int c){
        int idx = r* colnum + c;
        return idx;
    }

    public UnionFind connect(){
        UnionFind a = new UnionFind(rownum*colnum);

        for (int i = 1; i < rownum; i++){
            for (int j = 1; j< colnum; j++){
                if (Bubbles[i][j] == 1 && Bubbles[i][j-1] == 1){
                    a.union(twoD_to_oneD(i,j),twoD_to_oneD(i,j-1));
                }
                if (Bubbles[i][j] == 1 && Bubbles[i-1][j] == 1){
                    a.union(twoD_to_oneD(i,j),twoD_to_oneD(i-1,j));
                }
            }
        }

        for (int i = 0; i < rownum-1; i++){
            for (int j = 0; j< colnum-1; j++){
                if (Bubbles[i][j] == 1 && Bubbles[i][j+1] == 1){
                    a.union(twoD_to_oneD(i,j),twoD_to_oneD(i,j+1));
                }
                if (Bubbles[i][j] == 1 && Bubbles[i+1][j] == 1){
                    a.union(twoD_to_oneD(i,j),twoD_to_oneD(i+1,j));
                }
            }
        }
        return a;
    }

    public void pop(int row, int col){
        Bubbles[row][col] = 0;
    }

    public int[] popBubbles(int[][] darts){

        int[] result = new int[darts.length];
        int dart;
        for( int i = 0; i<darts.length; i++){
            int parent = colnum;
            dart = twoD_to_oneD(darts[i][0],darts[i][1]);
            if (Bubbles[darts[i][0]][darts[i][1]] == 0){
                result[i] = 0;
            }else{
                ufbefore = connect();
                for (int k = 0;k<colnum;k++){
                    if(ufbefore.connected(k,dart)){
                        parent = k;
                        break;
                    }
                }
                this.pop(darts[i][0],darts[i][1]);
                ufafter = connect();
                if(parent == colnum){
                    result[i] = 0;
                }else{
                    result[i] = Math.abs(ufbefore.sizes[parent] - ufafter.sizes[parent])-1;
                }

            }

        }

        return result;
    }
    // use print to test the code.
    public void printDeque(int[] a){
        int idx = 0;
        while (idx < a.length) {
            System.out.print(a[idx]+ " ");
            idx += 1;
        }
        System.out.println(System.lineSeparator());
    }

    public static void main(String[] args){
        int [][] arr = {{1,0,0,1},{1,0,1,1},{0,1,0,1},{0,1,1,1}};
        BubbleGrid a = new BubbleGrid(arr);
        int [][] darts = {{1,2},{3,1},{1,3}};
        int [] b = a.popBubbles(darts);
        a.printDeque(b);

    }


}
