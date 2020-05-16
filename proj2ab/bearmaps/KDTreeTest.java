package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private static Random r = new Random(500);

    private static KDTree buildLectureTree(){
        Point p1 = new Point(2, 3 ); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kdtree = new KDTree(List.of(p1, p2, p3,p4,p5,p6,p7));
        return kdtree;
    }

    private static void buildTreeWithDoubles(){
        Point p1 = new Point(2,3);
        Point p2 = new Point(2,3);
        KDTree kdtree = new KDTree(List.of(p1,p2));

    }

    @Test
    public void testNearestDemo(){
        KDTree kdTree = buildLectureTree();
        Point expected = new Point(1,5);
        Point actual = kdTree.nearest(0,7);
        assertEquals(expected,actual);
    }

    private Point OneRandomPoint(){
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x,y);


    }
    private List<Point> randomPointsGenerator(int num){
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < num; i++){
            points.add(OneRandomPoint());
        }
        return points;
    }

    private void testWithNPointsAndQQueries(int pointCount, int queryCount) {
        List<Point> points = randomPointsGenerator(pointCount);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPointsGenerator(queryCount);
        for (Point p : queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testWith1000PointsAnd200Queries() {
        int pointCount = 1000;
        int queryCount = 200;
        testWithNPointsAndQQueries(pointCount, queryCount);
    }

    @Test
    public void testWith10000PointsAnd2000Queries() {
        int pointCount = 10000;
        int queryCount = 2000;
        testWithNPointsAndQQueries(pointCount, queryCount);
    }

    @Test
    public void compareTimingOfNaiveVsKDTreeLikeTheSpec() {
        List<Point> randomPoints = randomPointsGenerator(100000);
        KDTree kd = new KDTree(randomPoints);
        NaivePointSet nps = new NaivePointSet(randomPoints);
        List<Point> queryPoints = randomPointsGenerator(10000);

        Stopwatch sw = new Stopwatch();
        double time = sw.elapsedTime();
        /*
        for (Point p : queryPoints) {
            nps.nearest(p.getX(), p.getY());
        }

        System.out.println("Naive 10000 queries on 100000 points: " + time);

         */

        sw = new Stopwatch();
        for (Point p : queryPoints) {
            kd.nearest(p.getX(), p.getY());
        }
        time = sw.elapsedTime();
        System.out.println("KDTree 10000 queries on 100000 points: " + time);
    }

}
