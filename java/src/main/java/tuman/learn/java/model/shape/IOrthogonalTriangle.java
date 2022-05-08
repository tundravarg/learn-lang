package tuman.learn.java.model.shape;


public interface IOrthogonalTriangle extends ITriangle {

    double getWidth();
    double getHeight();


    @Override
    default IPoint getP0() {
        return new Point(0.0, 0.0);
    }

    @Override
    default IPoint getP1() {
        return new Point(getWidth(), 0.0);
    }

    @Override
    default IPoint getP2() {
        return new Point(0.0, getHeight());
    }

}
