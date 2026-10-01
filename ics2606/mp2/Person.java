package ics2606.mp2;

public class Person {

    private static final int MAX_HEALTH = 100;

    private String name;
    private int health;

    public Person(String name) {
        this.name = name;
        this.health = MAX_HEALTH;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nHealth: " + this.health + "\n";
    }

    public boolean isAlive() {
        return this.health != 0;
    }

    public boolean heal(int boost) {
        if (!isAlive()) {
            return false;
        }

        this.health = Math.max(0, Math.min(this.health + boost, MAX_HEALTH));
        return true;
    }

    public boolean defends(int damage) {
        this.health = Math.max(0, Math.min(this.health - damage, MAX_HEALTH));
        return isAlive();
    }
}