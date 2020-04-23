public class LinkedListDeque <T> {
    private Node sentinel;
    private int size;



    private class Node<T>{
        public T item;
        public Node prev;
        public Node next;
        public Node(T i, Node p,Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(null, null, null); //create a new Node with string instance;
        sentinel.prev = sentinel; // sentinel.prev is pointing back to sentinel
        sentinel.next = sentinel.prev; //sentinel.next is pointing to sentinel.prev

    }

    public void addFirst(T i){
        if (isEmpty()) {
            Node cur_node = new Node(i, sentinel, sentinel.next); // create a new Node where Node.prev = sentinel and Node.next = sentinel.next
            sentinel.next = cur_node;
            sentinel.prev = sentinel.next;
        } else {
            Node cur_node = new Node(i, sentinel, sentinel.next);
            sentinel.next = cur_node;
        }
        size += 1;
    }

    public void addLast( T i){
        if (isEmpty()) {
            Node cur_node = new Node(i,sentinel.prev,sentinel); // create a new Node where Node.prev = sentinel and Node.next = sentinel.next
            sentinel.next = cur_node;
            sentinel.prev = cur_node;
        } else {
            Node prev_node = sentinel.prev;
            Node cur_node = new Node(i,prev_node,sentinel);
            prev_node.next = cur_node;
            sentinel.prev = cur_node;
        }
        size += 1;

    }

    public boolean isEmpty(){
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return size;
    }


    public void printDeque() {
        Node cur_node = sentinel;
        int idx = 1;
        while (idx <= size) {
            System.out.print(cur_node.next.item+ " ");
            cur_node = cur_node.next;
            idx += 1;
        }
        System.out.println(System.lineSeparator());

    }


    public T removeFirst() {
        if (isEmpty()){
            return null;
        }else {
            Node<T> first_node = sentinel.next;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return first_node.item;
        }
    }

    public T removeLast() {
        if(isEmpty()){
            return null;
        }else {
            Node<T> last_node = sentinel.prev;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return last_node.item;

        }
    }

    public T get( int index){
        if (size == 0){
            return null;
        }
        if (index >= size){
            return null;
        }
        Node<T> cur_node = sentinel;
        int idx = 0;
        while (idx <= index){
            cur_node = cur_node.next;
            idx += 1;
        }
        return cur_node.item;

    }

    private Node recurse(int index){
        if (index == -1){
            return sentinel;
        }else {
            Node start_node = this.recurse(index-1).next;
            return start_node;
        }
    }
    public T getRecursive(int index){
        if (size == 0){
            return null;
        }
        if (index >= size){
            return null;
        }
        if (index + 1 > this.size) {
            return null;
        }else {
            Node<T> want_node = this.recurse(index);
            return want_node.item;
        }
    }


    public LinkedListDeque(LinkedListDeque other){
        sentinel = new Node(null,null,null);
        size = 0;
        for(int i= 0; i<other.size(); i += 1){
            addLast((T)other.get(i));
        }

    }



    /*
    public static void main(String[] args){
        LinkedListDeque<Integer> l = new LinkedListDeque<Integer>();
        for (int i = 0; i<10;i +=1){
            l.addLast(i);
        }

        l.addFirst(3);
        l.addFirst(2);
        l.addLast(4);
        l.addLast(5);
        l.addFirst(1);
        l.addLast(6);    //'1334','like','ianyyz','cs61b','okkk','hw2'
        System.out.println( l.getRecursive(10));
        LinkedListDeque<Integer> new_copy = new LinkedListDeque<Integer>(l);
        l.removeLast();
        new_copy.printDeque();
        System.out.println(l.size());
        l.printDeque();
    }


    
     */
}
