public class Flight{
    int start;
    int end;
    int passenger;


    public Flight(int s, int e, int p){
        start =s;
        end = e;
        passenger = p;
    }
    public int getPassenger(){
        return this.passenger;
    }

    public int getStart(){
        return this.start;
    }

    public int getEnd(){
        return this.end;
    }

    public static void main(String[] args){
        Flight obj = new Flight(0,0,0);
    }

}
