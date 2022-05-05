package tuman.learn.java;


import tuman.learn.java.model.Location;
import tuman.learn.java.model.util.LocationBuilder;
import tuman.learn.java.model.util.LocationPrinter;


public class LearnStream {

    public static void main(String[] args) {
        LearnStream learnStream = new LearnStream();
        learnStream.buildHouse();
    }


    private static Location buildHouse() {
        Location house = LocationBuilder.create()
                .init(Location.Type.BUILDING, "House")
                .location(Location.Type.FLOOR, "1")
                    .children()
                    .location(Location.Type.SECTION, "Flat #1")
                        .children()
                        .location(Location.Type.ROOM, "Hall", 6.0, 0)
                        .location(Location.Type.ROOM, "Bathroom", 4.0, 0)
                        .location(Location.Type.ROOM, "Toilet", 2.0, 0)
                        .location(Location.Type.ROOM, "Kitchen", 10.0, 0)
                        .location(Location.Type.ROOM, "Room 1", 10.0, 0)
                        .location(Location.Type.ROOM, "Room 2", 8.0, 0)
                        .parent()
                    .location(Location.Type.SECTION, "Flat #2")
                        .children()
                        .location(Location.Type.ROOM, "Hall", 6.0, 0)
                        .location(Location.Type.ROOM, "Bathroom", 6.0, 0)
                        .location(Location.Type.ROOM, "Kitchen", 10.0, 0)
                        .location(Location.Type.ROOM, "Room", 12.0, 0)
                        .parent()
                    .location(Location.Type.SECTION, "Flat #3")
                        .children()
                        .location(Location.Type.ROOM, "Bathroom", 6.0, 0)
                        .location(Location.Type.ROOM, "Room 1", 16.0, 0)
                        .parent()
                    .location(Location.Type.SECTION, "Flat #4")
                        .children()
                        .location(Location.Type.ROOM, "Hall", 6.0, 0)
                        .location(Location.Type.ROOM, "Bathroom", 6.0, 0)
                        .location(Location.Type.ROOM, "Kitchen", 10.0, 0)
                        .location(Location.Type.ROOM, "Room", 12.0, 0)
                        .parent()
                    .location(Location.Type.SECTION, "Flat #5")
                        .children()
                        .location(Location.Type.ROOM, "Hall", 6.0, 0)
                        .location(Location.Type.ROOM, "Closet", 2.0, 0)
                        .location(Location.Type.ROOM, "Bathroom", 4.0, 0)
                        .location(Location.Type.ROOM, "Toilet", 2.0, 0)
                        .location(Location.Type.ROOM, "Kitchen", 10.0, 0)
                        .location(Location.Type.ROOM, "Room 1", 10.0, 0)
                        .location(Location.Type.ROOM, "Room 2", 8.0, 0)
                        .location(Location.Type.ROOM, "Room 3", 8.0, 0)
                        .parent()
                .location(Location.Type.FLOOR, "2")
                    .children()
                    .location(Location.Type.SECTION, "Flat #6")
                        .children()
                        .location(Location.Type.ROOM, "Hall", 6.0, 0)
                        .location(Location.Type.ROOM, "Bathroom", 4.0, 0)
                        .location(Location.Type.ROOM, "Toilet", 2.0, 0)
                        .location(Location.Type.ROOM, "Kitchen", 10.0, 0)
                        .location(Location.Type.ROOM, "Room 1", 10.0, 0)
                        .location(Location.Type.ROOM, "Room 2", 8.0, 0)
                        .parent()
                    .location(Location.Type.SECTION, "Flat #7")
                        .children()
                        .location(Location.Type.ROOM, "Hall", 6.0, 0)
                        .location(Location.Type.ROOM, "Bathroom", 6.0, 0)
                        .location(Location.Type.ROOM, "Kitchen", 10.0, 0)
                        .location(Location.Type.ROOM, "Room", 12.0, 0)
                        .parent()
                    .location(Location.Type.SECTION, "Flat #8")
                        .children()
                        .location(Location.Type.ROOM, "Bathroom", 6.0, 0)
                        .location(Location.Type.ROOM, "Room 1", 16.0, 0)
                        .parent()
                    .location(Location.Type.SECTION, "Flat #9")
                        .children()
                        .location(Location.Type.ROOM, "Hall", 6.0, 0)
                        .location(Location.Type.ROOM, "Bathroom", 6.0, 0)
                        .location(Location.Type.ROOM, "Kitchen", 10.0, 0)
                        .location(Location.Type.ROOM, "Room", 12.0, 0)
                        .parent()
                    .location(Location.Type.SECTION, "Flat #10")
                        .children()
                        .location(Location.Type.ROOM, "Hall", 6.0, 0)
                        .location(Location.Type.ROOM, "Closet", 2.0, 0)
                        .location(Location.Type.ROOM, "Bathroom", 4.0, 0)
                        .location(Location.Type.ROOM, "Toilet", 2.0, 0)
                        .location(Location.Type.ROOM, "Kitchen", 10.0, 0)
                        .location(Location.Type.ROOM, "Room 1", 10.0, 0)
                        .location(Location.Type.ROOM, "Room 2", 8.0, 0)
                        .location(Location.Type.ROOM, "Room 3", 8.0, 0)
                        .parent()
                .getRoot();
        LocationPrinter.stdOut(house);
        return house;
    }

}
