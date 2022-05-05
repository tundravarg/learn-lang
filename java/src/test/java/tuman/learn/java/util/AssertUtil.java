package tuman.learn.java.util;


import org.junit.Assert;

import java.util.function.Predicate;


public class AssertUtil {

    public interface Run {
        void run() throws Exception;
    }


    public static Predicate<Exception> checkClass(Class<? extends Exception> klass) {
        return klass::isInstance;
    }

    public static Predicate<Exception> checkClassAndMessage(Class<? extends Exception> klass, String message) {
        return (ex) ->
                klass.isInstance(ex)
                && (message == null && ex.getMessage() == null || message != null && message.equals(ex.getMessage()));
    }


    public static void assertThrow(Run run) {
        assertThrow(run, null);
    }

    public static void assertThrow(Run run, Predicate<Exception> check) {
        try {
            run.run();
        } catch (Exception ex) {
            if (check != null) {
                Assert.assertTrue(check.test(ex));
            }
            return;
        }
        Assert.fail("Expected exception");
    }

}
