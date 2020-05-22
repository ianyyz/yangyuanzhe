import edu.princeton.cs.algs4.Queue;

import java.beans.BeanDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    public List<Bear> solvedBears = new ArrayList<>();
    public List<Bed> solvedBeds = new ArrayList<>();

    private static List<Bear> BearConcatenate(List<Bear> l1, List<Bear> l2){
        l1.addAll(l2);
        return l1;
    }

    private static List<Bed> BedConcatenate(List<Bed> l1, List<Bed> l2){
        l1.addAll(l2);
        return l1;
    }

    private static int getRandomInd(List items) {
        int pivotIndex = (int) (Math.random() * items.size());
        return pivotIndex;
    }

    private static void partitionBear(
            List<Bear> unsortedBear, Bed pivot,
            List<Bear> less, List<Bear> equal, List<Bear> greater) {
        for (Bear i:unsortedBear){
            if(i.compareTo(pivot) < 0){
                less.add(i);
            }else if (i.compareTo(pivot) == 0){
                equal.add(i);
            }else{
                greater.add(i);
            }
        }
    }

    private static void partitionBed(
            List<Bed> unsortedBed, Bear pivot,
            List<Bed> less, List<Bed> equal, List<Bed> greater) {
        for (Bed i:unsortedBed){
            if(i.compareTo(pivot) < 0){
                less.add(i);
            }else if (i.compareTo(pivot) == 0){
                equal.add(i);
            }else{
                greater.add(i);
            }
        }
    }



    private static List<Bed> quickSortBed(List<Bed> unsortedBed,List<Bear> unsortedBear) {
        if (unsortedBed.size() <= 1){
            return unsortedBed;
        }
        List<Bed> lessBed = new ArrayList<>();
        List<Bed> equalBed = new ArrayList<>();
        List<Bed> greaterBed = new ArrayList<>();
        List<Bear> lessBear = new ArrayList<>();
        List<Bear> equalBear = new ArrayList<>();
        List<Bear> greaterBear = new ArrayList<>();

        Bear pivot = unsortedBear.get(getRandomInd(unsortedBear));
        partitionBed(unsortedBed,pivot,lessBed,equalBed,greaterBed);
        partitionBear(unsortedBear,equalBed.get(0),lessBear,equalBear,greaterBear);
        List<Bed> lessOrEqual = BedConcatenate(quickSortBed(lessBed,lessBear),equalBed);
        List<Bed> finalList = BedConcatenate(lessOrEqual,quickSortBed(greaterBed,greaterBear));
        return finalList;
    }

    private static List<Bear> quickSortBear(List<Bed> unsortedBed,List<Bear> unsortedBear) {
        if (unsortedBear.size() <= 1){
            return unsortedBear;
        }
        List<Bed> lessBed = new ArrayList<>();
        List<Bed> equalBed = new ArrayList<>();
        List<Bed> greaterBed = new ArrayList<>();
        List<Bear> lessBear = new ArrayList<>();
        List<Bear> equalBear = new ArrayList<>();
        List<Bear> greaterBear = new ArrayList<>();

        Bed pivot = unsortedBed.get(getRandomInd(unsortedBed));
        partitionBear(unsortedBear,pivot,lessBear,equalBear,greaterBear);
        partitionBed(unsortedBed,equalBear.get(0),lessBed,equalBed,greaterBed);
        List<Bear> lessOrEqual = BearConcatenate(quickSortBear(lessBed,lessBear),equalBear);
        List<Bear> finalList = BearConcatenate(lessOrEqual,quickSortBear(greaterBed,greaterBear));
        return finalList;
    }

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        solvedBeds = quickSortBed(beds,bears);
        solvedBears = quickSortBear(beds,bears);



    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return solvedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return solvedBeds;
    }
}
