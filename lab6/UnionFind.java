public class UnionFind {
    public int[] items;
    public int length;
    public int[] sizes;

    // TODO - Add instance variables?

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        items = new int[n];
        sizes = new int[n];
        length = n;
        for (int i = 0; i<length; i++){
            items[i] = -1;
            sizes[i] = 1;
        }

    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= length || vertex < 0){
            throw new IllegalArgumentException("the input vertex is out of range.");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        return sizes[v1];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        return items[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2){
            return true;
        }else {
            return false;
        }
    }

    public void resize(){
        for( int i = 0; i< items.length;i++){
            int root = find(i);
            sizes[i] = sizes[root];
        }
    }
    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (v1 == v2){
            return;
        }
        if (connected(v1,v2)){
            return;
        }
        // find the root;
        int root1 = find(v1);
        int root2 = find(v2);
        // calculate the size and union the two roots
        if (sizeOf(root1)<= sizeOf(root2)){
            items[root1] = root2;
            sizes[root2] += sizes[root1];
            sizes[root1] = sizes[root2];
            items[root2] = -sizes[root2];
            resize();
        }else{
            items[root2] = root1;
            sizes[root1] += sizes[root2];
            sizes[root2] = sizes[root1];
            items[root1] = -sizes[root1];
            resize();

        }

    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        if (items[vertex] < 0) {
            return vertex;
        }else{
            /*
            int root = find(items[vertex]);
            items[vertex] = root;
            return root;

             */
            return find(items[vertex]);
        }
    }

    public void printDeque(){
        int idx = 0;
        while (idx < length) {
            System.out.print(items[idx]+ " ");
            idx += 1;
        }
        System.out.println(System.lineSeparator());
    }

    public static void main(String[] args){
        UnionFind example = new UnionFind(15);
        example.union(0,2);
        example.union(3,6);
        example.union(2,3);
        example.union(1,5);
        example.union(5,4);
        example.union(2,5);
        example.union(7,9);
        example.union(10,12);
        example.union(12,5);
        example.union(13,0);
        example.union(14,7);
        example.union(9,4);
        System.out.println(example.connected(7,10));
        example.printDeque();
    }

}
