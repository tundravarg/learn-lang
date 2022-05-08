package tuman.learn.java.model.shape;


import java.util.List;


public interface IShape {

    default String getType() {
        return getClass().getSimpleName();
    }


    List<IPoint> getPoints();


    default IFrame getBoundingFrame() {
        List<IPoint> points = getPoints();

        if (points.isEmpty()) return new Frame();

        double minX =  Double.MAX_VALUE;
        double minY =  Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;
        for (IPoint p: points) {
            double x = p.getX();
            double y = p.getY();
            if (x < minX) minX = x;
            if (y < minY) minY = y;
            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }

        return new Frame(minX, minY, (maxX - minX), (maxY - minY));
    }

}
