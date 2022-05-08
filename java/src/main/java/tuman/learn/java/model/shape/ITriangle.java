package tuman.learn.java.model.shape;


import java.util.Arrays;
import java.util.List;


public interface ITriangle extends IShape {

    IPoint getP0();
    IPoint getP1();
    IPoint getP2();


    @Override
    default List<IPoint> getPoints() {
        return Arrays.asList(getP0(), getP1(), getP2());
    }

}
