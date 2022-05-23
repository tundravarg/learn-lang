package tuman.learn.java.utils;


import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class MiscUtils {

    private static final long NANOS_CORRECTION;
    static {
        long millis = System.currentTimeMillis();
        long nanos = System.nanoTime();
        NANOS_CORRECTION = (millis - nanos / 1000000) * 1000000;
    }


    public static long nanosInCurrentSecond() {
        long nanos  = System.nanoTime() + NANOS_CORRECTION;
        return nanos - nanos / 1000000000 * 1000000000;
    }


    /**
     * Measure time of `run` execution
     * @param run Function
     * @return Time in nanoseconds
     */
    public static long measure(Runnable run) {
        long t0 = System.nanoTime();
        if (run != null) {
            run.run();
        }
        long t1 = System.nanoTime();
        return t1 - t0;
    }


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


    public static boolean awaitTermination(ExecutorService executor, long timeout, TimeUnit timeUnit) {
        try {
            return executor.awaitTermination(timeout, timeUnit);
        } catch (InterruptedException ex) {
            return false;
        }
    }

    public static boolean awaitTermination(ExecutorService executor) {
        return awaitTermination(executor, 365, TimeUnit.DAYS);
    }

    public static boolean shutdownAndAwaitTermination(ExecutorService executor, boolean now, long timeout, TimeUnit timeUnit) {
        if (now) {
            executor.shutdownNow();
        } else {
            executor.shutdown();
        }
        return awaitTermination(executor, timeout, timeUnit);
    }

    public static boolean shutdownAndAwaitTermination(ExecutorService executor) {
        return shutdownAndAwaitTermination(executor, false, 365, TimeUnit.DAYS);
    }

    public static boolean shutdownNowAndAwaitTermination(ExecutorService executor) {
        return shutdownAndAwaitTermination(executor, true, 365, TimeUnit.DAYS);
    }


    public static <T, E extends Exception> Result<T, E> getResult(Future<T> future) {
        try {
            return (Result<T, E>)Result.value(future.get());
        } catch (Exception ex) {
            return (Result<T, E>)Result.error(ex);
        }
    }

    public static <T, E extends Exception> Result<T, E> getResult(Future<T> future, long timeout, TimeUnit timeUnit) {
        try {
            return (Result<T, E>)Result.value(future.get(timeout, timeUnit));
        } catch (Exception ex) {
            return (Result<T, E>)Result.error(ex);
        }
    }

}
