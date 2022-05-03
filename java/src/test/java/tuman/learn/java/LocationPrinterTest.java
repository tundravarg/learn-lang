package tuman.learn.java;


import org.junit.Before;
import org.junit.Test;
import tuman.learn.java.model.Location;
import tuman.learn.java.model.util.LocationBuilder;
import tuman.learn.java.model.util.LocationPrinter;


public class LocationPrinterTest {

    private Location root;


    @Before
    public void init() {
        root = new LocationBuilder()
                .id()
                .type(Location.Type.SECTION)
                .name("Flat #123")
                .location(Location.Type.ROOM, "Hall")
                    .area(6.0)
                    .children()
                    .location(Location.Type.CABINET, "Wardrobe")
                    .location(Location.Type.CABINET, "Cabinet")
                    .location(Location.Type.CABINET, "Mirror")
                    .parent()
                .location(Location.Type.ROOM, "Room")
                    .area(15.0)
                    .children()
                    .location(Location.Type.CABINET, "Wardrobe")
                    .parent()
                .location(Location.Type.ROOM, "Kitchen")
                    .area(12.0)
                    .children()
                    .location(Location.Type.CABINET, "Cupboard")
                    .location(Location.Type.CABINET, "Cabinet-1")
                    .location(Location.Type.CABINET, "Cabinet-2")
                    .location(Location.Type.CABINET, "Fridge")
                    .parent()
                .location(Location.Type.ROOM, "Bathroom")
                    .area(6.0)
                    .children()
                    .location(Location.Type.CABINET, "Washstand")
                    .location(Location.Type.CONTAINER, "Container-1")
                        .volume(1.0)
                    .location(Location.Type.CONTAINER, "Container-2")
                        .volume(0.7)
                    .parent()
                .getRoot();
    }


    @Test
    public void testToString() {
        System.out.println("<<<");
        System.out.println(LocationPrinter.toString(root));
        System.out.println(">>>");
    }

    @Test
    public void testPrint() {
        System.out.println("<<<");
        LocationPrinter.print(root, System.out);
        System.out.println(">>>");
    }

    @Test
    public void testStdOut() {
        System.out.println("<<<");
        LocationPrinter.stdOut(root);
        System.out.println(">>>");
    }

}
