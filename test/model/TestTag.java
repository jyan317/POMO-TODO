package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTag {

    private Tag testTag;

    @BeforeEach
    public void setUp() {
        String name = "1234";
        testTag = new Tag(name);
    }

    @Test
    public void testConstructor() {
        assertEquals("1234", testTag.getName());
    }

    @Test
    public void testToString() {
        assertEquals("#1234", testTag.toString());
    }
}