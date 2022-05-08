package tuman.learn.java.model.shape;


public interface IEquilateralTriangle extends IIsoscelesTriangle {

    double getSide();


    @Override
    default double getWidth() {
        return getSide();
    }

    @Override
    default double getHeight() {
        double s = getSide();
        return Math.sqrt(s * s * 0.75);
    }

}
