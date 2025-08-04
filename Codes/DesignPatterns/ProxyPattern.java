interface Door{
    void open();
    void close();
}

class SecureDoor implements Door{
    public void open(){
        System.out.println("Door Opened");
    }
    public void close(){
        System.out.println("Door Closed");
    }
}

class ProxyDoor implements Door{
    private SecureDoor door;
    private int pin;

    ProxyDoor(int pin){
        this.door = new SecureDoor();
        this.pin = pin;
    }

    // considering the pin is given as a parameter "inputPin"
    public void open(){
        int inputPin = 123;
        if(this.pin == inputPin)
            door.open();
        else
            System.out.println("Access Denied, Incorrect Password");
    }

    public void close(){
        int inputPin = 123;
        if(this.pin == inputPin)
            door.close();
        else
            System.out.println("Access Denied, Incorrect Password");
    }
}

public class ProxyPattern {
    public static void main(String[] args){
        Door door = new ProxyDoor(123);
        door.open();
        door.close();
    }
}
