package bearmaps;
import java.util.*;

public class KDTree<V> implements PointSet {
    private Node root;
    private static final boolean horizontal = false;
    private HashSet<Point> set = new HashSet<>();


    public class Node {
        private Point item;
        private boolean orientation;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(Point p,boolean orientation) {
            this.item = p;
            this.orientation = orientation;
        }

        public double distance(Point goal){
            return Math.sqrt(Math.pow((item.getX()-goal.getX()),2) + Math.pow(item.getY()-goal.getY(),2));
        }

        public double bestDistanceOfBadSide(Point goal){
            if(orientation == horizontal){
                return Math.abs(item.getX()-goal.getX());
            }else{
                return Math.abs(item.getY()-goal.getY());
            }
        }

    }

    public KDTree(List<Point> points){
        root = null;
        boolean ori = horizontal;
        for(int i = 0; i < points.size(); i++){
            if(!set.contains(points.get(i))){
                set.add(points.get(i));
                if (i == 0){
                    root = add(points.get(i),null,ori);
                }else{
                    add(points.get(i),root,ori);
                }
            }

        }
    }

    private int compare(Point a, Point b, boolean ori){
        double diff;
        if (ori == horizontal){
            diff = Double.compare(a.getX(),b.getX());
        }else{
            diff = Double.compare(a.getY(),b.getY());
        }
        if(diff >0) return 1;
        else if(diff < 0) return -1;
        else return 0;
    }

    private Node add(Point point, Node root,boolean ori){
        if ( point == null){
            throw new IllegalArgumentException();
        }
        if (root == null){
            Node newNode = new Node(point,ori);
            return newNode;
        }
        root.orientation = ori;
        int comparator = compare(point,root.item,root.orientation);
        if(comparator >= 0){
            root.right = add(point, root.right,!root.orientation);
        }else{
            root.left =  add(point, root.left, !root.orientation);
        }
        return root;
    }


    @Override
    public Point nearest(double x, double y){
        Point goal = new Point(x,y);
        Point result = nearest(root,goal,root).item;
        return result;
    };

    private int sideComparator(Node n, Point goal, boolean ori){
        if(ori == horizontal){
            if(goal.getX() < n.item.getX()){
                return 1;
            }else{
                return -1;
            }
        }else{
            if(goal.getY() < n.item.getY()){
                return 1;
            }else{
                return -1;
            }
        }
    }


    private Node nearest(Node n,Point goal,Node best){
        Node goodSide;
        Node badSide;
        if(goal == null){
            throw new UnsupportedOperationException();
        }
        if(n == null){
            return best;
        }
        if(n.distance(goal) < best.distance(goal)){
            best = n;
        }
        if(sideComparator(n,goal,n.orientation) > 0){
            goodSide = n.left;
            badSide = n.right;
        }else{
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearest(goodSide,goal,best);
        if(best.distance(goal)>n.bestDistanceOfBadSide(goal)){
            best = nearest(badSide,goal,best);
        }
        return best;
    }



    // this is the naive version of nearest which we have not use any pruning.
    public Node NaiveNearest(Node n,Point goal,Node best){
        if(goal == null){
            throw new UnsupportedOperationException();
        }
        if(n == null){
            return best;
        }
        if(n.distance(goal) < best.distance(goal)){
            best = n;
        }
        best = NaiveNearest(n.left,goal,best);
        best = NaiveNearest(n.right,goal,best);
        return best;
    }

    public static void main(String[] args){
        Point p1 = new Point(2, 3 ); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kdtree = new KDTree(List.of(p1, p2, p3,p4,p5,p6,p7));
        Point goal = new Point(0.0,7.0);

        Point ret = kdtree.NaiveNearest(kdtree.root,goal,kdtree.root).item; // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY());
    }


}
