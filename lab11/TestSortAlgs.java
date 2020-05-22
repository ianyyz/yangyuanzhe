import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;
public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        tas.enqueue("Eon");
        tas.enqueue("Eon");
        Queue<String> sortedTas = QuickSort.quickSort(tas);
        for( String item: sortedTas){
            System.out.println(item);
        }
        while (sortedTas.size() > 1){
            assertTrue(sortedTas.dequeue().compareTo(sortedTas.peek()) <= 0);
        }
        assertTrue(isSorted(sortedTas));

    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        tas.enqueue("Eon");
        Queue<String> sortedTas = MergeSort.mergeSort(tas);
        for( String item: sortedTas){
            System.out.println(item);
        }
        while (sortedTas.size() > 1){
            assertTrue(sortedTas.dequeue().compareTo(sortedTas.peek()) <= 0);
        }
        assertTrue(isSorted(sortedTas));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
