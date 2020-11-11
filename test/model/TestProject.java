package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {

    private Project testProject;
    private Project testSubProject;
    private Project testOtherProject;
    private Task t1;
    private Task t2;
    private Task t3;


    @BeforeEach
    public void setUp() {
        try {
            testProject = new Project("this is a test project");
            testSubProject = new Project("this is a test sub-project");
            testOtherProject = new Project("testOtherProject");
            t1 = new Task("task 1");
            t2 = new Task("task 2");
            t3 = new Task("task 3");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testConstructorNoExceptionThrown() {
        try {
            assertEquals("this is a test project", testProject.getDescription());
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
    }

    @Test
    public void testConstructorThrowsEmptyStringExceptionEmpty() {
        try {
            testProject = new Project("");
            fail("Didn't throw expected EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testConstructorThrowsEmptyStringExceptionNull() {
        try {
            testProject = new Project(null);
            fail("Didn't throw expected EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test passed - Caught expected EmptyStringException");
        }
    }

    @Test
    public void testAddTaskFromEmptyNoExceptionThrown() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(t1);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testAddTaskContainsTaskNoExceptionThrown() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(t1);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
            testProject.add(t1);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testAddTaskContainsOtherTaskNoExceptionThrown() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(t1);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
            testProject.add(t2);
            assertEquals(2, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
            assertTrue(testProject.contains(t2));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testAddFromEmptyThrowsNullArgumentException() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(null);
            fail("Didn't throw expected NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test passed - Caught expected NullArgumentException");
        }
    }

    @Test
    public void testAddTaskContainsTaskThrowsNullArgumentException() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(t1);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
            testProject.add(null);
            fail("Didn't throw expected NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test passed - Caught expected NullArgumentException");
        }
    }


    @Test
    public void testAddProjectFromEmptyNoExceptionThrown() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(testSubProject);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(testSubProject));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testAddProjectContainsProjectNoExceptionThrown() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(testSubProject);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(testSubProject));
            testProject.add(testSubProject);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(testSubProject));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testAddProjectContainsOtherProjectNoExceptionThrown() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(testOtherProject);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(testOtherProject));
            testProject.add(testSubProject);
            assertEquals(2, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(testOtherProject));
            assertTrue(testProject.contains(testSubProject));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }


    @Test
    public void testAddProjectContainsProjectThrowsNullArgumentException() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(testSubProject);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(testSubProject));
            testProject.add(null);
            fail("Didn't throw expected NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test passed - Caught expected NullArgumentException");
        }
    }

    @Test
    public void testAddProjectToThisProjectNoExceptionThrown() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(testProject);
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertFalse(testProject.contains(testProject));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testAddProjectThenTaskFromEmptyNoExceptionThrown() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(testSubProject);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(testSubProject));
            testProject.add(t1);
            assertEquals(2, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(testSubProject));
            assertTrue(testProject.contains(t1));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testAddTaskThenProjectFromEmptyNoExceptionThrown() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.add(t1);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
            testProject.add(testSubProject);
            assertEquals(2, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
            assertTrue(testProject.contains(testSubProject));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testRemoveFromEmptyNoExceptionThrown() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.remove(t1);
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testRemoveContainsTaskNoExceptionThrown() {
        try {
            testProject.add(t1);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
            testProject.remove(t1);
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertFalse(testProject.contains(t1));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testRemoveContainsProjectNoExceptionThrown() {
        try {
            testSubProject.add(t1);
            testProject.add(testSubProject);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(testSubProject));
            assertFalse(testProject.contains(t1));
            testProject.remove(testSubProject);
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertFalse(testProject.contains(testSubProject));
            assertFalse(testProject.contains(t1));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testRemoveDoesntContainTaskNoExceptionThrown() {
        try {
            testProject.add(t1);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
            testProject.remove(t2);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testRemoveFromEmptyThrowsNullArgumentException() {
        try {
            assertEquals(0, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            testProject.remove(null);
            fail("Didn't throw expected NullArgument Exception");
        } catch (NullArgumentException e) {
            System.out.println("Test passed - Caught expected NullArgumentException");
        }
    }

    @Test
    public void testRemoveContainsSomeTaskThrowsNullArgumentException() {
        try {
            testProject.add(t1);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(t1));
            testProject.remove(null);
            fail("Didn't throw expected NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test passed - Caught expected NullArgumentException");
        }
    }

    @Test
    public void testRemoveContainsSomeProjectThrowsNullArgumentException() {
        try {
            testSubProject.add(t1);
            testProject.add(testSubProject);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertEquals(0, testProject.getEstimatedTimeToComplete());
            assertTrue(testProject.contains(testSubProject));
            testProject.remove(null);
            fail("Didn't throw expected NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test passed - Caught expected NullArgumentException");
        }
    }

    @Test
    public void testGetProgressNoTasks() {
        assertEquals(0, testProject.getProgress());
    }

    @Test
    public void testGetProgressAllNotFinished() {
        testProject.add(t1);
        assertEquals(0, testProject.getProgress());
    }

    @Test
    public void testGetProgressAllFinishedSingle() {
        t1.setProgress(100);
        testProject.add(t1);
        assertEquals(100, testProject.getProgress());
    }

    @Test
    public void testGetProgressHalfFinishedOneTask() {
        t1.setProgress(50);
        testProject.add(t1);
        assertEquals(50, testProject.getProgress());
    }

    @Test
    public void testGetProgressHalfFinishedTwoTasks() {
        t1.setProgress(100);
        testProject.add(t1);
        testProject.add(t2);
        assertEquals(50, testProject.getProgress());
    }

    @Test
    public void testGetProgressBothFinished() {
        t1.setProgress(100);
        testProject.add(t1);
        t2.setProgress(100);
        testProject.add(t2);
        assertEquals(100, testProject.getProgress());
    }

    @Test
    public void testGetProgress1of3Finished() {
        t1.setProgress(100);
        testProject.add(t1);
        testProject.add(t2);
        testProject.add(t3);
        assertEquals(33, testProject.getProgress());
    }

    @Test
    public void testGetProgress2of3Finished() {
        t1.setProgress(100);
        testProject.add(t1);
        testProject.add(t2);
        t3.setProgress(100);
        testProject.add(t3);
        assertEquals(66, testProject.getProgress());
    }

    @Test
    public void testGetProgressAveragingCorrectly() {
        t1.setProgress(43);
        testProject.add(t1);
        t2.setProgress(67);
        testProject.add(t2);
        t3.setProgress(95);
        testProject.add(t3);
        assertEquals(68, testProject.getProgress());
    }

    @Test
    public void testGetProgressAvgCorrectlySubProjects() {
        t1.setProgress(43);
        testSubProject.add(t1);
        t2.setProgress(67);
        testSubProject.add(t2);
        testProject.add(testSubProject);
        t3.setProgress(95);
        testProject.add(t3);
        assertEquals(75, testProject.getProgress());
    }

    @Test
    public void getEstimatedTimeToCompleteNoTasks() {
        assertEquals(0, testProject.getEstimatedTimeToComplete());
    }

    @Test
    public void getEstimatedTimeToCompleteSingleComplete() {
        testProject.add(t1);
        assertEquals(0, testProject.getEstimatedTimeToComplete());
    }

    @Test
    public void getEstimatedTimeToCompleteSingleIncomplete() {
        t1.setEstimatedTimeToComplete(3);
        testProject.add(t1);
        assertEquals(3, testProject.getEstimatedTimeToComplete());
    }

    @Test
    public void getEstimatedTimeToCompleteTwoIncompleteComplete() {
        t1.setEstimatedTimeToComplete(10);
        testProject.add(t1);
        t2.setEstimatedTimeToComplete(14);
        testProject.add(t2);
        assertEquals(24, testProject.getEstimatedTimeToComplete());
    }

    @Test
    public void testIsCompletedNoTasks() {
        assertFalse(testProject.isCompleted());
    }

    @Test
    public void testIsCompletedSingleComplete() {
        t1.setProgress(100);
        testProject.add(t1);
        assertTrue(testProject.isCompleted());
    }

    @Test
    public void testIsCompletedSingleNotCompete() {
        t1.setProgress(50);
        testProject.add(t1);
        assertFalse(testProject.isCompleted());
    }

    @Test
    public void testIsCompletedTwoComplete() {
        t1.setProgress(100);
        testProject.add(t1);
        t2.setProgress(100);
        testProject.add(t2);
        assertTrue(testProject.isCompleted());
    }

    @Test
    public void testIsCompletedOneCompleteOneNotCompete() {
        t1.setProgress(50);
        testProject.add(t1);
        t2.setProgress(100);
        testProject.add(t2);
        assertFalse(testProject.isCompleted());
    }

    @Test
    public void testIsCompletedTwoIncomplete() {
        t1.setProgress(19);
        testProject.add(t1);
        t2.setProgress(10);
        testProject.add(t2);
        assertFalse(testProject.isCompleted());
    }

    @Test
    public void testContainsNoExceptionThrown() {
        try {
            testProject.add(t1);
            assertEquals(1, testProject.getNumberOfTasks());
            assertEquals(0, testProject.getProgress());
            assertTrue(testProject.contains(t1));
            assertFalse(testProject.contains(t2));
        } catch (NullArgumentException e) {
            fail("Caught unexpected NullArgumentException");
        }
    }

    @Test
    public void testContainsThrowsNullArgumentException() {
        try {
            testProject.contains(null);
            fail("Didn't throw expected NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test passed - Caught expected NullArgumentException");
        }
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(testProject.equals(testProject));
    }

    @Test
    public void testEqualsNotInstanceOfProject() {
        String testInstanceProject = "test/test";
        assertFalse(testProject.equals(testInstanceProject));
    }

    @Test
    public void testEqualsSameDescription() {
        Project testProject2 = new Project("this is a test project");
        assertTrue(testProject.equals(testProject2));
    }

    @Test
    public void testEqualsDifferentDescriptions() {
        Project testProject2 = new Project("this is another test project");
        assertFalse(testProject.equals(testProject2));
    }

    @Test
    public void testHashCode() {
        assertEquals(testProject.hashCode(), testProject.hashCode());
    }

    @Test
    public void testInvalidProgressException() {
        try {
            throw new InvalidProgressException();
        } catch (InvalidProgressException ipe) {
            System.out.println("Test passed - Caught InvalidProgressException");
        }
    }

    @Test
    public void testNegativeInputException() {
        try {
            throw new NegativeInputException();
        } catch (NegativeInputException nie) {
            System.out.println("Test passed - Caught NegativeInputException");
        }
    }

    @Test
    public void testGetTasksDeprecated() {
        try {
            testProject.getTasks();
        } catch (UnsupportedOperationException uoe) {
            System.out.println("Test passed - Caught expected UnsupportedOperationException");
        }
    }
}