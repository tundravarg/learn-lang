package tuman.learn.java;


import org.junit.Test;
import tuman.learn.java.model.Location;
import tuman.learn.java.model.util.LocationSplitIterator;


public class LocationIterationTest {

    @Test
    public void testSplitIterator() {
        Location flat = LocationPrinterTest.createData();
        LocationSplitIterator iterator = flat.splitIterator();
        while (iterator.tryAdvance(System.out::println));
    }


    @Test
    public void testStream() {
        Location flat = LocationPrinterTest.createData();
        flat.stream().forEach(System.out::println);
    }

}
