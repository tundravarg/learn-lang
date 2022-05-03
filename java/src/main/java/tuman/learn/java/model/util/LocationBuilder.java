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

        public IdGenerator(Location root) {
            this.nextId = findMaxId(0, root) + 1;
        }

        public int next() {
            return nextId++;
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


    private IdGenerator nextId;
    private Location root;
    private Location current;
    private Location parent;


    public LocationBuilder() {
        this(null);
    }

    public LocationBuilder(Location root) {

        this.nextId = new IdGenerator(this.root);
        this.root = root != null ? root : new Location();
        this.current = this.root;
        this.parent = null;
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


    public Location build() {
        return root;
    }

    public Location getRoot() {
        return root;
    }

    public Location getCurrent() {
        return current;
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
        current = new Location();
        current.setId(nextId.next());
        current.setParent(parent);
        parent.getChildren().add(current);
        return this;
    }

    public LocationBuilder id() {
        checkCurrent();
        if (current.getId() == null) {
            current.setId(nextId.next());
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

}
