package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidPriorityLevelException;
import model.exceptions.InvalidTimeException;
import model.exceptions.NullArgumentException;
import parsers.exceptions.ParsingException;
import org.junit.jupiter.api.Test;


public class TestExceptions {

    @Test
    public void testEmptyStringExceptionDefaultConstructor() {
        try {
            throw new EmptyStringException();
        } catch (EmptyStringException e) {
            System.out.println("Test passed - Threw EmptyStringException");
        }
    }

    @Test
    public void testInvalidPriorityLevelExceptionDefaultConstructor() {
        try {
            throw new InvalidPriorityLevelException();
        } catch (InvalidPriorityLevelException e) {
            System.out.println("Test passed - Threw InvalidPriorityLevelException");
        }
    }

    @Test
    public void testInvalidTimeExceptionDefaultConstructor() {
        try {
            throw new InvalidTimeException();
        } catch (InvalidTimeException e) {
            System.out.println("Test passed - Threw InvalidTimeException");
        }
    }

    @Test
    public void testNullArgumentExceptionDefaultConstructor() {
        try {
            throw new NullArgumentException();
        } catch (NullArgumentException e) {
            System.out.println("Test passed - Threw NullArgumentException");
        }
    }

    @Test
    public void testParsingExceptionDefaultConstructor() {
        try {
            throw new ParsingException();
        } catch (ParsingException e) {
            System.out.println("Test passed - Threw ParsingException");
        }
    }
}