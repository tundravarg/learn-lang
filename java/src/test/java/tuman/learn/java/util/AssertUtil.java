package tuman.learn.java.util;


import org.junit.Assert;

import java.util.function.Predicate;


public class AssertUtil {

    public interface Run {
        void run() throws Exception;
    }


    public static Predicate<Throwable> checkClass(Class<? extends Throwable> klass) {
        return klass::isInstance;
    }

    public static Predicate<Throwable> checkClassAndMessage(Class<? extends Throwable> klass, String message) {
        return (ex) ->
                klass.isInstance(ex)
                && (message == null && ex.getMessage() == null || message != null && message.equals(ex.getMessage()));
    }


    public static void assertThrow(Run run) {
        assertThrow(run, null);
    }

    public static void assertThrow(Run run, Predicate<Throwable> check) {
        try {
            run.run();
        } catch (Throwable ex) {
            if (check != null) {
                Assert.assertTrue(
                        String.format("Invalid exception: %s, '%s'", ex.getClass().getName(), ex.getMessage()),
                        check.test(ex));
            }
            return;
        }
        Assert.fail("Expected exception");
    }


    public static void assertDuration(long minMillis, long maxMillis, Runnable run) {
        long t0 = System.currentTimeMillis();
        run.run();
        long t1 = System.currentTimeMillis();
        long duration = t1 - t0;
        Assert.assertTrue(
                String.format("Duration must be in range [%d, %d] ms. Actual is %d ms", minMillis, maxMillis, duration),
                duration >= minMillis && duration <= maxMillis
                );
    }

    public static void assertDurationWithDelta(long duration, long delta, Runnable run) {
        delta = Math.abs(delta);
        assertDuration(duration - delta, duration + delta, run);
    }

    public static void assertDurationWithEps(long duration, double eps, Runnable run) {
        assertDurationWithDelta(duration, Math.round(duration * eps), run);
    }

}
