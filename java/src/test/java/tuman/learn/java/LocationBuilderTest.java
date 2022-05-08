package tuman.learn.java;


import org.junit.Test;
import tuman.learn.java.model.Location;
import tuman.learn.java.model.util.LocationBuilder;

import java.util.List;

import static org.junit.Assert.*;
import static tuman.learn.java.util.AssertUtil.assertThrow;
import static tuman.learn.java.util.AssertUtil.checkClassAndMessage;


public class LocationBuilderTest {

    @Test
    public void testRoot() {
        var builder = new LocationBuilder();

        var root = builder.getRoot();
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

        // Before first call `id()` on root, its `id` is `null`;
        var root = builder.getCurrent();
        assertNull(root.getId());
        // First call `id()` sets `id` on root
        builder.id();
        assertEquals(1, root.getId().intValue());
        // Next calls `id()` change nothing
        builder.id();
        assertEquals(1, root.getId().intValue());

        // Fill Location properties
        var loc1 = builder.location()
                .name("room-1")
                .type(Location.Type.ROOM)
                .area(5.0)
                .volume(12.5)
                .getCurrent();
        assertLocation(loc1, 2, "room-1", Location.Type.ROOM, 5.0, 12.5);
        // Call `id()` on non root does nothing
        builder.id();
        assertEquals(2, builder.getCurrent().getId().intValue());

        // Fill another Location properties
        var loc2 = builder.location()
                .name("zone-1")
                .type(Location.Type.ZONE)
                .area(2.0)
                .volume(4.0)
                .getCurrent();
        assertLocation(loc2, 3, "zone-1", Location.Type.ZONE, 2.0, 4.0);
        // It doesn't change other nodes
        assertLocation(loc1, 2, "room-1", Location.Type.ROOM, 5.0, 12.5);
    }

    @Test
    public void testBuild() {
        var builder = new LocationBuilder();

        // Initial state.
        Location root = builder.getRoot();
        // Root id must be null;
        assertNull(root.getId());

        // Add locations to root
        // Don't need to call `children()` on root
        builder .location()
                .location();
        assertEquals(2, root.getChildren().size());
        assertEquals(1, root.getChildren().get(0).getId().intValue());
        assertEquals(2, root.getChildren().get(1).getId().intValue());

        // If root has null id, build result is a list of added elements with no parent
        List<Location> built = builder.build();
        assertNotSame(root.getChildren(), built);
        assertEquals(root.getChildren().size(), built.size());
        assertSame(root.getChildren().get(0), built.get(0));
        assertSame(root.getChildren().get(1), built.get(1));
        // No parent if `root.id` is `null`
        assertNull(built.get(0).getParent());
        assertNull(built.get(1).getParent());

        // If root has non null id, build result is a list with one element - root
        // Its children has non null link to parent, which is root element
        root.setId(1000);
        built = builder.build();
        assertEquals(1, built.size());
        assertSame(root, built.get(0));
        // Parent is `root` if `root.id` is not `null`
        assertSame(root, built.get(0).getChildren().get(0).getParent());
        assertSame(root, built.get(0).getChildren().get(1).getParent());
        // New elements has parent if its `id` is not `null`
        assertSame(root, builder.location().getCurrent().getParent());

        Location loc1 = builder.getCurrent();
        assertNotSame(loc1, root);
        assertEquals(0, loc1.getChildren().size());

        builder.children();
        // Can't set properties exactly after `children()`
        // No current at this moment
        assertThrow(() -> builder.name("A"), checkClassAndMessage(IllegalStateException.class, "No current"));
        assertThrow(() -> builder.type(Location.Type.ROOM), checkClassAndMessage(IllegalStateException.class, "No current"));
        assertThrow(() -> builder.area(1.0), checkClassAndMessage(IllegalStateException.class, "No current"));
        assertThrow(() -> builder.volume(1.0), checkClassAndMessage(IllegalStateException.class, "No current"));

        Location cur = builder.location()
                .name("A")
                .type(Location.Type.ZONE)
                .area(1.1)
                .volume(2.2)
                .getCurrent();
        assertLocation(cur, loc1.getId() + 1, "A", Location.Type.ZONE, 1.1, 2.2);
        assertEquals(1, loc1.getChildren().size());
        assertSame(loc1.getChildren().get(0), cur);
        assertSame(loc1, cur.getParent());

        builder.location();
        assertEquals(2, loc1.getChildren().size());

        cur = builder.parent().getCurrent();
        assertSame(loc1, cur);
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
