package tuman.learn.java;


import org.junit.Test;
import tuman.learn.java.model.Location;
import tuman.learn.java.model.util.LocationBuilder;

import static org.junit.Assert.*;
import static tuman.learn.java.util.AssertUtil.assertThrow;
import static tuman.learn.java.util.AssertUtil.checkClassAndMessage;


public class LocationBuilderTest {

    @Test
    public void testRoot() {
        var builder = new LocationBuilder();

        var root = builder.build();
        assertClearLocation(root, false);

        assertNull(root.getId());
        builder.id();
        assertEquals(1, root.getId().intValue());
        builder.id();
        assertEquals(1, root.getId().intValue());

        builder .id()
                .name("root")
                .type(Location.Type.OTHER)
                .area(5.0)
                .volume(12.5);
        assertLocation(root, 1, "root", Location.Type.OTHER, 5.0, 12.5);
    }

    @Test
    public void testSetters() {
        var builder = new LocationBuilder();

        var root = builder.getCurrent();
        assertNull(root.getId());
        builder.id();
        assertEquals(1, root.getId().intValue());

        var loc1 = builder.location()
                .name("room-1")
                .type(Location.Type.ROOM)
                .area(5.0)
                .volume(12.5)
                .getCurrent();
        assertLocation(loc1, 2, "room-1", Location.Type.ROOM, 5.0, 12.5);

        var loc2 = builder.location()
                .name("zone-1")
                .type(Location.Type.ZONE)
                .area(2.0)
                .volume(4.0)
                .getCurrent();
        assertLocation(loc1, 2, "room-1", Location.Type.ROOM, 5.0, 12.5);
        assertLocation(loc2, 3, "zone-1", Location.Type.ZONE, 2.0, 4.0);
    }

    @Test
    public void testBuild() {
        var builder = new LocationBuilder();
        Location loc0 = builder.getCurrent();
        Location loc1 = builder.location().getCurrent();
        Location root = builder.getRoot();
        Location built = builder.build();

        assertClearLocation(loc0, false);
        assertClearLocation(loc1, true);
        assertClearLocation(root, false);
        assertClearLocation(built, false);

        assertEquals(built, root);
        assertEquals(built, loc0);
        assertNotEquals(built, loc1);
    }

    @Test
    public void testOperations() {
        var builder = new LocationBuilder();

        // Current is root initially
        builder.name("A");
        assertEquals("A", builder.getCurrent().getName());

        // Locations are added to current
        Location cur = builder.getCurrent();
        Location loc1 = builder.location().getCurrent();
        Location loc2 = builder.location().getCurrent();
        assertEquals(2, cur.getChildren().size());
        assertNotEquals(loc1, loc2);
        assertNotNull(loc1.getId());
        assertNotNull(loc2.getId());
        assertEquals(loc1.getId() + 1, loc2.getId().intValue());

        // Children
        cur = builder.getCurrent();
        builder.children();
        // No current at this moment
        assertThrow(() -> builder.name("A"), checkClassAndMessage(IllegalStateException.class, "No current"));
        assertThrow(() -> builder.type(Location.Type.ROOM), checkClassAndMessage(IllegalStateException.class, "No current"));
        assertThrow(() -> builder.area(1.0), checkClassAndMessage(IllegalStateException.class, "No current"));
        assertThrow(() -> builder.volume(1.0), checkClassAndMessage(IllegalStateException.class, "No current"));
        // Add child location to current
        loc1 = builder.location().name("A").type(Location.Type.ZONE).area(1.1).volume(2.2).getCurrent();
        assertLocation(loc1, cur.getId() + 1, "A", Location.Type.ZONE, 1.1, 2.2);
        assertEquals(1, cur.getChildren().size());
        assertEquals(2, cur.getParent().getChildren().size());

        // Parent
        cur = builder.parent()
                .location()
                .getCurrent();
        assertEquals(3, );
    }


    private static void assertClearLocation(Location location, boolean hasId) {
        assertLocation(location, null, null, 0.0, 0.0);
        if (hasId) {
            assertNotNull(location.getId());
        } else {
            assertNull(location.getId());
        }
    }

    private static void assertLocation(
            Location location,
            String name, Location.Type type,
            double area, double volume) {
        assertNotNull(location);
        assertEquals(name, location.getName());
        assertEquals(type, location.getType());
        assertEquals(area, location.getArea(), 0.0);
        assertEquals(volume, location.getVolume(), 0.0);
    }

    private static void assertLocation(
            Location location,
            Integer id,
            String name, Location.Type type,
            double area, double volume) {
        assertNotNull(location);
        assertEquals(id, location.getId());
        assertEquals(name, location.getName());
        assertEquals(type, location.getType());
        assertEquals(area, location.getArea(), 0.0);
        assertEquals(volume, location.getVolume(), 0.0);
    }

}
