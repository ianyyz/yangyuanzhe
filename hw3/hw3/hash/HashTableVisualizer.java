package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class HashTableVisualizer {

    public static void main(String[] args) {
        /* scale: StdDraw scale
           N:     number of items
           M:     number of buckets */

        /* After getting your simpleOomages to spread out
           nicely, be sure to try
           scale = 0.5, N = 2000, M = 100. */

        /* this is the original part.

        double scale = 1.0;
        int N = 1000;
        int M = 10;

        HashTableDrawingUtility.setScale(scale);
        List<Oomage> oomies = new ArrayList<>();
        for (int i = 0; i < N; i += 1) {
   //         oomies.add(SimpleOomage.randomSimpleOomage());
            oomies.add(ComplexOomage.randomComplexOomage());
        }


         */

        // this part is the visualizer of a DeadlyList of the ComplexOomage, this show how the HashCode() that implemented in
        // ComplexOomage fails to do its job. the major reason is it cannot tell the different between two Integer Lists if they
        // have the same last four digits.
        double scale = 1.0;
        int N = 1000;
        int M = 10;

        HashTableDrawingUtility.setScale(scale);

        List<Oomage> deadlyList = new ArrayList<>();
        ArrayList<Integer> fixedFourDigit = new ArrayList<>(4);
        fixedFourDigit.add(1);
        fixedFourDigit.add(2);
        fixedFourDigit.add(3);
        fixedFourDigit.add(4);
        for (int i = 0; i < N; i+=1){
            ComplexOomage item = ComplexOomage.randomComplexOomage();
            List<Integer> itemParam = item.params;
            itemParam.addAll(fixedFourDigit);
            deadlyList.add(new ComplexOomage(itemParam));
        }
        visualize(deadlyList, M, scale);
    }



    public static void visualize(List<Oomage> oomages, int M, double scale) {
        HashTableDrawingUtility.drawLabels(M);
        int[] numInBucket = new int[M];
        for (Oomage s : oomages) {
            int bucketNumber = (s.hashCode() & 0x7FFFFFFF) % M;
            double x = HashTableDrawingUtility.xCoord(numInBucket[bucketNumber]);
            numInBucket[bucketNumber] += 1;
            double y = HashTableDrawingUtility.yCoord(bucketNumber, M);
            s.draw(x, y, scale);
        }
    }
} 
