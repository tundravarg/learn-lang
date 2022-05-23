package tuman.learn.java.utils;


public class Result<T, E extends Exception> {

    private T value;
    private E error;


    protected Result() {}


    public static <T> Result<T, ?> value(T value) {
        Result<T, ?> result = new Result<>();
        result.value = value;
        return result;
    }

    public static <E extends Exception> Result<?, E> error(E error) {
        Result<?, E> result = new Result<>();
        result.error = error;
        return result;
    }


    @Override
    public String toString() {
        return error != null ? "Error: " + error : "Value: " + value;
    }

    public T getValue() {
        return value;
    }

    public E getError() {
        return error;
    }

    public boolean isError() {
        return error != null;
    }

    public Object getValuerOrError() {
        return error != null ? error : value;
    }

    public T get() throws E {
        if (error != null) {
            throw error;
        }
        return value;
    }

}
