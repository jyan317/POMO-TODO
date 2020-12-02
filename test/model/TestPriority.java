package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPriority {

    private Priority testPriority;

    @Test
    public void testConstructorDefault() {
        testPriority = new Priority();
        assertFalse(testPriority.isImportant());
        assertFalse(testPriority.isUrgent());
    }

    @Test
    public void testConstructorQuad1() {
        testPriority = new Priority(1);
        assertTrue(testPriority.isImportant());
        assertTrue(testPriority.isUrgent());
    }

    @Test
    public void testConstructorQuad2() {
        testPriority = new Priority(2);
        assertTrue(testPriority.isImportant());
        assertFalse(testPriority.isUrgent());
    }

    @Test
    public void testConstructorQuad3() {
        testPriority = new Priority(3);
        assertFalse(testPriority.isImportant());
        assertTrue(testPriority.isUrgent());
    }

    @Test
    public void testConstructorQuad4() {
        testPriority = new Priority(4);
        assertFalse(testPriority.isImportant());
        assertFalse(testPriority.isUrgent());
    }

    @Test
    public void testSetImportant() {
        testPriority = new Priority(4);
        assertFalse(testPriority.isImportant());
        assertFalse(testPriority.isUrgent());
        testPriority.setImportant(true);
        assertTrue(testPriority.isImportant());
        assertFalse(testPriority.isUrgent());
        testPriority.setImportant(false);
        assertFalse(testPriority.isImportant());
        assertFalse(testPriority.isUrgent());
    }

    @Test
    public void testSetUrgent() {
        testPriority = new Priority(4);
        assertFalse(testPriority.isImportant());
        assertFalse(testPriority.isUrgent());
        testPriority.setUrgent(true);
        assertTrue(testPriority.isUrgent());
        assertFalse(testPriority.isImportant());
        testPriority.setUrgent(false);
        assertFalse(testPriority.isUrgent());
        assertFalse(testPriority.isImportant());
    }

    @Test
    public void testToStringImportantUrgent() {
        testPriority = new Priority(1);
        assertEquals("IMPORTANT & URGENT", testPriority.toString());
    }

    @Test
    public void testToStringImportant() {
        testPriority = new Priority(2);
        assertEquals("IMPORTANT", testPriority.toString());
    }

    @Test
    public void testToStringUrgent() {
        testPriority = new Priority(3);
        assertEquals("URGENT", testPriority.toString());
    }

    @Test
    public void testToStringDefault() {
        testPriority = new Priority();
        assertEquals("DEFAULT", testPriority.toString());
    }
}
