package tuman.learn.java.utils;


import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;


public class MiscUtils {

    public static boolean sleep(long millis) {
        try {
            Thread.sleep(millis);
            return true;
        } catch (InterruptedException ex) {
            return false;
        }
    }

    public static boolean sleep(double secs) {
        return sleep(Math.round(secs * 1e3));
    }

    public static boolean sleep(Duration duration) {
        return sleep(duration.toMillis());
    }


    public static boolean join(Collection<? extends Thread> threads) {
        try {
            for (Thread thread: threads) {
                thread.join();
            }
            return true;
        } catch (InterruptedException ex) {
            return false;
        }
    }

    public static boolean join(Thread... threads) {
        return join(Arrays.asList(threads));
    }

}
