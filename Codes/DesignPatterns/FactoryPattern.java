interface Product{
    String getName();
}

class ProductA implements Product{
    public String getName(){
        return "Product A";
    }
}

class ProductB implements Product{
    public String getName(){
        return "Product B";
    }
}

abstract class Creator{
    public abstract Product factoryMethod();

    public void someOperation(){
        Product p = factoryMethod();
        System.out.println("Creator is working with: " + p.getName());
    }
}

class CreatorA extends Creator{
    public Product factoryMethod(){
        return new ProductA();
    }
}

class CreatorB extends Creator{
    public Product factoryMethod(){
        return new ProductB();
    }
}



public class FactoryPattern {
    public static void main(String[] args){
        Creator ca = new CreatorA();
        ca.someOperation();

        Creator cb = new CreatorA();
        cb.someOperation();
    }
}



// or simply we can use simple factory method

// class SimpleFactory {
//     public static Product createProduct(String type) {
//         if (type.equals("A")) {
//             return new ProductA();
//         } else if (type.equals("B")) {
//             return new ProductB();
//         }
//         throw new IllegalArgumentException("Unknown product type");
//     }
// }