package bearmaps;
import java.util.*;

public class NaivePointSet implements PointSet {
    public List<Point> points;
    public PriorityQueue<Point> pointQuene;
    public Comparator<Point> comp;
    public NaivePointSet(List<Point> points){
        this.points = points;

    };
    @Override
    public Point nearest(double x, double y){
        comp = (f1,f2) ->{
            double dis1 = Math.sqrt(Math.pow((f1.getX()-x),2) + Math.pow(f1.getY()-y,2));
            double dis2 = Math.sqrt(Math.pow((f2.getX()-x),2) + Math.pow(f2.getY()-y,2));
            double diff = dis1-dis2;
            if(diff > 0){
                return 1;
            }else if(diff ==0){
                return 0;
            }else{
                return -1;
            }
        };
        this.pointQuene = new PriorityQueue<>(points.size(),comp);
        for (Point p: points){
            pointQuene.add(p);
        }
        return pointQuene.peek();
    }

    public static void main(String[] args){
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY());
    }



}
