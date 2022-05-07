package tuman.learn.java;


import junit.framework.TestCase;
import tuman.learn.java.model.Location;
import tuman.learn.java.model.util.LocationSplitIterator;

import static tuman.learn.java.util.AssertUtil.assertThrow;


public class LocationIterationTest extends TestCase {

    public void testSplitIterator() {
        Location flat = LocationPrinterTest.createData();
        int fullSize = flat.size();

        LocationSplitIterator iterator = flat.splitIterator();
        int count = 0;
        while (iterator.tryAdvance(System.out::println)) {
            count++;
        }

        System.out.printf("Size VS Count: %d, %d\n", fullSize, count);
        assertEquals(fullSize, count);
    }


    public void testStream() {
        Location flat = LocationPrinterTest.createData();
        flat.stream().forEach(System.out::println);
    }


    public void testMaxDepth() {
        Location root = LocationPrinterTest.createData();

        int DEPTH = 1;
        int size = root.size(DEPTH);
        long count = root.stream(DEPTH)
                .map(location -> {
                    System.out.println(location);
                    int depth = location.depth(root);
                    assertTrue(depth >= 0);
                    assertTrue(depth <= DEPTH);
                    return location;
                })
                .count();

        System.out.printf("Depth: %d\n", DEPTH);
        System.out.printf("Size VS Count: %d, %d\n", size, count);
        assertEquals(size, count);
    }

    public void testDepth() {
        Location root = LocationPrinterTest.createData();

        assertEquals(0, root.depth());
        assertEquals(0, root.depth(null));
        assertEquals(0, root.depth(root));

        Location child1 = root.getChildren().get(0);

        assertEquals(1,  child1.depth());
        assertEquals(1,  child1.depth(null));
        assertEquals(1,  child1.depth(root));
        assertEquals(0,  child1.depth(child1));
        assertEquals(-1, root.depth(child1));

        Location child2 = child1.getChildren().get(0);

        assertEquals(2,  child2.depth());
        assertEquals(2,  child2.depth(null));
        assertEquals(2,  child2.depth(root));
        assertEquals(1,  child2.depth(child1));
        assertEquals(0,  child2.depth(child2));
        assertEquals(-1,  child1.depth(child2));
        assertEquals(-2,  root.depth(child2));

        Location other = new Location().init(Location.Type.OTHER, "Other");

        assertThrow(() -> { child1.depth(other); });
        assertThrow(() -> { other.depth(child1); });
    }

}
