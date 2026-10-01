package ics2606.mp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Derived from the written requirements for the Food class.
 * Food must not store its own name or weight; both come from Item.
 */
class FoodTest {

    // --- inheritance ---------------------------------------------------

    @Test
    void food_isAnItem() {
        assertTrue(new Food("Eggs", 1.7, 45) instanceof Item);
    }

    @Test
    @DisplayName("name and weight are inherited from Item, not duplicated")
    void constructor_storesNameAndWeightViaItem() {
        Food food = new Food("Eggs", 1.7, 45);
        assertEquals("Eggs", food.getName());
        assertEquals(1.7, food.getWeight());
        assertEquals(45, food.getHealth());
    }

    @Test
    void setName_onFood_updatesThroughInheritedSetter() {
        Food food = new Food("Eggs", 1.7, 45);
        food.setName("POTATOES");
        assertEquals("POTATOES", food.getName());
    }

    // --- health accessors ----------------------------------------------

    @Test
    void setHealth_updatesStoredHealth() {
        Food food = new Food("Eggs", 1.7, 45);
        food.setHealth(56);
        assertEquals(56, food.getHealth());
    }

    // --- toString ------------------------------------------------------

    @Test
    @DisplayName("toString returns the three-line Name/Weight/Health format")
    void toString_exactFormatAndNoOutput() {
        Food food = new Food("Eggs", 1.7, 45);

        CaptureStdOut capture = CaptureStdOut.start();
        String result = food.toString();
        String printed = capture.stop();

        assertEquals("Name: Eggs\nWeight: 1.7\nHealth: 45\n", result);
        assertEquals("", printed, "toString must not print");
    }

    // --- use -----------------------------------------------------------

    @Nested
    @DisplayName("use returns false and prints nothing when the target is not a Person")
    class UseOnNonPerson {

        @Test
        void onNull() {
            Food food = new Food("Taters", 0.3, 45);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = food.use(null);
            String printed = capture.stop();

            assertFalse(result);
            assertEquals("", printed, "must print nothing for a non-Person target");
        }

        @Test
        void onAnItem() {
            Food food = new Food("Taters", 0.3, 45);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = food.use(new Item("Book", 5.3));
            String printed = capture.stop();

            assertFalse(result);
            assertEquals("", printed);
        }

        @Test
        void onAWeapon() {
            Food food = new Food("Taters", 0.3, 45);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = food.use(new Weapon("Sting", 1.5, 30));
            String printed = capture.stop();

            assertFalse(result);
            assertEquals("", printed);
        }
    }

    @Nested
    @DisplayName("use on a Person heals and reports the outcome")
    class UseOnPerson {

        @Test
        void onAlivePerson_healsPrintsAndReturnsTrue() {
            Person p = new Person("Bob");
            Food food = new Food("Eggs", 1.7, 45);
            p.defends(10);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = food.use(p);
            String printed = capture.stop();

            assertTrue(result);
            assertEquals("Bob ate Eggs for 45 health!\n", printed);
            assertEquals(100, p.getHealth(), "55 + 45 is exactly 100");
        }

        @Test
        void onAlivePerson_capsAt100() {
            Person p = new Person("Bob");
            Food food = new Food("Eggs", 1.7, 45);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = food.use(p);
            capture.stop();

            assertTrue(result);
            assertEquals(100, p.getHealth());
        }

        @Test
        @DisplayName("on dead Person prints 'cannot be healed' and returns false")
        void onDeadPerson_printsCannotBeHealedAndReturnsFalse() {
            Person p = new Person("Bob");
            p.defends(100);
            Food food = new Food("Eggs", 1.7, 45);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = food.use(p);
            String printed = capture.stop();

            assertFalse(result);
            assertEquals("Bob cannot be healed\n", printed);
            assertEquals(0, p.getHealth(), "a dead Person must not be revived");
        }

        @Test
        void usesCurrentItemNameAndHealthNotStaleCopies() {
            Person p = new Person("Bob");
            p.defends(10);
            Food food = new Food("Taters", 0.3, 45);
            food.setName("POTATOES");
            food.setHealth(56);

            CaptureStdOut capture = CaptureStdOut.start();
            boolean result = food.use(p);
            String printed = capture.stop();

            assertTrue(result);
            assertEquals("Bob ate POTATOES for 56 health!\n", printed);
            assertEquals(100, p.getHealth());
        }
    }
}