package bearmaps.proj2ab;
import java.util.*;

public class KDTree implements PointSet {
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

        // did not generalize the situation where we have have in AugmentedStreetMapGraph
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


}
