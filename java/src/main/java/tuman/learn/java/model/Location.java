package tuman.learn.java.model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Location {

    public enum Type {
        OTHER,
        BUILDING,
        FLOOR,
        SECTION,
        ROOM,
        ZONE,
        CABINET,
        CONTAINER
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

}
