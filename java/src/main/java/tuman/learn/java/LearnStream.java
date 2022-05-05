package tuman.learn.java;


import tuman.learn.java.model.Location;
import tuman.learn.java.model.util.LocationBuilder;
import tuman.learn.java.model.util.LocationPrinter;
import tuman.learn.java.utils.TestRun;


public class LearnStream {

    public static void main(String[] args) {
        LearnStream learnStream = new LearnStream();
        learnStream.aggregation();
    }


    private void aggregation() {
        Location house = buildHouse();

        TestRun.run("Flat Area", (name, out) -> {
            Location flat1 = house.getChildren().get(0).getChildren().get(0);
            LocationPrinter.print(flat1, out.getOutStream());

            // Get Flat area
            double flat1Area = flat1.getChildren().stream()
                    .reduce(0.0, (area, room) -> area + room.getArea(), Double::sum);
            out.out("Flat 1 area: %.1f", flat1Area);
            assert flat1Area == 40.0;
        });

        TestRun.run("Floor Area", (name, out) -> {
            Location floor1 = house.getChildren().get(0);
            LocationPrinter.print(floor1, out.getOutStream());

            // Get Floor area by sum of Flats areas
            double floor1Area1 = floor1.getChildren().stream()
                    // Flat Area
                    .map(flat -> flat.getChildren().stream()
                            .reduce(0.0, (a, r) -> a + r.getArea(), Double::sum))
                    // Sum areas
                    .reduce(0.0, Double::sum);
            out.out("Floor 1 area: %.1f", floor1Area1);
            assert floor1Area1 == 40.0 + 34 + 22 + 34 + 50;

            // Get Floor ara by sum of Rooms in Flats areas
            double floor1Area2 = floor1.getChildren().stream()
                    // Get Rooms in Flats
                    .flatMap(flat -> flat.getChildren().stream())
                    // Sum Rooms areas
                    .reduce(0.0, (area, room) -> area + room.getArea(), Double::sum);
            out.out("Floor 1 area: %.1f", floor1Area2);
            assert floor1Area2 == floor1Area1;
        });
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
                    .parent()
                .getRoot();
//        LocationPrinter.stdOut(house);
        return house;
    }

}
