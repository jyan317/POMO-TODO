package factory;

import model.Tag;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class TagFactory {

    private static List<Tag> tags = new ArrayList<>();

    public static void printTagInfo(Tag tag) {
        System.out.println("Tasks that are tagged with " + tag.getName() + " are:");
        for (Task t : tag.getTasks()) {
            System.out.println("\t" + t.getDescription());
        }
    }

    public static Tag getTagWithName(String name) {
        Tag incomingTag = new Tag(name);
        Tag output = incomingTag;
        if (!(tags.contains(incomingTag))) {
            tags.add(incomingTag);
        } else {
            for (Tag t : tags) {
                if (t.getName().equals(name)) {
                    output = t;
                }
            }
        }
        return output;
    }
}