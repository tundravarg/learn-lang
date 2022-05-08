package tuman.learn.java.model.shape;


public class Triangle implements ITriangle {

    private IPoint p0;
    private IPoint p1;
    private IPoint p2;


    @Override
    public IPoint getP0() {
        return p0;
    }

    public void setP0(IPoint p0) {
        this.p0 = p0;
    }

    @Override
    public IPoint getP1() {
        return p1;
    }

    public void setP1(IPoint p1) {
        this.p1 = p1;
    }

    @Override
    public IPoint getP2() {
        return p2;
    }

    public void setP2(IPoint p2) {
        this.p2 = p2;
    }

}
