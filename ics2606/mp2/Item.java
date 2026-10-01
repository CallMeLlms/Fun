package ics2606.mp2;

public class Item {

    private String name;
    private double weight;

    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nWeight: " + this.weight + "\n";
    }

    public boolean use(Object target) {
        System.out.println("Not usable");
        return false;
    }
}