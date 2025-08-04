import java.util.ArrayList;
import java.util.List;

class Counter{
    private List<Runnable> observers = new ArrayList<>();
    private int count = 0;
    
    public void addObserver(Runnable obs){
        observers.add(obs);
    }

    public void increment(){
        count++;
        System.out.println("Counter: Value incremented to " + count);
        // Notify all observers
        for(Runnable obs: observers)
            obs.run();
    }

    public int getValue(){
        return count;
    }
}

public class ObserverPattern {
    public static void main(String[] args){
        Counter c = new Counter();

        c.addObserver(() -> System.out.println("Display A: Counter value is now " + c.getValue()));
        c.addObserver(() -> System.out.println("Display B: Value changed to " + c.getValue()));

        System.out.println("Increment 1");
        c.increment();

        System.out.println("Increment 2");
        c.increment();
    }
}
