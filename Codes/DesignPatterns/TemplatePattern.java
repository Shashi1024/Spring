abstract class BeverageMaker {

    public final void prepareBeverage() {
        boilWater();
        brew();         
        pourInCup();
        addCondiments(); 
        System.out.println("Beverage is ready!\n");
    }

    private void boilWater() {
        System.out.println("Boiling water.");
    }

    private void pourInCup() {
        System.out.println("Pouring into cup.");
    }


    protected abstract void brew();
    protected abstract void addCondiments();
}


class TeaMaker extends BeverageMaker {
    @Override
    protected void brew() {
        System.out.println("Steeping the tea bag.");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding lemon.");
    }
}

class CoffeeMaker extends BeverageMaker {
    @Override
    protected void brew() {
        System.out.println("Dripping coffee through filter.");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding sugar and milk.");
    }
}


public class TemplatePattern {
    public static void main(String[] args) {
        System.out.println("--- Making Tea ---");
        BeverageMaker tea = new TeaMaker();
        tea.prepareBeverage(); 

        System.out.println("--- Making Coffee ---");
        BeverageMaker coffee = new CoffeeMaker();
        coffee.prepareBeverage(); 
    }
}

