package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestProjectIterator {

    private Project testProject;
    private Project testSubProject1;
    private Project testSubProject2;
    private Project testSubProject3;
    private Project testSubProject4;
    private Project testSubProject1a;
    private Project testSubProject2a;
    private Project testSubProject3a;
    private Project testSubProject4a;
    private Task t1;
    private Task t2;
    private Task t3;
    private Task t4;
    private Task t1a;
    private Task t2a;
    private Task t3a;
    private Task t4a;

    @BeforeEach
    public void setUp() {
        testProject = new Project("this is a test project");
        testSubProject1 = new Project("this is test sub-project 1");
        testSubProject1.setPriority(new Priority(1));
        testSubProject2 = new Project("this is test sub-project 2");
        testSubProject2.setPriority(new Priority(2));
        testSubProject3 = new Project("this is test sub-project 3");
        testSubProject3.setPriority(new Priority(3));
        testSubProject4 = new Project("this is test sub-project 4");
        testSubProject1a = new Project("this is test sub-project 1a");
        testSubProject1a.setPriority(new Priority(1));
        testSubProject2a = new Project("this is test sub-project 2a");
        testSubProject2a.setPriority(new Priority(2));
        testSubProject3a = new Project("this is test sub-project 3a");
        testSubProject3a.setPriority(new Priority(3));
        testSubProject4a = new Project("this is test sub-project 4a");
        t1 = new Task("task 1 ## important; urgent");
        t2 = new Task("task 2 ## important");
        t3 = new Task("task 3 ## urgent");
        t4 = new Task("task 4");
        t1a = new Task("task 1a ## important; urgent");
        t2a = new Task("task 2a ## important");
        t3a = new Task("task 3a ## urgent");
        t4a = new Task("task 4a");
    }

    @Test
    public void testEmpty() {
        Iterator<Todo> it = testProject.iterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void testSingleTaskP1() {
        testProject.add(t1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testSingleTaskP2() {
        testProject.add(t2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testSingleTaskP3() {
        testProject.add(t3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testSingleTaskP4() {
        testProject.add(t4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testSingleProjectP1() {
        testProject.add(testSubProject1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testSingleProjectP2() {
        testProject.add(testSubProject2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testSingleProjectP3() {
        testProject.add(testSubProject3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testSingleProjectP4() {
        testProject.add(testSubProject4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP1P2() {
        testProject.add(t1);
        testProject.add(t2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP2P1() {
        testProject.add(t2);
        testProject.add(t1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP1P3() {
        testProject.add(t1);
        testProject.add(t3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP3P1() {
        testProject.add(t3);
        testProject.add(t1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP1P4() {
        testProject.add(t1);
        testProject.add(t4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP4P1() {
        testProject.add(t4);
        testProject.add(t1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP2P3() {
        testProject.add(t2);
        testProject.add(t3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP3P2() {
        testProject.add(t3);
        testProject.add(t2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP2P4() {
        testProject.add(t2);
        testProject.add(t4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP4P2() {
        testProject.add(t4);
        testProject.add(t2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP3P4() {
        testProject.add(t3);
        testProject.add(t4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP4P3() {
        testProject.add(t4);
        testProject.add(t3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP1P2P3P4() {
        testProject.add(t1);
        testProject.add(t2);
        testProject.add(t3);
        testProject.add(t4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP3P2P1P4() {
        testProject.add(t3);
        testProject.add(t2);
        testProject.add(t1);
        testProject.add(t4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP2P4P3P1() {
        testProject.add(t2);
        testProject.add(t4);
        testProject.add(t3);
        testProject.add(t1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP4P3P2P1() {
        testProject.add(t4);
        testProject.add(t3);
        testProject.add(t2);
        testProject.add(t1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP1P1() {
        testProject.add(t1);
        testProject.add(t1a);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t1a, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP2P2() {
        testProject.add(t2);
        testProject.add(t2a);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t2a, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP3P3() {
        testProject.add(t2);
        testProject.add(t2a);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t2a, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP4P4() {
        testProject.add(t4);
        testProject.add(t4a);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4a, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP1P1Flipped() {
        testProject.add(t1a);
        testProject.add(t1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1a, it.next());
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP2P2Flipped() {
        testProject.add(t2a);
        testProject.add(t2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t2a, it.next());
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP3P3Flipped() {
        testProject.add(t3a);
        testProject.add(t3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t3a, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTaskP4P4Flipped() {
        testProject.add(t4a);
        testProject.add(t4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t4a, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP1P2() {
        testProject.add(testSubProject1);
        testProject.add(testSubProject2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP2P1() {
        testProject.add(testSubProject2);
        testProject.add(testSubProject1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP1P3() {
        testProject.add(testSubProject1);
        testProject.add(testSubProject3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP3P1() {
        testProject.add(testSubProject3);
        testProject.add(testSubProject1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP1P4() {
        testProject.add(testSubProject1);
        testProject.add(testSubProject4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP4P1() {
        testProject.add(testSubProject4);
        testProject.add(testSubProject1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP2P3() {
        testProject.add(testSubProject2);
        testProject.add(testSubProject3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP3P2() {
        testProject.add(testSubProject3);
        testProject.add(testSubProject2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP2P4() {
        testProject.add(testSubProject2);
        testProject.add(testSubProject4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP4P2() {
        testProject.add(testSubProject4);
        testProject.add(testSubProject2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP3P4() {
        testProject.add(testSubProject3);
        testProject.add(testSubProject4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP4P3() {
        testProject.add(testSubProject4);
        testProject.add(testSubProject3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP1P2P3P4() {
        testProject.add(testSubProject1);
        testProject.add(testSubProject2);
        testProject.add(testSubProject3);
        testProject.add(testSubProject4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP3P2P1P4() {
        testProject.add(testSubProject3);
        testProject.add(testSubProject2);
        testProject.add(testSubProject1);
        testProject.add(testSubProject4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP2P4P3P1() {
        testProject.add(testSubProject2);
        testProject.add(testSubProject4);
        testProject.add(testSubProject3);
        testProject.add(testSubProject1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP4P3P2P1() {
        testProject.add(testSubProject4);
        testProject.add(testSubProject3);
        testProject.add(testSubProject2);
        testProject.add(testSubProject1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP1P1() {
        testProject.add(testSubProject1);
        testProject.add(testSubProject1a);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject1a, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP2P2() {
        testProject.add(testSubProject2);
        testProject.add(testSubProject2a);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject2a, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP3P3() {
        testProject.add(testSubProject3);
        testProject.add(testSubProject3a);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3a, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP4P4() {
        testProject.add(testSubProject4);
        testProject.add(testSubProject4a);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4a, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP1P1Flipped() {
        testProject.add(testSubProject1a);
        testProject.add(testSubProject1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1a, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP2P2Flipped() {
        testProject.add(testSubProject2a);
        testProject.add(testSubProject2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject2a, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP3P3Flipped() {
        testProject.add(testSubProject3a);
        testProject.add(testSubProject3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject3a, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProjectP4P4Flipped() {
        testProject.add(testSubProject4a);
        testProject.add(testSubProject4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject4a, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTask1Project1() {
        testProject.add(t1);
        testProject.add(testSubProject1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProject1Task1() {
        testProject.add(testSubProject1);
        testProject.add(t1);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t1, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTask2Project2() {
        testProject.add(t2);
        testProject.add(testSubProject2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProject2Task2() {
        testProject.add(testSubProject2);
        testProject.add(t2);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject2, it.next());
        assertTrue(it.hasNext());
        assertEquals(t2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTask3Project3() {
        testProject.add(t3);
        testProject.add(testSubProject3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProject3Task3() {
        testProject.add(testSubProject3);
        testProject.add(t3);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject3, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTask4Project4() {
        testProject.add(t4);
        testProject.add(testSubProject4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testProject4Task4() {
        testProject.add(testSubProject4);
        testProject.add(t4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject4, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testOnlyImmediateChildren() {
        testSubProject1.add(t1);
        testSubProject1.add(t2);
        testProject.add(t3);
        testProject.add(testSubProject1);
        testProject.add(t4);
        Iterator<Todo> it = testProject.iterator();
        assertTrue(it.hasNext());
        assertEquals(testSubProject1, it.next());
        assertTrue(it.hasNext());
        assertEquals(t3, it.next());
        assertTrue(it.hasNext());
        assertEquals(t4, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testNoNextThrowsException() {
        try {
            Iterator<Todo> it = testProject.iterator();
            assertFalse(it.hasNext());
            it.next();
            fail("Didn't throw expected NoSuchElementException");
        } catch (NoSuchElementException nsee) {
            System.out.println("Test passed - Caught expected NoSuchElementException");
        }
    }
}