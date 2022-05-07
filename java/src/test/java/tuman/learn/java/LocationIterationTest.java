package tuman.learn.java;


import junit.framework.TestCase;
import tuman.learn.java.model.Location;
import tuman.learn.java.model.util.LocationSplitIterator;

import java.util.function.Function;


public class LocationIterationTest extends TestCase {

    public void testSplitIterator() {
        Location flat = LocationPrinterTest.createData();
        int fullSize = flat.size();

        LocationSplitIterator iterator = flat.splitIterator();
        int count = 0;
        while (iterator.tryAdvance(System.out::println)) {
            count++;
        }

        assertEquals(fullSize, count);
    }


    public void testStream() {
        Location flat = LocationPrinterTest.createData();
        flat.stream().forEach(System.out::println);
    }


    public void testMaxDepth() {
        Location root = LocationPrinterTest.createData();

        Function<Location, Integer> locationDepth = (location) -> {
            int depth = 0;
            Location current = location;
            while (current != null && current != root) {
                depth++;
                current = current.getParent();
            }
            assertEquals(root, current);
            return depth;
        };

        int DEPTH = 1;
        int size = root.size(DEPTH);
        long count = root.stream(DEPTH)
                .map(location -> {
                    System.out.println(location);
                    int depth = locationDepth.apply(location);
                    assertTrue(depth >= 0);
                    assertTrue(depth <= DEPTH);
                    return location;
                })
                .count();
        assertEquals(size, count);
    }

}
