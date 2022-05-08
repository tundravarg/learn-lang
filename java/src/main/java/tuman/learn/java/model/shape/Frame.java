package tuman.learn.java.model.shape;


public class Frame extends Point implements IFrame {

    double width;
    double height;


    public Frame() {}

    public Frame(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public Frame(IPoint p, IPoint size) {
        this(p.getX(), p.getY(), size.getX(), size.getY());
    }

    public Frame(IFrame f) {
        this(f.getX(), f.getY(), f.getWidth(), f.getHeight());
    }


    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

}
