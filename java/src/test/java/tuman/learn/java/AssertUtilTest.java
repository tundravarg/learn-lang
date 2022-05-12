package tuman.learn.java;


import org.junit.Assert;
import org.junit.Test;
import tuman.learn.java.util.AssertUtil;
import tuman.learn.java.utils.MiscUtils;

import java.io.IOException;
import java.util.function.Predicate;


public class AssertUtilTest {

    @Test
    public void testAssertThrow() {
        testThrow(null, null, false);
        testThrow(new IllegalArgumentException("Asd"), AssertUtil.checkClass(RuntimeException.class), true);
        testThrow(new IllegalArgumentException("Asd"), AssertUtil.checkClass(IOException.class), false);
        testThrow(new IllegalArgumentException("Asd"), AssertUtil.checkClassAndMessage(RuntimeException.class, "Asd"), true);
        testThrow(new IllegalArgumentException("Asd"), AssertUtil.checkClassAndMessage(RuntimeException.class, "Qwe"), false);
        testThrow(new IllegalArgumentException("Asd"), AssertUtil.checkClassAndMessage(IOException.class, "Qwe"), false);
    }

    private static void testThrow(Exception ex, Predicate<Throwable> check, boolean pass) {
        try {
            AssertUtil.assertThrow(() -> { if (ex != null) throw ex; }, check);
        } catch (AssertionError e) {
            if (pass) {
                Assert.fail(getFailMessage("pass", e));
            }
            return;
        } catch (Exception e) {
            Assert.fail(getFailMessage(pass ? "pass" : "AssertionError", e));
            return;
        }
        if (!pass) {
            Assert.fail(getFailMessage("AssertionError", null));
        }
    }

    private static String getFailMessage(String expected, Throwable thrown) {
        if (thrown != null) {
            return String.format("Expected: %s; Actual: %s, %s", expected, thrown.getClass().getSimpleName(), thrown.getMessage());
        } else {
            return String.format("Expected: %s; Actual: pass", expected);
        }
    }


    @Test
    public void testAssertDuration() {
        final long D = 1; // Overhead duration

        AssertUtil.assertDuration(100, 100 + D, sleepingFunc(100));
        AssertUtil.assertDuration(90, 110, sleepingFunc(90));
        AssertUtil.assertDuration(90, 110, sleepingFunc(110 - D));
        AssertUtil.assertThrow(() -> {
            AssertUtil.assertDuration(90, 110, sleepingFunc(89));
        }, AssertUtil.checkClass(AssertionError.class));
        AssertUtil.assertThrow(() -> {
            AssertUtil.assertDuration(90, 110, sleepingFunc(111));
        }, AssertUtil.checkClass(AssertionError.class));

        AssertUtil.assertDurationWithDelta(100, 10, sleepingFunc(100));
        AssertUtil.assertDurationWithDelta(100, 10, sleepingFunc(90));
        AssertUtil.assertDurationWithDelta(100, 10, sleepingFunc(110 - D));
        AssertUtil.assertThrow(() -> {
            AssertUtil.assertDurationWithDelta(100, 10, sleepingFunc(89));
        }, AssertUtil.checkClass(AssertionError.class));
        AssertUtil.assertThrow(() -> {
            AssertUtil.assertDurationWithDelta(100, 10, sleepingFunc(111));
        }, AssertUtil.checkClass(AssertionError.class));

        AssertUtil.assertDurationWithEps(100, 0.1, sleepingFunc(100));
        AssertUtil.assertDurationWithEps(100, 0.1, sleepingFunc(90));
        AssertUtil.assertDurationWithEps(100, 0.1, sleepingFunc(110 - D));
        AssertUtil.assertThrow(() -> {
            AssertUtil.assertDurationWithEps(100, 0.1, sleepingFunc(89));
        }, AssertUtil.checkClass(AssertionError.class));
        AssertUtil.assertThrow(() -> {
            AssertUtil.assertDurationWithEps(100, 0.1, sleepingFunc(111));
        }, AssertUtil.checkClass(AssertionError.class));
    }

    private static Runnable sleepingFunc(long duration) {
        return () -> MiscUtils.sleep(duration);
    }

}
