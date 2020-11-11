package factory;

import model.Tag;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static factory.TagFactory.printTagInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTagFactory {

    private Task task1a;
    private Task task2a;
    private List<Tag> tags;
    private Tag groceryShopping;

    private Task task1b;
    private Task task2b;
    private Tag tag1b;
    private Tag tag2b;

    @BeforeEach
    public void setUp() {
        task1a = new Task("Buy milk ## Grocery shopping");
        task2a = new Task("Buy bread ## Grocery shopping");

        task1b = new Task("Buy milk B Series");
        task2b = new Task("Buy bread B Series");
        tag1b = TagFactory.getTagWithName("Grocery shopping B Series");
        tag2b = TagFactory.getTagWithName("Grocery shopping B Series");
        task1b.addTag(tag1b);
        task2b.addTag(tag2b);
    }

    @Test
    public void testConstructor() {
        TagFactory tf = new TagFactory();
    }

    @Test
    public void testASeries1() {
        tags = new ArrayList<>(task1a.getTags());
        groceryShopping = tags.get(0);
        assertEquals(2, groceryShopping.getTasks().size());
        printTagInfo(groceryShopping);
    }

    @Test
    public void testASeries2() {
        tags = new ArrayList<>(task2a.getTags());
        groceryShopping = tags.get(0);
        assertEquals(2, groceryShopping.getTasks().size());
        printTagInfo(groceryShopping);
    }

    @Test
    public void testBSeriesCorrectPrint1() {
        printTagInfo(tag1b);
    }

    @Test
    public void testBSeriesCorrectPrint2() {
        printTagInfo(tag2b);
    }
}