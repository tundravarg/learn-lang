package tuman.learn.java.utils;


public class ObjectHolder <T> {

    private T object;


    public ObjectHolder() {}

    public ObjectHolder(T object) {
        this.object = object;
    }


    @Override
    public String toString() {
        return object != null ? object.toString() : null;
    }

    public static <T> ObjectHolder<T> of(T object) {
        return new ObjectHolder<>(object);
    }


    public boolean equals(Object object1) {
        if (this == object1) return true;
        if (object1 == null || getClass() != object1.getClass()) return false;
        if (!super.equals(object1)) return false;
        ObjectHolder<?> that = (ObjectHolder<?>) object1;
        return java.util.Objects.equals(object, that.object);
    }

    public int hashCode() {
        return java.util.Objects.hashCode(object);
    }


    public T get() {
        return object;
    }

    public void set(T object) {
        this.object = object;
    }

    public ObjectHolder<T> with(T object) {
        this.object = object;
        return this;
    }

}
