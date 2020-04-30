package es.datastructur.synthesizer;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    @Override
    public int capacity(){
        return rb.length;
    }

    @Override
    public int fillCount(){
        return fillCount;

    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (fillCount >= rb.length){
            throw new RuntimeException("Ring Buffer Overflow");
        }
        fillCount += 1;
        if(last == rb.length-1){
            rb[last] = x;
            last = 0;
        }else{
            rb[last] = x;
            last += 1;
        }

    }

    @Override
    public boolean equals(Object other){
        if (other == null){
            return false;
        }
        if(other.getClass() != this.getClass()){
            return false;
        }
        ArrayRingBuffer<T> o = (ArrayRingBuffer<T>) other;
        if(o.fillCount != this.fillCount){
            return false;
        }
        for (int i = 0; i<o.fillCount; i+= 1){
            if(rb[i] != o.rb[i]){
                return false;
            }
        }
        return true;



    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        T return_item;
        if (fillCount == 0){
            throw new RuntimeException("Ring Buffer Underflow");
        }
        fillCount -= 1;
        if(first == rb.length-1){
            return_item = rb[first];
            rb[first] = null;
            first = 0;
        }else {
            return_item = rb[first];
            rb[first] = null;
            first += 1;
        }
        return return_item;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        T return_item = rb[first];
        if (fillCount == 0){
            throw new RuntimeException("Ring Buffer Underflow");
        }
        return return_item;

    }

    @Override
    public Iterator<T>  iterator(){
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int wizpos;

        public ArrayRingBufferIterator(){
            wizpos = 0;
        }
        public boolean hasNext(){
            return wizpos<fillCount;
        }
        public T next(){
            T return_item = rb[wizpos];
            wizpos += 1;
            return return_item;

        }

    }
    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
