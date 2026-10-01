package ics2606.mp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Derived from the written requirements for the Person class, not from
 * reading Person.java. Where the spec is silent the test name says so.
 */
class PersonTest {

    private static final String NAME = "Bob";

    // --- constructor ---------------------------------------------------

    @Test
    @DisplayName("constructor stores the given name")
    void constructor_storesName() {
        Person p = new Person(NAME);
        assertEquals(NAME, p.getName());
    }

    @Test
    @DisplayName("constructor sets health to the default of 100")
    void constructor_setsDefaultHealth() {
        Person p = new Person(NAME);
        assertEquals(100, p.getHealth());
    }

    // --- getters -------------------------------------------------------

    @Test
    void getHealth_reflectsCurrentHealth() {
        Person p = new Person(NAME);
        p.defends(11);
        assertEquals(89, p.getHealth());
    }

    // --- toString ------------------------------------------------------

    @Test
    @DisplayName("toString returns 'Name: Bob\\nHealth: 89\\n' and prints nothing")
    void toString_exactFormatAndNoOutput() {
        Person p = new Person(NAME);
        p.defends(11);

        CaptureStdOut capture = CaptureStdOut.start();
        String result = p.toString();
        String printed = capture.stop();

        assertEquals("Name: Bob\nHealth: 89\n", result);
        assertEquals("", printed, "toString must not print");
    }

    // --- isAlive -------------------------------------------------------

    @Nested
    @DisplayName("isAlive is true when health is NOT zero")
    class IsAlive {

        @Test
        void atFullHealth_true() {
            assertTrue(new Person(NAME).isAlive());
        }

        @Test
        void atOneHealth_true() {
            Person p = new Person(NAME);
            p.defends(99);
            assertEquals(1, p.getHealth());
            assertTrue(p.isAlive());
        }

        @Test
        void atZeroHealth_false() {
            Person p = new Person(NAME);
            p.defends(100);
            assertEquals(0, p.getHealth());
            assertFalse(p.isAlive());
        }
    }

    // --- heal ----------------------------------------------------------

    @Nested
    @DisplayName("heal adds the boost while alive, never exceeding 100")
    class Heal {

        @Test
        void addsBoostAndReturnsTrue() {
            Person p = new Person(NAME);
            p.defends(10);

            assertTrue(p.heal(5));
            assertEquals(95, p.getHealth());
        }

        @Test
        void capsAt100AndReturnsTrue() {
            Person p = new Person(NAME);

            assertTrue(p.heal(50));
            assertEquals(100, p.getHealth());
        }

        @Test
        void atExactly100StaysAt100() {
            Person p = new Person(NAME);
            assertTrue(p.heal(100));
            assertEquals(100, p.getHealth());
        }

        @Test
        void onDeadPerson_returnsFalseAndLeavesHealthAtZero() {
            Person p = new Person(NAME);
            p.defends(500);
            assertFalse(p.isAlive());

            assertFalse(p.heal(10));
            assertEquals(0, p.getHealth());
        }

        @Test
        @DisplayName("spec is silent on negative boosts; health must never go below 0")
        void negativeBoost_neverLeavesHealthBelowZero() {
            Person p = new Person(NAME);
            p.defends(95);
            assertEquals(5, p.getHealth());

            p.heal(-10);

            assertEquals(0, p.getHealth(), "health must not become negative");
            assertFalse(p.isAlive(), "a person at 0 health is not alive");
        }
    }

    // --- defends -------------------------------------------------------

    @Nested
    @DisplayName("defends subtracts damage, never dropping below zero")
    class Defends {

        @Test
        void subtractsDamageAndReturnsTrueWhenSurviving() {
            Person p = new Person(NAME);

            assertTrue(p.defends(40));
            assertEquals(60, p.getHealth());
        }

        @Test
        void floorsAtZeroAndReturnsFalseWhenLethal() {
            Person p = new Person(NAME);

            assertFalse(p.defends(500));
            assertEquals(0, p.getHealth());
        }

        @Test
        void exactLethalDamageReturnsFalse() {
            Person p = new Person(NAME);

            assertFalse(p.defends(100));
            assertEquals(0, p.getHealth());
        }

        @Test
        void onePointBelowLethalReturnsTrue() {
            Person p = new Person(NAME);

            assertTrue(p.defends(99));
            assertEquals(1, p.getHealth());
        }

        @Test
        @DisplayName("spec is silent on negative damage; health must never exceed 100")
        void negativeDamage_neverLeavesHealthAboveMax() {
            Person p = new Person(NAME);

            p.defends(-50);

            assertEquals(100, p.getHealth());
        }
    }
}