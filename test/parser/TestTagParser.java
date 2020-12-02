package parser;

import model.DueDate;
import model.Priority;
import model.Status;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsers.TagParser;
import parsers.exceptions.ParsingException;

import static org.junit.jupiter.api.Assertions.*;

public class TestTagParser {

    TagParser testParser;
    Task testTask;
    Task testTask2;
    Priority p1;
    Priority p2;
    Priority p3;
    Priority p4;
    DueDate today;
    DueDate tomorrow;

    @BeforeEach
    public void setUp() {
        testParser = new TagParser();
        testTask = new Task("Test Task");
        p1 = new Priority(1);
        p2 = new Priority(2);
        p3 = new Priority(3);
        p4 = new Priority(4);
        today = new DueDate();
        tomorrow = new DueDate();
        tomorrow.postponeOneDay();
        testTask2 = new Task("Test Task 2");
        testTask2.setStatus(Status.DONE);
        testTask2.setPriority(p2);
        testTask2.setDueDate(tomorrow);
        testTask2.addTag("tag1");
    }

    @Test
    public void testGetDescription() {
        try {
            testParser.parse("Testing getDescription method ##", testTask);
            assertEquals(testParser.getDescription(), testTask.getDescription());
            assertEquals(testParser.getDescription(), "Testing getDescription method ");
            assertEquals(testTask.getDescription(), "Testing getDescription method ");
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected parsingException");
        }

    }

    @Test
    public void testEmptyThrowsParsingException() {
        try {
            testParser.parse("", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
            fail("Didn't throw expected ParsingException");
        } catch (ParsingException e) {
            System.out.println("Test passed - Caught expected ParsingException");
        }
    }

    @Test
    public void testDescriptionThrowsParsingException() {
        try {
            testParser.parse("This should throw a parsingException tag1; important", testTask);
            assertEquals("This should throw a parsingException tag1; important", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
            fail("Didn't throw expected ParsingException");
        } catch (ParsingException e) {
            System.out.println("Test passed - Caught expected ParsingException");
        }
    }

    @Test
    public void testMetaDataThrowsParsingException() {
        try {
            testParser.parse("Parsing; important; Exception", testTask);
            assertEquals("Parsing; important; Exception", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
            fail("Didn't throw expected ParsingException");
        } catch (ParsingException e) {
            System.out.println("Test passed - Caught expected ParsingException");
        }
    }

    @Test
    public void testSinglePoundThrowsParsingException() {
        try {
            testParser.parse("description # something; else", testTask);
            assertEquals("description # something; else", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
            fail("Didn't throw expected ParsingException");
        } catch (ParsingException e) {
            System.out.println("Test passed - Caught expected ParsingException");
        }
    }

    @Test
    public void testDeliminatorWithSpaceThrowsParsingException() {
        try {
            testParser.parse("something # # other", testTask);
            assertEquals("something # # other", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
            fail("Didn't throw expected ParsingException;");
        } catch (ParsingException e) {
            System.out.println("Test passed - Caught expected ParsingException");
        }
    }

    @Test
    public void testDeliminatorOnlyNoExceptionThrown() {
        try {
            testParser.parse("##", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleSpaceOnlyNoExceptionThrown() {
        try {
            testParser.parse(" ##", testTask);
            assertEquals(" ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionDoubleSpaceOnlyNoExceptionThrown() {
        try {
            testParser.parse("  ##", testTask);
            assertEquals("  ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testTriplePound() {
        try {
            testParser.parse("###", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("#"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }


    @Test
    public void testNewDescriptionOnlyNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionLeadingSpacesOnlyNoExceptionThrown() {
        try {
            testParser.parse("     Testing new description ##", testTask);
            assertEquals("     Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleTagFromEmptyNoExceptionThrown() {
        try {
            testParser.parse("## tag", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleTagAlreadyOtherTagsNoExceptionThrown() {
        try {
            testTask.addTag("tag1");
            testTask.addTag("tag2");
            testParser.parse("## tag", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(3, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tag2"));
            assertTrue(testTask.containsTag("tag"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleTagAlreadyThisTagsNoExceptionThrown() {
        try {
            testTask.addTag("tag1");
            testTask.addTag("tag2");
            testParser.parse("## tag1", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tag2"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleTagExtraLeadingSpacesNoExceptionThrown() {
        try {
            testParser.parse("##     tag1", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleTagExtraTrailingSpacesNoExceptionThrown() {
        try {
            testParser.parse("## tag1       ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleTagExtraSpacesNoExceptionThrown() {
        try {
            testParser.parse("##      tag1        ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleEmptyTagNoExceptionThrown() {
        try {
            testParser.parse("##              ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2FromEmptyNoExceptionThrown() {
        try {
            testParser.parse("## important", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2AlreadyImportantNoExceptionThrown() {
        try {
            testTask.setPriority(p2);
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            testParser.parse("## important", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("important"));
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2WierdCapsAlreadyImportantNoExceptionThrown() {
        try {
            testTask.setPriority(p2);
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            testParser.parse("## iMPOrtANt", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("iMPOrtANt"));
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2DuplicationAlreadyImportantNoExceptionThrown() {
        try {
            testTask.setPriority(p2);
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            testParser.parse("## iMPOrtANt; important", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("iMPOrtANt"));
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2WeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("## ImpoRtaNt", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2ExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("##     important     ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2IncorrectSpellingNoExceptionThrown() {
        try {
            testParser.parse("## inportant", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("inportant"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2IncorrectSpellingSpaceNoExceptionThrown() {
        try {
            testParser.parse("## impor tant", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("impor tant"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2IncorrectSpellingNonStandardTaskNoExceptionThrown() {
        try {
            testParser.parse("## inportant", testTask2);
            assertEquals("Test Task 2", testTask2.getDescription());
            assertEquals(2, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertTrue(testTask2.containsTag("inportant"));
            assertEquals("IMPORTANT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(tomorrow.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2IncorrectSpellingSpaceNonStandardTaskNoExceptionThrown() {
        try {
            testParser.parse("## impor tant", testTask2);
            assertEquals("Test Task 2", testTask2.getDescription());
            assertEquals(2, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertTrue(testTask2.containsTag("impor tant"));
            assertEquals("IMPORTANT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(tomorrow.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }


    @Test
    public void testSinglePriority3FromEmptyNoExceptionThrown() {
        try {
            testParser.parse("## urgent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3AlreadyUrgentNoExceptionThrown() {
        try {
            testTask.setPriority(p3);
            assertEquals("URGENT", testTask.getPriority().toString());
            testParser.parse("## urgent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("urgent"));
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3WierdCapsAlreadyUrgentNoExceptionThrown() {
        try {
            testTask.setPriority(p3);
            assertEquals("URGENT", testTask.getPriority().toString());
            testParser.parse("## UrGenT", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("UrGenT"));
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3DuplicationAlreadyUrgentNoExceptionThrown() {
        try {
            testTask.setPriority(p3);
            assertEquals("URGENT", testTask.getPriority().toString());
            testParser.parse("## UrGenT; urgent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("UrGenT"));
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3CapsNoExceptionThrown() {
        try {
            testParser.parse("## Urgent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3WeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("## urGEnT", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3ExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("##     urgent     ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3IncorrectSpellingNoExceptionThrown() {
        try {
            testParser.parse("## urjent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("urjent"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3IncorrectSpellingSpaceNoExceptionThrown() {
        try {
            testParser.parse("## ur gent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("ur gent"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3IncorrectSpellingNonStandardTaskNoExceptionThrown() {
        try {
            testParser.parse("## urjent", testTask2);
            assertEquals("Test Task 2", testTask2.getDescription());
            assertEquals(2, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertTrue(testTask2.containsTag("urjent"));
            assertEquals("IMPORTANT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(tomorrow.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3IncorrectSpellingSpaceNonStandardTaskNoExceptionThrown() {
        try {
            testParser.parse("## ur gent", testTask2);
            assertEquals("Test Task 2", testTask2.getDescription());
            assertEquals(2, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertTrue(testTask2.containsTag("ur gent"));
            assertEquals("IMPORTANT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(tomorrow.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3AlreadyPriority1NoExcepitonThrown() {
        try {
            testTask.setPriority(new Priority(1));
            testParser.parse("## urgent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("urgent"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3ExtraSpaceAlreadyPriority1NoExcepitonThrown() {
        try {
            testTask.setPriority(new Priority(1));
            testParser.parse("##     urgent     ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("urgent"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3WierdCapsAlreadyPriority1NoExcepitonThrown() {
        try {
            testTask.setPriority(new Priority(1));
            testParser.parse("## URGent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("URGent"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority3DuplicationAlreadyPriority1NoExcepitonThrown() {
        try {
            testTask.setPriority(new Priority(1));
            testParser.parse("## URGent; urGENt", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("URGent"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2AlreadyPriority1NoExcepitonThrown() {
        try {
            testTask.setPriority(new Priority(1));
            testParser.parse("## important", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("important"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2ExtraSpaceAlreadyPriority1NoExcepitonThrown() {
        try {
            testTask.setPriority(new Priority(1));
            testParser.parse("##     important     ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("important"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2WierdCapsAlreadyPriority1NoExcepitonThrown() {
        try {
            testTask.setPriority(new Priority(1));
            testParser.parse("## ImPoRtAnT", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("ImPoRtAnT"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSinglePriority2DuplicationAlreadyPriority1NoExcepitonThrown() {
        try {
            testTask.setPriority(new Priority(1));
            testParser.parse("## ImPoRtAnT; imporTanT", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("ImPoRtAnT"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusInProgressNoExceptionThrown() {
        try {
            testParser.parse("## in progress", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusInProgressCapsNoExceptionThrown() {
        try {
            testParser.parse("## In Progress", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusInProgressWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("## IN ProGresS", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusInProgressExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("##     in progress        ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusUpNextNoExceptionThrown() {
        try {
            testParser.parse("## up next", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusUpNextCapsNoExceptionThrown() {
        try {
            testParser.parse("## Up Next", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusUpNextWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("## Up nEXt", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusUpNextExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("##     up next        ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusDoneNoExceptionThrown() {
        try {
            testParser.parse("## done", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusDoneCapsNoExceptionThrown() {
        try {
            testParser.parse("## Done", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusDoneWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("## dONE", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusDoneExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("##      done        ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusToDoNoExceptionThrown() {
        try {
            testParser.parse("## to do", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusToDoCapsNoExceptionThrown() {
        try {
            testParser.parse("## To Do", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusToDoWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("## TO dO", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleStatusToDoExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("##      to do        ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateTodayNoExceptionThrown() {
        try {
            testParser.parse("## today", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateAlreadyTodayNoExceptionThrown() {
        try {
            testTask.setDueDate(today);
            assertEquals(today.toString(), testTask.getDueDate().toString());
            testParser.parse("## today", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateTodayFromTomorrowNoExceptionThrown() {
        try {
            testTask.setDueDate(tomorrow);
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
            testParser.parse("## today", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateTodayCapsNoExceptionThrown() {
        try {
            testParser.parse("## Today", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateTodayWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("## TodAy", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateTodayExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("##    today       ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateTomorrowNoExceptionThrown() {
        try {
            testParser.parse("## tomorrow", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateAlreadyTomorrowExtraSpaceNoExceptionThrown() {
        try {
            testTask.setDueDate(tomorrow);
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
            testParser.parse("## tomorrow", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateTomorrowFromTodayExtraSpaceNoExceptionThrown() {
        try {
            testTask.setDueDate(today);
            assertEquals(today.toString(), testTask.getDueDate().toString());
            testParser.parse("## tomorrow", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateTomorrowCapsNoExceptionThrown() {
        try {
            testParser.parse("## Tomorrow", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateTomorrowWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("## toMorRoW", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDueDateTomorrowExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("##    tomorrow      ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleTagNoExtraSpacesNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## tag1", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleTagExtraLeadingSpacesNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##     tag1", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleTagExtraTrailingSpacesNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## tag1       ", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleTagExtraSpacesNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##      tag1        ", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSinglePriority2NoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## important", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSinglePriority2CapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## Important", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSinglePriority2WeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## ImpoRtaNt", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSinglePriority2ExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##     important     ", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSinglePriority3NoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## urgent", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSinglePriority3CapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## Urgent", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSinglePriority3WeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## urGEnT", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSinglePriority3ExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##     urgent     ", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusInProgressNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## in progress", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusInProgressCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## In Progress", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusInProgressWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## IN ProGresS", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusInProgressExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##     in progress        ", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusUpNextNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## up next", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusUpNextCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## Up Next", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusUpNextWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## Up nEXt", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusUpNextExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##     up next        ", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusDoneNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## done", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusDoneCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## Done", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusDoneWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## dONE", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusDoneExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##      done        ", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusToDoNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## to do", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusToDoCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## To Do", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusToDoWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## TO dO", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleStatusToDoExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##      to do        ", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleToDoAlreadyToDo() {
        try {
            testTask.setStatus(Status.TODO);
            testParser.parse("## to do", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleTodoFromInProgress() {
        try {
            testTask.setStatus(Status.IN_PROGRESS);
            testParser.parse("## to do", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleInProgressAlreadyInProgress() {
        try {
            testTask.setStatus(Status.IN_PROGRESS);
            testParser.parse("## in progress", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleInProgressFromUpNext() {
        try {
            testTask.setStatus(Status.UP_NEXT);
            testParser.parse("## in progress", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleUpNextAlreadyUpNext() {
        try {
            testTask.setStatus(Status.UP_NEXT);
            testParser.parse("## up next", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleUpNextFromDone() {
        try {
            testTask.setStatus(Status.DONE);
            testParser.parse("## up next", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDoneAlreadyDone() {
        try {
            testTask.setStatus(Status.DONE);
            testParser.parse("## done", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testSingleDoneFromToDo() {
        try {
            testTask.setStatus(Status.TODO);
            testParser.parse("## done", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleDueDateTodayNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## today", testTask);
            assertEquals("Testing new description ", testParser.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleDueDateTodayCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## Today", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleDueDateTodayWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## TodAy", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleDueDateTodayExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##    today       ", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleDueDateTomorrowNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## tomorrow", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleDueDateTomorrowCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## Tomorrow", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleDueDateTomorrowWeirdCapsNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ## toMorRoW", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testNewDescriptionSingleDueDateTomorrowExtraSpaceNoExceptionThrown() {
        try {
            testParser.parse("Testing new description ##    tomorrow      ", testTask);
            assertEquals("Testing new description ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateTagsIdenticalNoExceptionThrown() {
        try {
            testParser.parse("## tag1; tag1", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateTagsWithEmptyIdenticalNoExceptionThrown() {
        try {
            testParser.parse("## tag1; tag1;  ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateTagsCapsNoExceptionThrown() {
        try {
            testParser.parse("## tag1; tAg1; TAG1; TaG1", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateTagsCapsCheckOrderNoExceptionThrown() {
        try {
            testParser.parse("## tAg1; tag1; TAG1; TaG1", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tAg1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateTagsOutsideSpacesNoExceptionThrown() {
        try {
            testParser.parse("## tag1    ;      tag1", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicatePriorityIdenticalNoExceptionThrown() {
        try {
            testParser.parse("## important; important", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicatePriorityCapsNoExceptionThrown() {
        try {
            testParser.parse("## urGenT; URgeNt", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicatePriorityOutsideSpacesNoExceptionThrown() {
        try {
            testParser.parse("## important     ;   ImporTant    ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testPriority1FromEmptyNoExceptionThrown() {
        try {
            testParser.parse("## important; urgent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testPriority1AlreadyImportantNoExceptionThrown() {
        try {
            testTask.setPriority(p2);
            assertEquals("IMPORTANT", testTask.getPriority().toString());
            testParser.parse("## urgent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testPriority1AlreadyUrgentNoExceptionThrown() {
        try {
            testTask.setPriority(p3);
            assertEquals("URGENT", testTask.getPriority().toString());
            testParser.parse("## important", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testPriority1AlreadyImportantAndUrgentNoExceptionThrown() {
        try {
            testTask.setPriority(p1);
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            testParser.parse("## important; urgent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("important"));
            assertTrue(testTask.containsTag("urgent"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testPriority1WierdCapsAlreadyImportantAndUrgentNoExceptionThrown() {
        try {
            testTask.setPriority(p1);
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            testParser.parse("## impOrtaNt; urgEnt", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("impOrtaNt"));
            assertTrue(testTask.containsTag("urgEnt"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testPriority1DuplicatesAlreadyImportantAndUrgentNoExceptionThrown() {
        try {
            testTask.setPriority(p1);
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            testParser.parse("## impOrtaNt; urgEnt; important; URGENT", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("impOrtaNt"));
            assertTrue(testTask.containsTag("urgEnt"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testPriority1NoExceptionThrown() {
        try {
            testParser.parse("## important; urgent", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicatePriority1NoExceptionThrown() {
        try {
            testParser.parse("## important; urgent; ImporTant", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateStatusToDoNoExceptionThrown() {
        try {
            testParser.parse("## to do; To Do", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateStatusInProgressNoExceptionThrown() {
        try {
            testParser.parse("## In progRess; in progress    ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateStatusUpNextNoExceptionThrown() {
        try {
            testParser.parse("##   Up NexT; up next", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateStatusDoneNoExceptionThrown() {
        try {
            testParser.parse("## done  ; DoNe      ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateStatusToDoTagNoExceptionThrown() {
        try {
            testParser.parse("## to do; done", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("done"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateStatusInProgressTagNoExceptionThrown() {
        try {
            testParser.parse("## in progress; to do", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("to do"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateStatusUpNextTagNoExceptionThrown() {
        try {
            testParser.parse("## up next; in progress", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("in progress"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateStatusDoneTagNoExceptionThrown() {
        try {
            testParser.parse("## done; to do", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("to do"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertNull(testTask.getDueDate());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateDueDateTodayNoExceptionThrown() {
        try {
            testParser.parse("## today; tODay  ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateDueDateTomorrowNoExceptionThrown() {
        try {
            testParser.parse("## Tomorrow; toMorroW  ", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateDueDateTodayTagNoExceptionThrown() {
        try {
            testParser.parse("## today; tomorrow", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tomorrow"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDuplicateDueDateTomorrowTagNoExceptionThrown() {
        try {
            testParser.parse("## Tomorrow; today", testTask);
            assertEquals("Test Task", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("today"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testDoneAlreadySetDontChangeNoExceptionThrown() {
        try {
            testParser.parse("parse no. 1 ## today; urgent", testTask2);
            assertEquals("parse no. 1 ", testTask2.getDescription());
            assertEquals(1, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertEquals("IMPORTANT & URGENT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(today.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testUpNextAlreadySetDontChangeNoExceptionThrown() {
        try {
            testTask2.setStatus(Status.UP_NEXT);
            testParser.parse("parse no. 1 ## today; urgent", testTask2);
            assertEquals("parse no. 1 ", testTask2.getDescription());
            assertEquals(1, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertEquals("IMPORTANT & URGENT", testTask2.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask2.getStatus());
            assertEquals(today.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testToDoAlreadySetDontChangeNoExceptionThrown() {
        try {
            testTask2.setStatus(Status.TODO);
            testParser.parse("parse no. 1 ## today; urgent", testTask2);
            assertEquals("parse no. 1 ", testTask2.getDescription());
            assertEquals(1, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertEquals("IMPORTANT & URGENT", testTask2.getPriority().toString());
            assertEquals(Status.TODO, testTask2.getStatus());
            assertEquals(today.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testInProgressAlreadySetDontChangeNoExceptionThrown() {
        try {
            testTask2.setStatus(Status.IN_PROGRESS);
            testParser.parse("parse no. 1 ## today; urgent", testTask2);
            assertEquals("parse no. 1 ", testTask2.getDescription());
            assertEquals(1, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertEquals("IMPORTANT & URGENT", testTask2.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask2.getStatus());
            assertEquals(today.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testTodayAlreadySetDontChangeNoExceptionThrown() {
        try {
            testTask2.setDueDate(today);
            testParser.parse("parse no. 1 ## tAg1; urgent", testTask2);
            assertEquals("parse no. 1 ", testTask2.getDescription());
            assertEquals(1, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertEquals("IMPORTANT & URGENT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(today.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testTomorrowAlreadySetDontChangeNoExceptionThrown() {
        try {
            testParser.parse("parse no. 1 ## TaG1; urgent", testTask2);
            assertEquals("parse no. 1 ", testTask2.getDescription());
            assertEquals(1, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertEquals("IMPORTANT & URGENT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(tomorrow.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testImportantAndUrgentAlreadySetDontChangeNoExceptionThrown() {
        try {
            testTask2.setPriority(new Priority(1));
            testParser.parse("parse no. 1 ## TaG1", testTask2);
            assertEquals("parse no. 1 ", testTask2.getDescription());
            assertEquals(1, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertEquals("IMPORTANT & URGENT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(tomorrow.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testImportantAlreadySetDontChangeNoExceptionThrown() {
        try {
            testParser.parse("parse no. 1 ## TaG1", testTask2);
            assertEquals("parse no. 1 ", testTask2.getDescription());
            assertEquals(1, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertEquals("IMPORTANT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(tomorrow.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testUrgentAlreadySetDontChangeNoExceptionThrown() {
        try {
            testTask2.setPriority(new Priority(3));
            testParser.parse("parse no. 1 ## TaG1", testTask2);
            assertEquals("parse no. 1 ", testTask2.getDescription());
            assertEquals(1, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertEquals("URGENT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(tomorrow.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testMultipleMetaDataNoDuplicationNoExceptionThrown() {
        try {
            testParser.parse("Updating description ## urgent; tag1; important; up next; today; tag2", testTask);
            assertEquals("Updating description ", testTask.getDescription());
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tag2"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }

    }

    @Test
    public void testMultipleMetaDataDuplicationNoExceptionThrown() {
        try {
            testParser.parse("Updating description ## urgent; Project; tag1; tomorrow;" +
                    "important;   up next  ; urgent; today; tag2  ; To Do; cpsc210", testTask);
            assertEquals("Updating description ", testTask.getDescription());
            assertEquals(6, testTask.getTags().size());
            assertTrue(testTask.containsTag("Project"));
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("today"));
            assertTrue(testTask.containsTag("tag2"));
            assertTrue(testTask.containsTag("To Do"));
            assertTrue(testTask.containsTag("cpsc210"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testMultipleMetaDataDuplicationNoSpacesNoExceptionThrown() {
        try {
            testParser.parse("Updating description##urgent;Project;tag1;tomorrow;" +
                    "important;up next;urgent;today;tag2;To Do;cpsc210", testTask);
            assertEquals("Updating description", testTask.getDescription());
            assertEquals(6, testTask.getTags().size());
            assertTrue(testTask.containsTag("Project"));
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("today"));
            assertTrue(testTask.containsTag("tag2"));
            assertTrue(testTask.containsTag("To Do"));
            assertTrue(testTask.containsTag("cpsc210"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testMultipleMetaDataDuplicateEverythingNoExceptionThrown() {
        try {
            testParser.parse("Updating description ## urgent; tag2; today; Project; tag1; in progress; tomorrow;" +
                    "important; done; tomorrow;  up next  ; to do; urgent; today; tag2  ; To Do; Important; up next; " +
                    "in progress; cpsc210; tag1; done", testTask);
            assertEquals("Updating description ", testTask.getDescription());
            assertEquals(8, testTask.getTags().size());
            assertTrue(testTask.containsTag("Project"));
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tag2"));
            assertTrue(testTask.containsTag("cpsc210"));
            assertTrue(testTask.containsTag("tomorrow"));
            assertTrue(testTask.containsTag("done"));
            assertTrue(testTask.containsTag("up next"));
            assertTrue(testTask.containsTag("to do"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testMultipleParsesDifferentInputsDifferentTasksNoExceptionThrown() {
        try {
            testParser.parse("parse no. 1 ## today; urgent", testTask);
            assertEquals("parse no. 1 ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
            testParser.parse("parse no. 2 ## urgent; today; to do; tag2", testTask2);
            assertEquals("parse no. 2 ", testTask2.getDescription());
            assertEquals(2, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertTrue(testTask2.containsTag("tag2"));
            assertEquals("IMPORTANT & URGENT", testTask2.getPriority().toString());
            assertEquals(Status.TODO, testTask2.getStatus());
            assertEquals(today.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testMultipleParsesDifferentInputsSameTaskNoExceptionThrown() {
        try {
            testParser.parse("parse no. 1 ## urgent; done; tomorrow; tag1", testTask);
            assertEquals("parse no. 1 ", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.DONE, testTask.getStatus());
            assertEquals(tomorrow.toString(), testTask.getDueDate().toString());
            testParser.parse("parse no. 2 ## important; today; to do; tag2", testTask);
            assertEquals("parse no. 2 ", testTask.getDescription());
            assertEquals(2, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag1"));
            assertTrue(testTask.containsTag("tag2"));
            assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testMultipleParsesSameInputsNoStatusDifferentTasksNoExceptionThrown() {
        try {
            testParser.parse("parse no. 1 ## today; urgent; tag99", testTask);
            assertEquals("parse no. 1 ", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag99"));
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
            testParser.parse("parse no. 2 ## today; urgent; tag99", testTask2);
            assertEquals("parse no. 2 ", testTask2.getDescription());
            assertEquals(2, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertTrue(testTask2.containsTag("tag99"));
            assertEquals("IMPORTANT & URGENT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
            assertEquals(today.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testMultipleParsesSameInputsNoPriorityDifferentTasksNoExceptionThrown() {
        try {
            testParser.parse("parse no. 1 ## today; up next; tag99", testTask);
            assertEquals("parse no. 1 ", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag99"));
            assertEquals("DEFAULT", testTask.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            assertEquals(today.toString(), testTask.getDueDate().toString());
            testParser.parse("parse no. 2 ## today; up next; tag99", testTask2);
            assertEquals("parse no. 2 ", testTask2.getDescription());
            assertEquals(2, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertTrue(testTask2.containsTag("tag99"));
            assertEquals("IMPORTANT", testTask2.getPriority().toString());
            assertEquals(Status.UP_NEXT, testTask2.getStatus());
            assertEquals(today.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testMultipleParsesSameInputsNoDateDifferentTasksNoExceptionThrown() {
        try {
            testParser.parse("parse no. 1 ## in progress; tag99; urgent", testTask);
            assertEquals("parse no. 1 ", testTask.getDescription());
            assertEquals(1, testTask.getTags().size());
            assertTrue(testTask.containsTag("tag99"));
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask.getStatus());
            assertNull(testTask.getDueDate());
            testParser.parse("parse no. 2 ## in progress; tag99; urgent", testTask2);
            assertEquals("parse no. 2 ", testTask2.getDescription());
            assertEquals(2, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertTrue(testTask2.containsTag("tag99"));
            assertEquals("IMPORTANT & URGENT", testTask2.getPriority().toString());
            assertEquals(Status.IN_PROGRESS, testTask2.getStatus());
            assertEquals(tomorrow.toString(), testTask2.getDueDate().toString());
        } catch (ParsingException e) {
            fail("Caught unexpected ParsingException");
        }
    }

    @Test
    public void testMultipleParsesOneEmptyInputDifferentTasksNoExcepitonThrown() {
        try {
            testParser.parse("parse no. 1 ## urgent", testTask);
            assertEquals("parse no. 1 ", testTask.getDescription());
            assertEquals(0, testTask.getTags().size());
            assertEquals("URGENT", testTask.getPriority().toString());
            assertEquals(Status.TODO, testTask.getStatus());
            testParser.parse("##", testTask2);
            assertEquals("Test Task 2", testTask2.getDescription());
            assertEquals(1, testTask2.getTags().size());
            assertTrue(testTask2.containsTag("tag1"));
            assertEquals("IMPORTANT", testTask2.getPriority().toString());
            assertEquals(Status.DONE, testTask2.getStatus());
        } catch (ParsingException e) {
            fail("caught unexpected ParsingExcepiton");
        }
    }
}