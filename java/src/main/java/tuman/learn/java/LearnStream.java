package tuman.learn.java;


import org.javatuples.Pair;
import tuman.learn.java.model.Location;
import tuman.learn.java.model.util.LocationBuilder;
import tuman.learn.java.model.util.LocationPrinter;
import tuman.learn.java.utils.ObjectHolder;
import tuman.learn.java.utils.TestRun;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class LearnStream {

    public static void main(String[] args) {
        System.out.println("==== Learn Streams ====");
        LearnStream learnStream = new LearnStream();

        learnStream.generation();
//        learnStream.reduction1();
//        learnStream.reduction2();
//        learnStream.grouping();
    }


    private void generation() {
        final IntConsumer printInt = e -> System.out.println(e);

        TestRun.run("Concrete elements", (name, out) -> {
            IntStream.of(3).forEach(printInt);
            IntStream.of(7, 6, 2).forEach(printInt);
        });

        TestRun.run("Range Open", (name, out) -> {
            IntStream.range(3, 7).forEach(printInt);
        });

        TestRun.run("Range Closed", (name, out) -> {
            IntStream.rangeClosed(3, 7).forEach(printInt);
        });

        TestRun.run("Generate const", (name, out) -> {
            IntStream.generate(() -> 1).limit(5).forEach(printInt);
        });

        TestRun.run("Generate fibo", (name, out) -> {
            final int v[] = {0, 1};
            IntStream.generate(() -> { int nv = v[0] + v[1]; v[0] = v[1]; v[1] = nv; return nv; })
                    .limit(10)
                    .forEach(printInt);
        });

        TestRun.run("Generate iterate", (name, out) -> {
            IntStream.iterate(1, v -> v * 2)
                    .limit(10)
                    .forEach(printInt);
        });

        TestRun.run("Concat", (name, out) -> {
            IntStream.concat(IntStream.of(1, 3, 5), IntStream.of(6, 4, 2))
                    .sorted()
                    .forEach(printInt);
        });
    }


    private void reduction1() {
        Location house = buildHouse();

        TestRun.run("Flat Area", (name, out) -> {

            Location flat1 = house.getChildren().get(0).getChildren().get(0);
            LocationPrinter.print(flat1, System.out);

            // Get Flat area
            double flat1Area = flat1.getChildren().stream()
                    .reduce(0.0, (area, room) -> area + room.getArea(), Double::sum);
            out.out("Flat 1 area: %.1f", flat1Area);
            assert flat1Area == 40.0;

        });

        TestRun.run("Floor Area", (name, out) -> {

            Location floor1 = house.getChildren().get(0);
            LocationPrinter.print(floor1, System.out);

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


    private void reduction2() {
        int[] numberArray = {-15, 0, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89};
        Collection<Integer> numberCollection = Arrays.stream(numberArray).boxed().collect(Collectors.toSet());

        int min1 = Arrays.stream(numberArray).min().getAsInt();
        int min2 = numberCollection.stream().min(Comparator.comparingInt(e -> e.intValue())).get();

        int min3 = Arrays.stream(numberArray).reduce(Integer.MAX_VALUE, (min, e) -> e < min ? e : min);
        int min4 = numberCollection.stream().reduce(Integer.MAX_VALUE, (min, e) -> e < min ? e : min);

        int min5 = numberCollection.stream()
                .collect(Collector.of(
                    () -> ObjectHolder.of(Integer.MAX_VALUE),
                    (min, e) -> { if (e < min.get()) min.set(e); },
                    (e1, e2) -> e1.get().intValue() < e2.get().intValue() ? e1 : e2
                ))
                .get();

        System.out.printf("Min: %d, %d, %d, %d, %d\n", min1, min2, min3, min4, min5);
        assert min2 == min1;
        assert min3 == min1;
        assert min4 == min1;
        assert min5 == min1;

        List<Integer> evenNumbers = numberCollection.stream()
                .filter(n -> n % 2 == 0)
                .reduce(
                    new ArrayList<Integer>(),
                    (list, e) -> { list.add(e); return list; },
                    (l1, l2) -> { l1.addAll(l2); return l1; });
        System.out.printf("Even: %s\n", evenNumbers);

        List<Integer> oddNumbers = numberCollection.stream()
                .filter(n -> n % 2 == 1)
                .collect(Collector.of(
                    () -> new ArrayList<Integer>(),
                    (list, e) -> list.add(e),
                    (l1, l2) -> { l1.addAll(l2); return l1; }));
        System.out.printf("Odd: %s\n", evenNumbers);
    }


    private void grouping() {
        Location house = buildHouse();

        TestRun.run("Range Flats by number of Rooms", (name, out) -> {

            final Predicate<Location> isLivingRoom = (room) -> room != null
                    && room.getType() == Location.Type.ROOM
                    && room.getName().toLowerCase().startsWith("room");
            final Function<Location, Double> flatArea = (flat) -> flat.getChildren().stream()
                    .filter(room -> room.getType() == Location.Type.ROOM)
                    .reduce(0.0, (area, room) -> area + room.getArea(), Double::sum);

            // Group Flats by number of Rooms
            Map<Integer, List<Location>> flatsByNumberOfRooms = house
                    .getChildren().stream() // Floors
                    .flatMap(floor -> floor.getChildren().stream()) // Flats
                    .collect(Collectors.groupingBy(flat ->
                        (int)
                        flat.getChildren().stream()
                            .filter(isLivingRoom)
                            .count()
                    ));

            // Print Flats in groups
            out.out("Flats by number of separate living rooms:");
            flatsByNumberOfRooms.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(flats -> {
                        out.out("%d-room flats (%d):", flats.getKey(), flats.getValue().size());
                        flats.getValue().stream()
                            .sorted(Comparator.comparing(Location::getName))
                            .forEach(flat -> out.out("  %s, %s floor, %.1f m",
                                flat.getName(),
                                flat.getParent().getName(),
                                flatArea.apply(flat)));
                    });

            // Print each Flat with number of Rooms
            out.out("Number of separate living rooms:");
            flatsByNumberOfRooms.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .flatMap(entry ->
                        entry.getValue().stream()
                        .map(flat -> Pair.with(entry.getKey(), flat))
                    )
                    .forEach(entry -> {
                        out.out("%s, %s floor, %d rooms, %.1f m",
                                entry.getValue1().getName(),
                                entry.getValue1().getParent().getName(),
                                entry.getValue0(),
                                flatArea.apply(entry.getValue1()));
                    });
        });
    }


    private static Location buildHouse() {
        //noinspection UnnecessaryLocalVariable
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
                        .location(Location.Type.ROOM, "Hall", 16.0, 0)
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
                        .location(Location.Type.ROOM, "Hall", 16.0, 0)
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
