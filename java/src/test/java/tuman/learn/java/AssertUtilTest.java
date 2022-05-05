package tuman.learn.java;


import org.junit.Assert;
import org.junit.Test;
import tuman.learn.java.util.AssertUtil;

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

    private static void testThrow(Exception ex, Predicate<Exception> check, boolean pass) {
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

}
