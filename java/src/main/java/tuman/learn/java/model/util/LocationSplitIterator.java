package tuman.learn.java.model.util;


import tuman.learn.java.model.Location;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class LocationSplitIterator implements Spliterator<Location> {

    private Location root;
    private int maxDepth;
    private Stack<Iterator<Location>> levels = new Stack<>();


    public LocationSplitIterator(Location root) {
        this(root, -1);
    }

    public LocationSplitIterator(Location root, int maxDepth) {
        this.root = root;
        this.maxDepth = maxDepth >= 0 ? maxDepth : Integer.MAX_VALUE;
        this.levels.push(Collections.singletonList(root).iterator());
    }


    @Override
    public boolean tryAdvance(Consumer<? super Location> consumer) {
        while (!levels.isEmpty() && !levels.peek().hasNext()) {
            levels.pop();
        }
        if (levels.isEmpty()) {
            return false;
        }
        Location current = levels.peek().next();
        consumer.accept(current);
        if (levels.size() <= maxDepth) {
            levels.push(current.getChildren().iterator());
        }
        return true;
    }

    @Override
    public Spliterator<Location> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return NONNULL;
    }


    public static LocationSplitIterator iterator(Location root) {
        return new LocationSplitIterator(root);
    }

    public static LocationSplitIterator iterator(Location root, int depth) {
        return new LocationSplitIterator(root, depth);
    }

    public static Stream<Location> stream(Location root) {
        return StreamSupport.stream(iterator(root), false);
    }

    public static Stream<Location> stream(Location root, int depth) {
        return StreamSupport.stream(iterator(root, depth), false);
    }

}
