package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double SolutionWeight;
    private double timeSpent;
    private int numStatesExplored;
    private Vertex goal;
    private Vertex start;
    public ArrayHeapMinPQ<Vertex> priorityQ;
    private HashMap<Vertex,Double>  distTo = new HashMap<>();
    private HashMap<Vertex,Vertex>  edgeTo = new HashMap<>();
    private List<Vertex> solution = new LinkedList<>();


    private List<Vertex> getSolutionHelper(HashMap<Vertex,Vertex> map,LinkedList<Vertex> solutionList){
        Vertex cur = goal;
        while (!cur.equals(start)){
            solutionList.addFirst(cur);
            cur = map.get(cur);
        }
        solutionList.addFirst(start);
        return solutionList;
    }

    private void relax(AStarGraph<Vertex> input,Vertex e){
        for (WeightedEdge<Vertex> edge: input.neighbors(e)){
            Vertex start = edge.from();
            Vertex end = edge.to();
            double weight = edge.weight();
            if (!distTo.containsKey(end)){
                distTo.put(end,Double.POSITIVE_INFINITY);
            }
            if(distTo.get(start) + weight < distTo.get(end)){
                distTo.put(end,distTo.get(start) + weight);
                edgeTo.put(end,e);
                if (priorityQ.contains(end)){
                    priorityQ.changePriority(end,distTo.get(end)+input.estimatedDistanceToGoal(end,goal));
                }
                if (!priorityQ.contains(end)){
                    priorityQ.add(end,distTo.get(end)+input.estimatedDistanceToGoal(end,goal));
                }
            }
        }

    }


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout){
        Stopwatch sw = new Stopwatch(); // start the stopwatch
        numStatesExplored = 0;
        priorityQ = new ArrayHeapMinPQ<>();
        priorityQ.add(start,input.estimatedDistanceToGoal(start,end));
        goal = end;
        this.start = start;
        distTo.put(start,0.0);
        while (priorityQ.size() > 0 && sw.elapsedTime() <= timeout ){
            Vertex cur = priorityQ.getSmallest();

            if(!cur.equals(goal)) {
                priorityQ.removeSmallest();
                relax(input,cur);
                numStatesExplored += 1;
            }else{
                SolutionWeight = distTo.get(goal);
                outcome = SolverOutcome.SOLVED;
                solution = getSolutionHelper(edgeTo,(LinkedList) solution);
                return;
            }
        }
        timeSpent = sw.elapsedTime();
        if(timeSpent >= timeout){
            outcome = SolverOutcome.TIMEOUT;
        }else if(priorityQ.size() == 0){
            outcome = SolverOutcome.UNSOLVABLE;
        }

    }

    @Override
    public SolverOutcome outcome(){
        return outcome;

    }

    @Override
    public List<Vertex> solution(){
        if (outcome == SolverOutcome.TIMEOUT || outcome == SolverOutcome.UNSOLVABLE){
            return new LinkedList<>();
        }else{
            return solution;
        }
    }

    @Override
    public double solutionWeight(){
        return SolutionWeight;

    }

    @Override
    public int numStatesExplored(){
        return numStatesExplored;

    }

    @Override
    public double explorationTime(){
        return timeSpent;
    }
}
