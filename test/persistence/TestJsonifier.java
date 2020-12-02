package persistence;

import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static persistence.Jsonifier.*;

public class TestJsonifier {

    private Tag testTag;
    private Priority testPriority1;
    private Priority testPriority2;
    private Priority testPriority3;
    private Priority testPriority4;
    private DueDate testDueDate;
    private DueDate nullDueDate;
    private Calendar today;
    private Calendar tomorrow;
    private Task testTask1;
    private Task testTask2;
    private Task testTask3;
    private Task testTask4;
    private List<Task> testTasks;
    private JSONObject jsonTag;
    private JSONObject jsonPriority1;
    private JSONObject jsonPriority2;
    private JSONObject jsonPriority3;
    private JSONObject jsonPriority4;
    private JSONObject jsonDueDate;
    private JSONObject jsonDueDateNull;
    private JSONObject jsonTask1;
    private JSONObject jsonTask2;
    private JSONObject jsonTask3;
    private JSONObject jsonTask4;
    private JSONArray jsonTaskList;


    @BeforeEach
    public void setUp() {
        testTag = new Tag("testTag");

        testPriority1 = new Priority(1);
        testPriority2 = new Priority(2);
        testPriority3 = new Priority(3);
        testPriority4 = new Priority(4);

        today = Calendar.getInstance();
        tomorrow = Calendar.getInstance();
        tomorrow.set(Calendar.DAY_OF_MONTH, (tomorrow.get(Calendar.DATE) + 1));
        testDueDate = new DueDate();
        nullDueDate = null;

        testTask1 = new Task("testTask1");
        testTask2 = new Task("testTask2 ## important; tomorrow; up next; tag1; tag2");
        testTask3 = new Task("testTask3 ## today; Tag; in progress; urgent");
        testTask4 = new Task("testTask4 ## done");

        testTasks = new ArrayList<>();
        testTasks.add(testTask1);
        testTasks.add(testTask2);
        testTasks.add(testTask3);
        testTasks.add(testTask4);

        jsonTag = tagToJson(testTag);
        jsonPriority1 = priorityToJson(testPriority1);
        jsonPriority2 = priorityToJson(testPriority2);
        jsonPriority3 = priorityToJson(testPriority3);
        jsonPriority4 = priorityToJson(testPriority4);
        jsonDueDate = dueDateToJson(testDueDate);
        jsonDueDateNull = dueDateToJson(nullDueDate);
        jsonTask1 = taskToJson(testTask1);
        jsonTask2 = taskToJson(testTask2);
        jsonTask3 = taskToJson(testTask3);
        jsonTask4 = taskToJson(testTask4);
        jsonTaskList = taskListToJson(testTasks);


    }

    @Test
    public void testConstructor() {
        Jsonifier testJsonifier = new Jsonifier();
    }

    @Test
    public void testTagToJSON() {
        assertEquals("testTag", jsonTag.get("name"));
    }

    @Test
    public void testPriority1ToJSON() {
        assertTrue(jsonPriority1.getBoolean("important"));
        assertTrue(jsonPriority1.getBoolean("urgent"));
    }

    @Test
    public void testPriority2ToJSON() {
        assertTrue(jsonPriority2.getBoolean("important"));
        assertFalse(jsonPriority2.getBoolean("urgent"));
    }

    @Test
    public void testPriority3ToJSON() {
        assertFalse(jsonPriority3.getBoolean("important"));
        assertTrue(jsonPriority3.getBoolean("urgent"));
    }

    @Test
    public void testPriority4ToJSON() {
        assertFalse(jsonPriority4.getBoolean("important"));
        assertFalse(jsonPriority4.getBoolean("urgent"));
    }

    @Test
    public void testDueDateNonNullToJSON() {
        assertEquals(today.get(Calendar.YEAR), jsonDueDate.get("year"));
        assertEquals(today.get(Calendar.MONTH), jsonDueDate.get("month"));
        assertEquals(today.get(Calendar.DAY_OF_MONTH), jsonDueDate.get("day"));
        assertEquals(23, jsonDueDate.get("hour"));
        assertEquals(59, jsonDueDate.get("minute"));
    }

    @Test
    public void testDueDateNullToJSON() {
        assertNull(jsonDueDateNull);
    }

    @Test
    public void testTaskToJSONOnlyDescription() {
        assertEquals("testTask1", jsonTask1.get("description"));
        assertEquals(0, jsonTask1.getJSONArray("tags").length());
        assertFalse(jsonTask1.getJSONObject("priority").getBoolean("important"));
        assertFalse(jsonTask1.getJSONObject("priority").getBoolean("urgent"));
        assertEquals("TODO", jsonTask1.get("status"));
        assertEquals(JSONObject.NULL, jsonTask1.get("due-date"));
    }

    @Test
    public void testTaskToJSONParseSomeFields() {
        assertEquals("testTask2 ", jsonTask2.get("description"));
        assertEquals(2, jsonTask2.getJSONArray("tags").length());
        assertEquals("tag1",
                jsonTask2.getJSONArray("tags").getJSONObject(0).get("name"));
        assertEquals("tag2",
                jsonTask2.getJSONArray("tags").getJSONObject(1).get("name"));
        assertTrue(jsonTask2.getJSONObject("priority").getBoolean("important"));
        assertFalse(jsonTask2.getJSONObject("priority").getBoolean("urgent"));
        assertEquals("UP_NEXT", jsonTask2.get("status"));
        assertEquals(tomorrow.get(Calendar.YEAR), jsonTask2.getJSONObject("due-date").get("year"));
        assertEquals(tomorrow.get(Calendar.MONTH), jsonTask2.getJSONObject("due-date").get("month"));
        assertEquals(tomorrow.get(Calendar.DAY_OF_MONTH), jsonTask2.getJSONObject("due-date").get("day"));
        assertEquals(23, jsonTask2.getJSONObject("due-date").get("hour"));
        assertEquals(59, jsonTask2.getJSONObject("due-date").get("minute"));
    }

    @Test
    public void testTaskToJSONParseAllFields() {
        assertEquals("testTask3 ", jsonTask3.get("description"));
        assertEquals(1, jsonTask3.getJSONArray("tags").length());
        assertEquals("Tag",
                jsonTask3.getJSONArray("tags").getJSONObject(0).get("name"));
        assertFalse(jsonTask3.getJSONObject("priority").getBoolean("important"));
        assertTrue(jsonTask3.getJSONObject("priority").getBoolean("urgent"));
        assertEquals("IN_PROGRESS", jsonTask3.get("status"));
        assertEquals(today.get(Calendar.YEAR), jsonTask3.getJSONObject("due-date").get("year"));
        assertEquals(today.get(Calendar.MONTH), jsonTask3.getJSONObject("due-date").get("month"));
        assertEquals(today.get(Calendar.DAY_OF_MONTH), jsonTask3.getJSONObject("due-date").get("day"));
        assertEquals(23, jsonTask3.getJSONObject("due-date").get("hour"));
        assertEquals(59, jsonTask3.getJSONObject("due-date").get("minute"));
    }

    @Test
    public void testTaskToJSONDone() {
        assertEquals("testTask4 ", jsonTask4.get("description"));
        assertEquals(0, jsonTask4.getJSONArray("tags").length());
        assertFalse(jsonTask4.getJSONObject("priority").getBoolean("important"));
        assertFalse(jsonTask4.getJSONObject("priority").getBoolean("urgent"));
        assertEquals("DONE", jsonTask4.get("status"));
        assertEquals(JSONObject.NULL, jsonTask4.get("due-date"));
    }

    @Test
    public void testTaskListToJSON() {
        assertEquals(4, jsonTaskList.length());
        JSONObject jsonTaskListGet0 = (JSONObject) jsonTaskList.get(0);
        assertEquals("testTask1", jsonTaskListGet0.get("description"));
        assertEquals(0, jsonTaskListGet0.getJSONArray("tags").length());
        assertFalse(jsonTaskListGet0.getJSONObject("priority").getBoolean("important"));
        assertFalse(jsonTaskListGet0.getJSONObject("priority").getBoolean("urgent"));
        assertEquals("TODO", jsonTaskListGet0.get("status"));
        assertEquals(JSONObject.NULL, jsonTaskListGet0.get("due-date"));
        JSONObject jsonTaskListGet1 = (JSONObject) jsonTaskList.get(1);
        assertEquals("testTask2 ", jsonTaskListGet1.get("description"));
        assertEquals(2, jsonTaskListGet1.getJSONArray("tags").length());
        assertEquals("tag1",
                jsonTaskListGet1.getJSONArray("tags").getJSONObject(0).get("name"));
        assertEquals("tag2",
                jsonTaskListGet1.getJSONArray("tags").getJSONObject(1).get("name"));
        assertTrue(jsonTaskListGet1.getJSONObject("priority").getBoolean("important"));
        assertFalse(jsonTaskListGet1.getJSONObject("priority").getBoolean("urgent"));
        assertEquals("UP_NEXT", jsonTaskListGet1.get("status"));
        assertEquals(tomorrow.get(Calendar.YEAR), jsonTaskListGet1.getJSONObject("due-date").get("year"));
        assertEquals(tomorrow.get(Calendar.MONTH), jsonTaskListGet1.getJSONObject("due-date").get("month"));
        assertEquals(tomorrow.get(Calendar.DAY_OF_MONTH), jsonTaskListGet1.getJSONObject("due-date").get("day"));
        assertEquals(23, jsonTaskListGet1.getJSONObject("due-date").get("hour"));
        assertEquals(59, jsonTaskListGet1.getJSONObject("due-date").get("minute"));
        JSONObject jsonTaskListGet2 = (JSONObject) jsonTaskList.get(2);
        assertEquals("testTask3 ", jsonTaskListGet2.get("description"));
        assertEquals(1, jsonTaskListGet2.getJSONArray("tags").length());
        assertEquals("Tag",
                jsonTaskListGet2.getJSONArray("tags").getJSONObject(0).get("name"));
        assertFalse(jsonTaskListGet2.getJSONObject("priority").getBoolean("important"));
        assertTrue(jsonTaskListGet2.getJSONObject("priority").getBoolean("urgent"));
        assertEquals("IN_PROGRESS", jsonTaskListGet2.get("status"));
        assertEquals(today.get(Calendar.YEAR), jsonTaskListGet2.getJSONObject("due-date").get("year"));
        assertEquals(today.get(Calendar.MONTH), jsonTaskListGet2.getJSONObject("due-date").get("month"));
        assertEquals(today.get(Calendar.DAY_OF_MONTH), jsonTaskListGet2.getJSONObject("due-date").get("day"));
        assertEquals(23, jsonTaskListGet2.getJSONObject("due-date").get("hour"));
        assertEquals(59, jsonTaskListGet2.getJSONObject("due-date").get("minute"));
        JSONObject jsonTaskListGet3 = (JSONObject) jsonTaskList.get(3);
        assertEquals("testTask4 ", jsonTaskListGet3.get("description"));
        assertEquals(0, jsonTaskListGet3.getJSONArray("tags").length());
        assertFalse(jsonTaskListGet3.getJSONObject("priority").getBoolean("important"));
        assertFalse(jsonTaskListGet3.getJSONObject("priority").getBoolean("urgent"));
        assertEquals("DONE", jsonTaskListGet3.get("status"));
        assertEquals(JSONObject.NULL, jsonTaskListGet3.get("due-date"));
    }
}