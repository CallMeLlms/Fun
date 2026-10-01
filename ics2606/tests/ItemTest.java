package ics2606.mp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Derived from the written requirements for the Item class.
 * Item is concrete, not abstract, because the main program instantiates it
 * directly and expects "Not usable" from the base implementation.
 */
class ItemTest {

    // --- constructor and getters ---------------------------------------

    @Test
    void constructor_storesNameAndWeight() {
        Item item = new Item("Fork", 0.45);
        assertEquals("Fork", item.getName());
        assertEquals(0.45, item.getWeight());
    }

    @Test
    void setName_updatesStoredName() {
        Item item = new Item("Fork", 0.45);
        item.setName("Spoon");
        assertEquals("Spoon", item.getName());
    }

    @Test
    void setWeight_updatesStoredWeight() {
        Item item = new Item("Fork", 0.45);
        item.setWeight(1.25);
        assertEquals(1.25, item.getWeight());
    }

    // --- toString ------------------------------------------------------

    @Test
    @DisplayName("toString returns 'Name: Fork\\nWeight: 0.45\\n' and prints nothing")
    void toString_exactFormatAndNoOutput() {
        Item item = new Item("Fork", 0.45);

        CaptureStdOut capture = CaptureStdOut.start();
        String result = item.toString();
        String printed = capture.stop();

        assertEquals("Name: Fork\nWeight: 0.45\n", result);
        assertEquals("", printed, "toString must not print");
    }

    @Test
    void toString_reflectsUpdatedValues() {
        Item item = new Item("Fork", 0.45);
        item.setName("There and Back Again: And What Happened After");
        item.setWeight(15.7);

        assertEquals(
                "Name: There and Back Again: And What Happened After\nWeight: 15.7\n",
                item.toString());
    }

    // --- use -----------------------------------------------------------

    @Test
    @DisplayName("use on a Person prints 'Not usable' and returns false")
    void use_onPerson_printsNotUsableAndReturnsFalse() {
        Item item = new Item("Book", 5.3);
        Person p = new Person("Frodo Baggins");

        CaptureStdOut capture = CaptureStdOut.start();
        boolean result = item.use(p);
        String printed = capture.stop();

        assertFalse(result);
        assertEquals("Not usable\n", printed);
    }

    @Test
    void use_onNull_printsNotUsableAndReturnsFalse() {
        Item item = new Item("Book", 5.3);

        CaptureStdOut capture = CaptureStdOut.start();
        boolean result = item.use(null);
        String printed = capture.stop();

        assertFalse(result);
        assertEquals("Not usable\n", printed);
    }

    @Test
    void use_onSubclassTarget_stillPrintsNotUsable() {
        Item item = new Item("Book", 5.3);
        Weapon weapon = new Weapon("Sting", 1.5, 30);

        CaptureStdOut capture = CaptureStdOut.start();
        boolean result = item.use(weapon);
        String printed = capture.stop();

        assertFalse(result);
        assertEquals("Not usable\n", printed);
    }
}