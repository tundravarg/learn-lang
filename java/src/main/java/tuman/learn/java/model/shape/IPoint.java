package tuman.learn.java.model.shape;


import java.util.Collections;
import java.util.List;


public interface IPoint extends IShape {

     double getX();
     double getY();


     @Override
     default List<IPoint> getPoints() {
          return Collections.singletonList(this);
     }

     @Override
     default IFrame getBoundingFrame() {
          return new Frame(getX(), getY(), 0.0, 0.0);
     }

}
