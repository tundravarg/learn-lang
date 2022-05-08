package tuman.learn.java.model.shape;


import java.util.Arrays;
import java.util.List;


public interface IFrame extends IPoint {

    double getWidth();
    double getHeight();


    default IPoint getSize() {
        return new Point(getWidth(), getHeight());
    }

    @Override
    default List<IPoint> getPoints() {
        return Arrays.asList(
            new Point(getX(), getY()),
            new Point(getX() + getWidth(), getY()),
            new Point(getX() + getWidth(), getY() + getHeight()),
            new Point(getX(), getY() + getHeight())
        );
    }

    @Override
    default IFrame getBoundingFrame() {
        return this;
    }

}
