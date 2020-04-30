package es.datastructur.synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    public int capacity();

    public int fillCount();

    public void enqueue(T x);

    public T dequeue();

    public T peek();

    Iterator<T> iterator();

    default boolean isEmpty(){
        return true;
    }

    default boolean isFull(){
        return true;
    }
}
