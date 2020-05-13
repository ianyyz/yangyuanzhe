import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FlightSolver{
    private ArrayList<Flight> flights;
    public PriorityQueue<Flight> prStart;
    public PriorityQueue<Flight> prEnd;

    Comparator<Flight> StartComp = (f1,f2) ->{
        int diff = f1.getStart() - f2.getStart();
        return diff;
    };

    Comparator<Flight> EndComp = (f1,f2) -> {
        int diff = f1.getEnd() - f2.getEnd();
        return diff;
    };




    public FlightSolver(ArrayList<Flight> flights){
        this.flights = flights;
        prStart = new PriorityQueue<>(flights.size(),StartComp);
        prEnd = new PriorityQueue<>(flights.size(),EndComp);

        for (Flight f: flights){
            prStart.add(f);
            prEnd.add(f);
        }
    }

    public int solve() {
        int max = 0;
        int cur = 0;
        while (prEnd.size() > 0 && prStart.size() > 0) {
            int startTime = prStart.peek().getStart();
            int endTime = prEnd.peek().getEnd();
            if (startTime < endTime) {
                cur += prStart.poll().getPassenger();
            } else if (startTime > endTime) {
                cur -= prEnd.poll().getPassenger();
            } else {
                cur += prStart.poll().getPassenger() - prEnd.poll().getPassenger();
            }
            if (cur > max) {
                max = cur;
            }
        }
        return max;
    }
}
