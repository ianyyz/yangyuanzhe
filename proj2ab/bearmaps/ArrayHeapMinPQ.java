package bearmaps;
import java.lang.management.MonitorInfo;
import java.util.*;
import org.junit.*;

/*  some idea after finish the ArrayHeapMinPQ:
    1: we need to utilize recursion to help us to traversal the tree.
    2: don't get constrained by the very big picture, focus on each node. eg. don't deliberately pick the final destination of a Node
    Let the Node judge and promote or demote by itself.
    3: take the advantage of build in data structures, for example, we use HashMap to store the item and its index, which enables
    us to get a time complexity O(logN)


 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> MinHeapList;
    private HashMap<T,Integer> map;
    public int size;

    public ArrayHeapMinPQ(){
        MinHeapList = new ArrayList<>();
        size = 0;
        map = new HashMap<>();

    }

    // a node class which contains an item and its corresponding priority
    private class Node{
        public T item;
        public double priority;

        public Node(T item, double priority){
            this.item = item;
            this.priority = priority;
        }

    }

    //used to check whether a demote is necessary.
    private boolean checkProperty(int k){
        int left = leftChild(k);
        int right = rightChild(k);

        if (left == -1 && right == -1) return true; // if there is no children, no need to demote.
        if (left != -1 && right == -1){  //if there is only left child (because of the heap identity)
            double diff = MinHeapList.get(k).priority - MinHeapList.get(left).priority;
            return diff < 0; // compare the priority of child and parent.
        }
        // if there are two children
        double diffLeft = MinHeapList.get(k).priority - MinHeapList.get(left).priority;
        double diffRight = MinHeapList.get(k).priority - MinHeapList.get(right).priority;
        return (diffLeft <0) && (diffRight<0); // no need demote iff left child and right child are both smaller than parent k.
    }

    // return the parent of an index k
    private int parent(int k){
        if ((k-1)/2 <0) {
            return -1;
        }else {
            return (k - 1) / 2;
        }
    }

    //return the leftChild of an index k
    private int leftChild(int k){
        if(2*k + 1 <= size -1 ) {
            return 2 * k + 1;
        }else{
            return -1;
        }
    }

    // return the rightChild of an index k
    private int rightChild(int k){
        if(2*k +2 <= size -1){
            return 2*k +2;
        }else {
            return -1;
        }
    }

    // add a new node to the heapArray.
    @Override
    public void add(T item, double priority){
        if(contains(item)){
            throw new IllegalArgumentException();
        }
        Node newItem = new Node(item,priority);
        MinHeapList.add(newItem); // add the item in the last spot.
        map.put(newItem.item,size); // add the item as the key and record the spot as its value into a hashmap
        promote(size); //check the priority and decide if we want to swim up.
        size += 1;// update the size.

    };

    private void promote(int k){
        int parent = parent(k);
        if (k == 0){ // if k itself is the upmost parent (smallest item), return
            return;
        }
        if(MinHeapList.get(k).priority > MinHeapList.get(parent).priority){
            return; // if k's priority is bigger than its parent's, nothing happens, we are good.
        }else{
            Collections.swap(MinHeapList,k,parent);  // else, use swap to swap the two spots
            map.put(MinHeapList.get(parent).item,parent); // update the map, so that we still know where is the location of item in our heapArray.
            map.put(MinHeapList.get(k).item,k); // same as above.
            promote(parent); // recursive loop to check if we can swim up further.
        }
    }

    /* Returns true if the PQ contains the given item. */

    // take the advantage of hashmap with time complexity O(logN), we can search the item in O(logN).
    @Override
    public boolean contains(T item){
        return map.containsKey(item);

    };
    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */

    // simply return the item belongs to first Node in our HeapArray.
    @Override
    public T getSmallest(){
        if (size == 0){
            throw new NoSuchElementException();
        }
        return MinHeapList.get(0).item;
    };

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest(){
        if (size == 0){
            throw new NoSuchElementException();
        }
        Collections.swap(MinHeapList,0,size-1);  // swap the first element and last element in the arraylist
        Node result = MinHeapList.remove(size-1); // now, remove the last element which suppose to be the smallest.
        size -= 1; // update the size
        map.remove(result.item); //update the map
        demote(0);
        return result.item;

    };

    private void demote(int k){
        if (checkProperty(k) == true){ // check whether demote is necessary
            return;
        }
        int left = leftChild(k);
        int right = rightChild(k);
        if (left !=-1 && right == -1){ // if only left child exist
            Collections.swap(MinHeapList,k,left); // swap left child and k
            map.put(MinHeapList.get(left).item,left); // update the hashmap
            map.put(MinHeapList.get(k).item,k);
        }else{  // if there are two children for node k
            if(MinHeapList.get(right).priority > MinHeapList.get(left).priority){ // if left child has less priority
                Collections.swap(MinHeapList,k,left); // swap left and k
                map.put(MinHeapList.get(left).item,left); // update map
                map.put(MinHeapList.get(k).item,k);
                demote(left);
            }else{
                Collections.swap(MinHeapList,k,right); // swap right and k
                map.put(MinHeapList.get(right).item,right); // update map
                map.put(MinHeapList.get(k).item,k);
                demote(right);
            }
        }
    }
    /* Returns the number of items in the PQ. */
    @Override
    // simply return size
    public int size(){
        return size;

    };
    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority){
        if (!contains(item)){
            throw new NoSuchElementException();
        }
        int i = map.get(item); // get the corresponding index of the item through hashmap in time complexity O(logN)
        double oldPriority = MinHeapList.get(i).priority; // record old priority
        MinHeapList.get(i).priority = priority; // update new priority;
        // adjust the HeapArray by promote or demote the changed Node.
        if (oldPriority < priority) {
            demote(i);
        } else {
            promote(i);
        }
    };
}
