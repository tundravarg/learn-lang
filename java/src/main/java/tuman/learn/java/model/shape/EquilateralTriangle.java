package tuman.learn.java.model.shape;


public class EquilateralTriangle implements IEquilateralTriangle {

    private double side;


    public EquilateralTriangle() {}

    public EquilateralTriangle(double side) {
        this.side = side;
    }


    @Override
    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

}
