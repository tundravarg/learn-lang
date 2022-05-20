package tuman.learn.java;


import junit.framework.TestCase;
import org.junit.Assert;
import tuman.learn.java.util.AssertUtil;
import tuman.learn.java.utils.MiscUtils;

import java.time.Duration;


public class TestSleep extends TestCase {

    public void testDuration() {
        AssertUtil.assertDuration(123, 123, () -> MiscUtils.sleep(123));
        AssertUtil.assertDuration(123, 123, () -> MiscUtils.sleep(1.23));
        AssertUtil.assertDuration(123, 123, () -> MiscUtils.sleep(Duration.ofMillis(123)));
    }


    public void testReturn() {
        assertSleepResult(true, trySleep(100, null));
        assertSleepResult(false, trySleep(100, 50L));
        assertSleepResult(true, trySleep(100, 150L));
    }

    private Boolean trySleep(long duration, Long interruptAfter) {
        Boolean result[] = {null};
        Thread thread = new Thread(() -> {
            result[0] = MiscUtils.sleep(duration);
        });
        thread.start();

        if (interruptAfter != null) {
            MiscUtils.sleep(interruptAfter);
            thread.interrupt();
        }

        try {
            thread.join();
        } catch (InterruptedException ex) {}

        return result[0];
    }

    private void assertSleepResult(boolean expected, Boolean actual) {
        Assert.assertNotNull("No result", actual);
        Assert.assertEquals(String.format("Result must be %b. Actual %s", expected, actual), expected, actual);
    }


    public void testMeasure() {
        final long T = 123; // ms

        long callNothing = MiscUtils.measure(null);
        long callEmpty = MiscUtils.measure(() -> {});
        long callSleep = MiscUtils.measure(() -> MiscUtils.sleep(123));

        System.out.printf("Call nothing: %d ns\n", callNothing);
        System.out.printf("Call empty: %d ns\n", callEmpty);
        System.out.printf("Call sleep %d ms: %d ns\n", T, callSleep);

        AssertUtil.assertRange(T * 1000000, T * 1000000 + 100000, callSleep);
    }

}
