package tuman.learn.java.model.shape;


public class Point implements IPoint {

    private double x;
    private double y;


    public Point() {}

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(IPoint p) {
        this.x = p.getX();
        this.y = p.getY();
    }


    @Override
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
