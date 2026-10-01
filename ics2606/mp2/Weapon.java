package ics2606.mp2;

public class Weapon extends Item {

    private int damage;

    public Weapon(String name, double weight, int damage) {
        super(name, weight);
        this.damage = damage;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String toString() {
        return super.toString() + "Damage: " + this.damage + "\n";
    }

    @Override
    public boolean use(Object target) {
        if (!(target instanceof Person)) {
            return false;
        }

        Person p = (Person) target;

        System.out.println("Attack " + p.getName() + " with " + getName() + " for " + this.damage + " damage!");

        if (p.defends(this.damage)) {
            System.out.println(p.getName() + " lives!");
        } else {
            System.out.println(p.getName() + " is dead!");
        }

        return true;
    }
}