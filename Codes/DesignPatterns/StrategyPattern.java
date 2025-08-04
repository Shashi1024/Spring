interface RouteStrategy{
    void buildRoute(String start, String end);
}

class CarRoute implements RouteStrategy{
    public void buildRoute(String start, String end){
        System.out.println("Car Route from "+start+" to "+end);
    }
}

class WalkingRoute implements RouteStrategy{
    public void buildRoute(String start, String end){
        System.out.println("Walking Route from "+start+" to "+end);
    }
}

class BicycleRoute implements RouteStrategy{
    public void buildRoute(String start, String end){
        System.out.println("Bicycle Route from "+start+" to "+end);
    }
}

class Navigator{
    private RouteStrategy strategy;

    public void setRouteStrategy(RouteStrategy rs){
        strategy = rs;
    }

    public void navigate(String start, String end){
        if (strategy == null) {
            System.out.println("Navigator: No route strategy set!");
            return;
        }
        System.out.print("Navigator: Calculating route... ");
        strategy.buildRoute(start, end);
    }
}


public class StrategyPattern {
    public static void main(String[] args){
        Navigator n = new Navigator();
        n.setRouteStrategy(new CarRoute());
        n.navigate("Nagole", "Raidurg");

    }
}
