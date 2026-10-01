package ics2606.mp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Derived from the written requirements for the Weapon class.
 * Weapon must not store its own name or weight; both come from Item.
 */
class WeaponTest {

    // --- inheritance ---------------------------------------------------

    @Test
    void weapon_isAnItem() {
        assertTrue(new Weapon("BFG", 28.1, 900) instanceof Item);
    }

    @Test
    @DisplayName("name and weight are inherited from Item, not duplicated")
    void constructor_storesNameAndWeightViaItem() {
        Weapon weapon = new Weapon("BFG", 28.1, 900);
        assertEquals("BFG", weapon.getName());
        assertEquals(28.1, weapon.getWeight());
        assertEquals(900, weapon.getDamage());
    }

    // --- damage accessors ----------------------------------------------

    @Test
    void setDamage_updatesStoredDamage() {
        Weapon weapon = new Weapon("Sting", 1.5, 30);
        weapon.setDamage(60);
        assertEquals(60, weapon.getDamage());
    }

    // --- toString ------------------------------------------------------

    @Test
    @DisplayName("toString returns the three-line Name/Weight/Damage format")
    void toString_exactFormatAndNoOutput() {
        Weapon weapon = new Weapon("BFG", 28.1, 900);

        CaptureStdOut capture = CaptureStdOut.start();
        String result = weapon.toString();
        String printed = capture.stop();

        assertEquals("Name: BFG\nWeight: 28.1\nDamage: 900\n", result);
        assertEquals("", printed, "toString must not print");
    }

    // --- use -----------------------------------------------------------

    @Nested
    @DisplayName("use returns false and prints nothing when the target is not a Person")
    class UseOnNonPerson {

        @Test
        void onNull() {
            Weapon weapon = new Weapon("Sting", 1.5, 30);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = weapon.use(null);
            String printed = capture.stop();

            assertFalse(result);
            assertEquals("", printed);
        }

        @Test
        void onAnItem() {
            Weapon weapon = new Weapon("Sting", 1.5, 30);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = weapon.use(new Item("Book", 5.3));
            String printed = capture.stop();

            assertFalse(result);
            assertEquals("", printed);
        }

        @Test
        void onAFood() {
            Weapon weapon = new Weapon("Sting", 1.5, 30);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = weapon.use(new Food("Taters", 0.3, 45));
            String printed = capture.stop();

            assertFalse(result);
            assertEquals("", printed);
        }
    }

    @Nested
    @DisplayName("use on a Person attacks and reports the outcome")
    class UseOnPerson {

        @Test
        @DisplayName("lethal target prints the attack then 'is dead!' and still returns true")
        void onLethalTarget_printsAttackThenIsDead() {
            Person p = new Person("Bob");
            Weapon weapon = new Weapon("BFG", 28.1, 900);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = weapon.use(p);
            String printed = capture.stop();

            assertTrue(result, "use returns true even when the attack is lethal");
            assertEquals("Attack Bob with BFG for 900 damage!\nBob is dead!\n", printed);
            assertEquals(0, p.getHealth(), "900 damage is lethal against 100 health");
        }

        @Test
        void onSurvivingPerson_usesActualDamage() {
            Person p = new Person("Smeagol");
            Weapon weapon = new Weapon("Glowing Sting", 1.4, 60);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = weapon.use(p);
            String printed = capture.stop();

            assertTrue(result);
            assertEquals(
                    "Attack Smeagol with Glowing Sting for 60 damage!\nSmeagol lives!\n",
                    printed);
            assertEquals(40, p.getHealth());
            assertTrue(p.isAlive());
        }

        @Test
        @DisplayName("lethal target prints the attack then 'is dead!' and still returns true")
        void onLethalAttack_printsAttackThenIsDead() {
            Person p = new Person("Bob");
            p.defends(40);
            Weapon weapon = new Weapon("Sting", 1.5, 60);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = weapon.use(p);
            String printed = capture.stop();

            assertTrue(result, "use returns true even when the attack is lethal");
            assertEquals("Attack Bob with Sting for 60 damage!\nBob is dead!\n", printed);
            assertEquals(0, p.getHealth());
            assertFalse(p.isAlive());
        }

        @Test
        void onAlreadyDeadPerson_stillPrintsIsDead() {
            Person p = new Person("Bob");
            p.defends(100);
            Weapon weapon = new Weapon("Sting", 1.5, 30);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = weapon.use(p);
            String printed = capture.stop();

            assertTrue(result);
            assertEquals("Attack Bob with Sting for 30 damage!\nBob is dead!\n", printed);
            assertEquals(0, p.getHealth(), "health must not go below 0");
        }

        @Test
        void usesCurrentItemNameAndDamageNotStaleCopies() {
            Person p = new Person("Bob");
            Weapon weapon = new Weapon("Sting", 1.5, 30);
            weapon.setName("Glowing Sting");
            weapon.setDamage(20);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = weapon.use(p);
            String printed = capture.stop();

            assertTrue(result);
            assertEquals(
                    "Attack Bob with Glowing Sting for 20 damage!\nBob lives!\n",
                    printed);
            assertEquals(80, p.getHealth());
        }
    }
}