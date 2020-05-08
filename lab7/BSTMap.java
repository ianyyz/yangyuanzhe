import edu.princeton.cs.algs4.BST;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V>  implements Map61B<K,V>{

    private Node root;

    public class Node {
        private K key;
        private V value;
        private int size;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v, int num) {
            key = k;
            value = v;
            size = num;

        }
    }


    public BSTMap(){
        this.clear();
    }


    @Override
    public void clear(){
        root = null;
    }


    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key){
        if(key == null){
            throw new IllegalArgumentException("this is an illegal argument.");
        }
        return get(key) != null;
    }


    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    private V getHelper(K key, Node p){
        if(key == null){
            throw new IllegalArgumentException("this is an illegal argument.");
        }
        if(p == null){
            return null;
        }
        int comparator = key.compareTo(p.key);
        if(comparator == 0){
            return p.value;
        }else if(comparator > 0){
            return getHelper(key, p.right);
        }else{
            return getHelper(key, p.left);
        }

    }

    @Override
    public V get(K key){
        return getHelper(key, root);
    }


    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    private Node putHelper(K key,V value, Node p){
        if(key == null){
            throw new IllegalArgumentException("this is an illegal argument here.");
        }
        if (p == null){
            p = new Node(key,value,1);
        }
        int comparator = key.compareTo(p.key);
        if(comparator == 0){
            p.value = value;
        }else if(comparator > 0){
            p.right =  putHelper(key, value, p.right);
        }else{
            p.left =  putHelper(key, value, p.left);
        }
        p.size = 1 + size(p.left) + size(p.right);
        return p;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value){
        if (key == null){
            throw new IllegalArgumentException("calls put() with a null key");
        }
        root = putHelper(key, value, root);
    }

    // copy from internet, this is very important recursion, try to learn how to exploit it.
    public void printInOrder(Node p){
        // base case
        if (p == null){
            return;
        }
        //always call the left part until we hit a base case.
        printInOrder(p.left);
        //print out the node where the base case happens
        System.out.println(p.key);
        //continue on right part.
        printInOrder(p.right);


    }

    @Override
    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet(){
        throw new UnsupportedOperationException("this operation is not supported here.");
    }


    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */

    // to find the parent of a key Node and return the corresponding node.

    private Node min(Node p){
        if (p.left == null){
            return p;
        }else{
            return min(p.left);
        }
    }

    private Node DeleteMin(Node p){
        if (p.left == null){  // if p has no left child, check its right node, connect its parents to its right node if there exists one.
            return p.right;
        }
        p.left = DeleteMin(p.left);
        p.size = size(p.right) + size(p.left) + 1; //update the size very recursion step.
        return p;


    }

    // learned from BST.java followed by my understanding.
    private Node Delete(K key, Node p){
        if(p == null){   // base case that the node is null. happens when the deleted node is the single child node without further child.
            return null;
        }
        int comparator = key.compareTo(p.key);  // set a comparator
        if (comparator <0){
            p.left = Delete(key,p.left);  //if comparator < 0, move to the left node. also reset the pointer at this step.
        }else if (comparator > 0){
            p.right = Delete(key,p.right); //if comparator > 0, move to the right node. also reset the pointer at this step.
        }else{          // if comparator == 0
            if(p.left == null){ // if the node has only one child and it is the right child then, the parent p will point to this right node.
                return p.right;
            }
            if(p.right == null){ // if the node has only one child and it is the left child then, the parent p will point to this left child.
                return p.left;
            }
            Node t = p; // this situation happens when Node p has two legs (two children). We store p as t and later p will be collected by garbage collector
            p = min(p.right); //to find the smallest node that is greater than p in order to replace p by this minimum node.
            p.right = DeleteMin(t.right); // update the right part of p where we stored as t and point new p.right to that part.
            p.left = t.left; //to point new p.left to the original left part of p where we stored as t.
        }
        p.size = size(p.left) + size(p.right) + 1; // update the size of node every time in the recursion.
        return p;


    }
    @Override
    public V remove(K key){
        if(key == null){
            throw new IllegalArgumentException("this is an illegal argument here.");
        }
        if (!containsKey(key)){
            return null;
        }
        V result = this.get(key);
        root = Delete(key,root);
        return result;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value){
        if(key == null){
            throw new IllegalArgumentException("this is an illegal argument here.");
        }
        if (!containsKey(key)){
            return null;
        }
        if (this.get(key).equals(value)){
            V result = this.get(key);
            root = Delete(key,root);
            return result;
        }else{
            return null;
        }
    }

    /* cannot figure out this part, may try it later
    private class BSTMapIterator implements Iterator<K>{


        public BSTMapIterator(){
            wizpos = 0;
        }
        public boolean hasNext(){

            return wizpos<size();
        }
        public K next(){
            K return_item = rb[wizpos];
            wizpos += 1;
            return return_item;

        }


    }

     */


    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args){
        BSTMap<Integer,Integer> a = new BSTMap<>();
        a.put(5,5);
        a.put(2,2);
        a.put(7,7);
        a.put(3,3);
        a.put(6,6);
        a.put(3,13);
        a.printInOrder(a.root);

    }

}
