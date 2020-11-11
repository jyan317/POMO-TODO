package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;
import static model.Task.NO_DUE_DATE;
import static org.junit.jupiter.api.Assertions.*;

public class TestTask {

    private Task testTask;
    private DueDate today;
    private DueDate tomorrow;

    @BeforeEach
    public void setUp() {
        try {
            testTask = new Task("this is a test task");
            today = new DueDate();
            tomorrow = new DueDate();
            tomorrow.postponeOneDay();
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testConstructorNoExceptionsThrown() {
        try {
            testTask = new Task("this task should parse ## Project; CPSC 210; important;"
                    + "urgent; tomorrow; in progress");
            assertEquals("this task should parse ", testTask.getDescription());
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("Project"));
            assertTrue(testTask.containsTag("CPSC 210"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
            assertEquals(0, testTask.getEstimatedTimeToComplete());
            assertEquals(0, testTask.getProgress());
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testConstructorNoExceptionsThrownMultipleDueDates() {
        try {
            testTask = new Task("this task should parse ## today; tag1; tomorrow");
            assertEquals("this task should parse ", testTask.getDescription());
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tomorrow"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
            assertEquals(0, testTask.getEstimatedTimeToComplete());
            assertEquals(0, testTask.getProgress());
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testConstructorNoExceptionsThrownMultipleStatuses() {
        try {
            testTask = new Task("this task should parse ## in progress; tag2; up next");
            assertEquals("this task should parse ", testTask.getDescription());
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag2"));
            assertTrue(testTask.containsTag("up next"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
            assertEquals(0, testTask.getEstimatedTimeToComplete());
            assertEquals(0, testTask.getProgress());
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testConstructorNoExceptionsThrownDuplicateImportant() {
        try {
            testTask = new Task("this task should parse ## important; tag3; important");
            assertEquals("this task should parse ", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag3"));
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
            assertEquals(0, testTask.getEstimatedTimeToComplete());
            assertEquals(0, testTask.getProgress());
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testConstructorNoExceptionsThrownDuplicateTomorrow() {
        try {
            testTask = new Task("this task should parse ## tomorrow; tag4; tag5; tomorrow");
            assertEquals("this task should parse ", testTask.getDescription());
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag4"));
            assertTrue(testTask.containsTag("tag5"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
            assertEquals(0, testTask.getEstimatedTimeToComplete());
            assertEquals(0, testTask.getProgress());
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testConstructorThrowsAndCatchesParsingException() {
        try {
            assertEquals("this is a test task", testTask.getDescription());
            assertTrue(testTask.getTags().isEmpty());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(testTask.getStatus(), Status.TODO);
            assertNull(testTask.getDueDate());
            assertEquals(0, testTask.getEstimatedTimeToComplete());
            assertEquals(0, testTask.getProgress());
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testConstructorThrowsEmptyStringExceptionEmptyThrowsAndCatchesParsingException() {
        try {
            testTask = new Task("");
            fail("Didn't throw expected EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testConstructorThrowsEmptyStringExceptionNullThrowsAndCatchesParsingException() {
        try {
            testTask = new Task(null);
            fail("Didn't throw expected EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testAddTagFromEmptyNoExceptionThrown() {
        try {
            testTask.addTag("tag1");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testAddTagContainsTagNoExceptionThrown() {
        try {
            testTask.addTag("tag1");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            testTask.addTag("tag1");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testAddTagContainsOtherTagNoExceptionThrown() {
        try {
            testTask.addTag("tag1");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            testTask.addTag("tag2");
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tag2"));
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testAddTagFromEmptyThrowsEmptyStringExceptionEmpty() {
        try {
            testTask.addTag("");
            fail("Didn't throw expected EmtpyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test passed - Caught expected EmptyStirngException");
        }
    }

    @Test
    public void testAddTagContainsSomeTagThrowsEmptyStringExceptionEmpty() {
        try {
            testTask.addTag("tag1");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            testTask.addTag("");
            fail("Didn't throw expected EmptyStringException");
        } catch (EmptyStringException e) {
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testAddTagFromEmptyThrowsEmptyStringExceptionNull() {
        try {
            Tag tag = new Tag(null);
            testTask.addTag(tag);
            fail("Didn't throw expected EmtpyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testAddTagContainsSomeTagThrowsEmptyStringExceptionNull() {
        try {
            testTask.addTag("tag1");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            String tagName = "";
            testTask.addTag(tagName);
            fail("Didn't throw expected EmptyStringException");
        } catch (EmptyStringException e) {
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testRemoveTagFromEmptyNoExceptionThrown() {
        try {
            assertTrue(testTask.getTags().isEmpty());
            testTask.removeTag("tag1");
            assertTrue(testTask.getTags().isEmpty());
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testRemoveTagContainsTagNoExceptionThrown() {
        try {
            testTask.addTag("tag1");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            testTask.removeTag("tag1");
            assertEquals(0, testTask.getTags().size());
            assertFalse(testTask.containsTag("tag1"));
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testRemoveTagDoesntContainTagNoExceptionThrown() {
        try {
            testTask.addTag("tag1");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            testTask.removeTag("tag2");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testRemoveTagThrowsEmptyStringExceptionEmpty() {
        try {
            testTask.addTag("tag1");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            testTask.removeTag("");
            fail("Didn't throw expected EmptyStringException");
        } catch (EmptyStringException e) {
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testRemoveTagThrowsEmptyStringExceptionNull() {
        try {
            testTask.addTag("tag1");
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            String tagName = "";
            testTask.removeTag(tagName);
            fail("Didn't throw expected EmptyStringException");
        } catch (EmptyStringException e) {
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testGetTags() {
        testTask.addTag("tag 1");
        testTask.addTag("tag 2");
        Set<Tag> listTags = testTask.getTags();
        assertEquals(2, testTask.getTags().size());
        assertTrue(testTask.containsTag("tag 1"));
        assertTrue(testTask.containsTag("tag 2"));
        assertThrows(UnsupportedOperationException.class,
                () -> {
                    throw new UnsupportedOperationException();
                });
    }

    @Test
    public void testSetPriority1NoExceptionThrown() {
        try {
            Priority p1 = new Priority(1);
            testTask.setPriority(p1);
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testSetPriority2NoExceptionThrown() {
        try {
            Priority p2 = new Priority(2);
            testTask.setPriority(p2);
            assertEquals("IMPORTANT", testTask.getPriority().toString());
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testSetPriority3NoExceptionThrown() {
        try {
            Priority p3 = new Priority(3);
            testTask.setPriority(p3);
            assertEquals("URGENT", testTask.getPriority().toString());
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testSetPriority4NoExceptionThrown() {
        try {
            Priority p2 = new Priority(2);
            testTask.setPriority(p2);
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            Priority p4 = new Priority(4);
            testTask.setPriority(p4);
            assertEquals("DEFAULT", testTask.getPriority().toString());
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testSetPriorityThrowsNullArgumentException() {
        try {
            testTask.setPriority(null);
            fail("Didn't throw expected NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test passed - Caught expected NullArgumentException");
        }
    }

    @Test
    public void testSetStatusNoExceptionThrown() {
        try {
            assertEquals(Status.TODO, testTask.getStatus());
            testTask.setStatus(Status.DONE);
            assertEquals(Status.DONE, testTask.getStatus());
            assertEquals(0, testTask.getProgress());
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testSetStatusThrowsNullArgumentException() {
        try {
            assertEquals(Status.TODO, testTask.getStatus());
            testTask.setStatus(null);
            fail("Didn't throw expected NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test passed - Caught expected NullArgumentException");
        }
    }

    @Test
    public void testSetDescriptionNoExceptionsThrown() {
        try {
            assertEquals("this is a test task", testTask.getDescription());
            testTask.setDescription("I changed the description ## urgent; tomorrow; tag1; up next");
            assertEquals("I changed the description ", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
            assertEquals(0, testTask.getEstimatedTimeToComplete());
            assertEquals(0, testTask.getProgress());
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }


    @Test
    public void testSetDescriptionThrowsAndCatchesParsingException() {
        try {
            assertEquals("this is a test task", testTask.getDescription());
            testTask.setDescription("I changed the description");
            assertEquals("I changed the description", testTask.getDescription());
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testSetDescriptionEmptyStringExceptionThrownEmptyThrowsAndCatchesParsingException() {
        assertEquals("this is a test task", testTask.getDescription());
        try {
            testTask.setDescription("");
            fail("Didn't throw expected EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testSetDescriptionEmptyStringExceptionThrownNullThrowsAndCatchesParsingException() {
        assertEquals("this is a test task", testTask.getDescription());
        try {
            testTask.setDescription(null);
            fail("Didn't throw expected EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testSetDueDate() {
        Date Jan1st70GMT = new Date(0);
        DueDate testDueDate = new DueDate(Jan1st70GMT);
        testTask.setDueDate(testDueDate);
        assertEquals(Jan1st70GMT.toString(), testTask.getDueDate().getDate().toString());
    }

    @Test
    public void testContainsTag() {
        try {
            testTask.addTag("tag1");
            testTask.addTag("tag2");
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tag2"));
            assertFalse(testTask.containsTag("tag3"));
        } catch (EmptyStringException ese) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testContainsTagEmptyName() {
        try {
            testTask.addTag("tag1");
            testTask.addTag("tag2");
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tag2"));
            assertFalse(testTask.containsTag("tag3"));
            assertFalse(testTask.containsTag(""));
        } catch (EmptyStringException ese) {
            System.out.println("Test passed - Caught expected EmtpyStringException");
        }
    }

    @Test
    public void testContainsTagNullName() {
        try {
            testTask.addTag("tag1");
            testTask.addTag("tag2");
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tag2"));
            assertFalse(testTask.containsTag("tag3"));
            String nullName = null;
            assertFalse(testTask.containsTag(nullName));
        } catch (EmptyStringException ese) {
            System.out.println("Test passed - Caught expected EmtpyStringException");
        }
    }

    @Test
    public void testContainsTagNullTag() {
        try {
            testTask.addTag("tag1");
            testTask.addTag("tag2");
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tag2"));
            assertFalse(testTask.containsTag("tag3"));
            Tag nullTag = null;
            assertFalse(testTask.containsTag(nullTag));
        } catch (NullArgumentException nae) {
            System.out.println("Test passed - Caught expected NullArgumentException");
        }
    }

    @Test
    public void testToString() {
        testTask.setDescription("testing toString");
        Date Jan1st70GMT = new Date(0);
        DueDate testDueDate = new DueDate(Jan1st70GMT);
        testTask.setDueDate(testDueDate);
        testTask.setStatus(Status.IN_PROGRESS);
        Priority p1 = new Priority(1);
        testTask.setPriority(p1);
        testTask.addTag("cpsc210");
        testTask.addTag("project");
        assertEquals("\n{\n\tDescription: testing toString\n\tDue date: Wed Dec 31 1969 04:00 PM"
                        + "\n\tStatus: IN PROGRESS\n\tPriority: IMPORTANT & URGENT\n\tTags: #cpsc210, #project\n}",
                testTask.toString());
    }

    @Test
    public void testToStringEmptyDueDate() {
        testTask.setDescription("testing toString with empty dueDate");
        testTask.setDueDate(NO_DUE_DATE);
        testTask.setStatus(Status.UP_NEXT);
        testTask.setPriority(new Priority(3));
        testTask.addTag("tag1");
        assertEquals("\n{\n\tDescription: testing toString with empty dueDate\n\tDue date: "
                        + "\n\tStatus: UP NEXT\n\tPriority: URGENT\n\tTags: #tag1\n}",
                testTask.toString());
    }

    @Test
    public void testToStringEmptyTagList() {
        testTask.setDescription("testing toString with empty tag list");
        Date Jan1st70GMT = new Date(0);
        DueDate testDueDate = new DueDate(Jan1st70GMT);
        testTask.setDueDate(testDueDate);
        testTask.setStatus(Status.UP_NEXT);
        testTask.setPriority(new Priority(3));
        assertEquals("\n{\n\tDescription: testing toString with empty tag list"
                        + "\n\tDue date: Wed Dec 31 1969 04:00 PM\n\tStatus: UP NEXT\n\tPriority: URGENT\n\tTags:  \n}",
                testTask.toString());
    }

    @Test
    public void testSetProgressInvalidProgressExceptionThrownBelowZero() {
        try {
            testTask.setProgress(-1);
            fail("Didn't throw expected InvalidProgressException");
        } catch (InvalidProgressException ipe) {
            System.out.println("Test passed - Caught expected InvalidProgressException");
        }
    }

    @Test
    public void testSetProgressNoExceptionThrownZero() {
        try {
            testTask.setProgress(0);
            assertEquals(0, testTask.getProgress());
        } catch (InvalidProgressException ipe) {
            fail("Caught unexpected InvalidProgressException");
        }
    }

    @Test
    public void testSetProgressNoExceptionThrownMid() {
        try {
            testTask.setProgress(48);
            assertEquals(48, testTask.getProgress());
        } catch (InvalidProgressException ipe) {
            fail("Caught unexpected InvalidProgressException");
        }
    }

    @Test
    public void testSetProgressNoExceptionThrownHundred() {
        try {
            testTask.setProgress(100);
            assertEquals(100, testTask.getProgress());
        } catch (InvalidProgressException ipe) {
            fail("Caught unexpected InvalidProgressException");
        }
    }

    @Test
    public void testSetProgressInvalidProgressExceptionThrownAboveHundred() {
        try {
            testTask.setProgress(701);
            fail("Didn't throw expected InvalidProgressException");
        } catch (InvalidProgressException ipe) {
            System.out.println("Test passed - Caught expected InvalidProgressException");
        }
    }

    @Test
    public void testSetEstimatedTimeToCompleteNegativeInputExceptionThrown() {
        try {
            testTask.setEstimatedTimeToComplete(-5);
            fail("Didn't throw expected NegativeInputException");
        } catch (NegativeInputException nie) {
            System.out.println("Test passed - Caught expected NegativeInputException");
        }
    }

    @Test
    public void testSetEstimatedTimeToCompleteNoExceptionThrownZero() {
        try {
            testTask.setEstimatedTimeToComplete(0);
            assertEquals(0, testTask.getEstimatedTimeToComplete());
        } catch (NegativeInputException nie) {
            fail("Caught unexpected NegativeInputException");
        }
    }

    @Test
    public void testSetEstimatedTimeToCompleteNoExceptionThrownPositive() {
        try {
            testTask.setEstimatedTimeToComplete(5);
            assertEquals(5, testTask.getEstimatedTimeToComplete());
        } catch (NegativeInputException nie) {
            fail("Caught unexpected NegativeInputException");
        }
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(testTask.equals(testTask));
    }

    @Test
    public void testEqualsNotInstanceOfTask() {
        String testInstanceStr = "test/test";
        assertFalse(testTask.equals(testInstanceStr));
    }

    @Test
    public void testEqualsDifferentDescriptions() {
        testTask = new Task("testTask ## tag1");
        Task testTask2 = new Task("testTask2 ## tag1");
        assertFalse(testTask.equals(testTask2));
    }

    @Test
    public void testEqualsDifferentDueDates() {
        testTask = new Task("testTask ## today");
        Task testTask2 = new Task("testTask ## tomorrow");
        assertFalse(testTask.equals(testTask2));
    }

    @Test
    public void testEqualsDifferentPriorities() {
        testTask = new Task("testTask ## urgent");
        Task testTask2 = new Task("testTask ## important");
        assertFalse(testTask.equals(testTask2));
    }

    @Test
    public void testEqualsDifferentStatuses() {
        testTask = new Task("testTask ## to do");
        Task testTask2 = new Task("testTask ## up next");
        assertFalse(testTask.equals(testTask2));
    }

    @Test
    public void testEqualsSameInfoDiffObjects() {
        testTask = new Task("testTask ## up next");
        Task testTask2 = new Task("testTask ## up next");
        assertTrue(testTask.equals(testTask2));
    }
}