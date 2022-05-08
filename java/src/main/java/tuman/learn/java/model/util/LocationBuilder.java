package tuman.learn.java.model.util;


import tuman.learn.java.model.Location;

import java.util.ArrayList;
import java.util.List;


public class LocationBuilder {

    public static class IdGenerator {

        private int nextId;

        public IdGenerator() {
            this(null);
        }

        public IdGenerator(int initial) {
            this.nextId = initial;
        }

        public IdGenerator(Location location) {
            this.nextId = findMaxId(0, getRoot(location)) + 1;
        }

        public int next() {
            return nextId++;
        }

        private static Location getRoot(Location location) {
            if (location == null) {
                return null;
            }
            Location root = location;
            while (root.getParent() != null) {
                root = root.getParent();
            }
            return root;
        }

        private static int findMaxId(int maxId, Location location) {
            if (location == null || location.getId() == null) {
                return maxId;
            }
            if (location.getId() > maxId) {
                maxId = location.getId();
            }
            for (Location child: location.getChildren()) {
                maxId = findMaxId(maxId, child);
            }
            return maxId;
        }

    }


    private final IdGenerator nextId;
    private final Location root;
    private Location current;
    private Location parent;


    public LocationBuilder() {
        this(null);
    }

    public LocationBuilder(Location root) {
        this.root = root != null ? root : new Location();
        this.nextId = new IdGenerator(root);
        this.current = this.root;
        this.parent = null;
    }


    public static LocationBuilder create() {
        return new LocationBuilder();
    }

    public static LocationBuilder create(Location root) {
        return new LocationBuilder(root);
    }


    private void checkCurrent() {
        if (current == null) {
            throw new IllegalStateException("No current");
        }
    }

    private void checkParent() {
        if (parent == null) {
            throw new IllegalStateException("No parent");
        }
    }


    public LocationBuilder children() {
        checkCurrent();
        parent = current;
        current = null;
        return this;
    }

    public LocationBuilder parent() {
        checkParent();
        current = parent;
        parent = current != root ? current.getParent() : null;
        return this;
    }

    public LocationBuilder location() {
        if (current == root) {
            children();
        }
        checkParent();
        current = new Location();
        current.setId(nextId.next());
        if (parent.getId() != null) {
            current.setParent(parent);
        }
        parent.getChildren().add(current);
        return this;
    }

    public LocationBuilder location(Location.Type type, String name) {
        return location().init(type, name);
    }

    public LocationBuilder location(Location.Type type, String name, double area, double volume) {
        return location().init(type, name, area, volume);
    }


    public LocationBuilder init(Location.Type type, String name) {
        checkCurrent();
        return id().type(type).name(name);
    }

    public LocationBuilder init(Location.Type type, String name, double area, double volume) {
        checkCurrent();
        return init(type, name).area(area).volume(volume);
    }


    public LocationBuilder id() {
        checkCurrent();
        if (current.getId() == null) {
            current.setId(nextId.next());
            current.getChildren().stream().forEach(c -> c.setParent(current));
        }
        return this;
    }

    public LocationBuilder name(String name) {
        checkCurrent();
        current.setName(name);
        return this;
    }

    public LocationBuilder type(Location.Type type) {
        checkCurrent();
        current.setType(type);
        return this;
    }

    public LocationBuilder area(double area) {
        checkCurrent();
        current.setArea(area);
        return this;
    }

    public LocationBuilder volume(double volume) {
        checkCurrent();
        current.setVolume(volume);
        return this;
    }


    public Location getRoot() {
        return root;
    }

    public Location getCurrent() {
        return current;
    }

    public List<Location> build() {
        var locations = new ArrayList<Location>();
        if (root.getId() != null) {
            root.getChildren().stream().forEach(c -> c.setParent(root));
            locations.add(root);
        } else {
            root.getChildren().stream().forEach(c -> c.setParent(null));
            locations.addAll(root.getChildren());
        }
        return locations;
    }

}
