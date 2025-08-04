public class SingletonPattern{
    private static SingletonPattern instance = null;

    private SingletonPattern(){
        // private constructor
    }

    public static SingletonPattern getInstance(){
        if(instance == null)
            instance = new SingletonPattern();
        
        return instance;
    }

    public void method(){
        System.out.println("Method invoked");
    }

    public static void main(String[] args){
        SingletonPattern sp = getInstance();
        sp.method();
    }
}