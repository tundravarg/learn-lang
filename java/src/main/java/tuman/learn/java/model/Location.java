package tuman.learn.java.model;


import tuman.learn.java.model.util.LocationSplitIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class Location {

    public enum Type {
        OTHER("Other"),
        BUILDING("Building"),
        FLOOR("Floor"),
        SECTION("Section"),
        ROOM("Room"),
        ZONE("Zone"),
        CABINET("Cabinet"),
        CONTAINER("Container");

        private final String label;

        Type(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }


    private static int nextId = 1;

    public static int nextId() {
        return nextId++;
    }

    public static void resetId(int value) {
        nextId = value;
    }

    public static void resetId() {
        resetId(1);
    }


    private Integer id;
    private String name;
    private Type type;
    private Location parent;
    private final List<Location> children = new ArrayList<>();
    private double area;
    private double volume;


    @Override
    public String toString() {
        return String.format("%s #%d '%s', parent: %s, area: %.3f, volume: %.3f",
                type, id, name,
                parent != null ? "#" + parent.getId() : null,
                area, volume);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Location getParent() {
        return parent;
    }

    public void setParent(Location parent) {
        this.parent = parent;
    }

    public List<Location> getChildren() {
        return children;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }


    public LocationSplitIterator splitIterator() {
        return LocationSplitIterator.iterator(this);
    }

    public LocationSplitIterator splitIterator(int depth) {
        return LocationSplitIterator.iterator(this, depth);
    }

    public Stream<Location> stream() {
        return LocationSplitIterator.stream(this);
    }

    public Stream<Location> stream(int depth) {
        return LocationSplitIterator.stream(this, depth);
    }

    public int size() {
        return size(-1);
    }

    public int size(int depth) {
        if (depth == 0) {
            return 1;
        } else {
            return 1 + children.stream().reduce(0, (size, child) -> size + child.size(depth - 1), Integer::sum);
        }
    }

}
