package ics2606.mp2;

public class Food extends Item {

    private int health;

    public Food(String name, double weight, int health) {
        super(name, weight);
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return super.toString() + "Health: " + this.health + "\n";
    }

    @Override
    public boolean use(Object target) {
        if (!(target instanceof Person)) {
            return false;
        }

        Person p = (Person) target;

        if (p.heal(this.health)) {
            System.out.println(p.getName() + " ate " + getName() + " for " + this.health + " health!");
            return true;
        }

        System.out.println(p.getName() + " cannot be healed");
        return false;
    }
}