package tuman.learn.java;


import tuman.learn.java.utils.MiscUtils;
import tuman.learn.java.utils.TestRun;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LearnMySemaphore {

    public static void main(String[] args) {
        System.out.println("==== Learn My Semaphore ====");
        LearnMySemaphore learnMySemaphore = new LearnMySemaphore();
        learnMySemaphore.learnMySemaphore();
    }

    private void learnMySemaphore() {
        ExecutorService executor = Executors.newCachedThreadPool();
        TestRun.Out fout = TestRun.DecoratedOut.withTimeAndThread(new TestRun.StdOut());
        MySemaphore semaphore = new MySemaphore();

        TestRun.run("My Semaphore", (name, out) -> {
            executor.execute(() -> {
                fout.out("Locking semaphore...");
                semaphore.lock();
//                semaphore.lock(); // ILLEGAL: semaphore has already been locked by this thread
                fout.out("Locked semaphore...");
                MiscUtils.sleep(3.0);
                fout.out("Unlocking semaphore...");
                semaphore.unlock();
                fout.out("Unlocked semaphore...");
            });
            executor.execute(() -> {
                MiscUtils.sleep(0.25);
                fout.out("Locking semaphore...");
//                semaphore.unlock(); // ILLEGAL: semaphore has not been locked by this thread
                semaphore.lock();
                fout.out("Locked semaphore...");
                MiscUtils.sleep(3.0);
                fout.out("Unlocking semaphore...");
                semaphore.unlock();
                fout.out("Unlocked semaphore...");
            });
        });

        executor.shutdown();
    }

}



class MySemaphore {

    private Thread lockingThread;

    public void lock() {
        Thread thread = Thread.currentThread();
        if (lockingThread == thread) {
            throw new IllegalStateException("Semaphore has already been locked by this thread: " + thread.getName());
        }
        while (lockingThread != null) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException ex) {
                return;
            }
        }
        lockingThread = thread;
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        if (lockingThread != thread) {
            throw new IllegalStateException("Semaphore has not been locked by this thread: " + thread.getName());
        }
        lockingThread = null;
        synchronized (this) {
            notify();
        }
    }

}
