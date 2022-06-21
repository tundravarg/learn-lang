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
                fout.out("Locked semaphore...");
                MiscUtils.sleep(3.0);
                fout.out("Unlocking semaphore...");
                semaphore.unlock();
                fout.out("Unlocked semaphore...");
            });
            executor.execute(() -> {
                MiscUtils.sleep(0.25);
                fout.out("Locking semaphore...");
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

    boolean locked;

    public void lock() {
        while (locked) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException ex) {
                return;
            }
        }
        locked = true;
    }

    public void unlock() {
        locked = false;
        synchronized (this) {
            notify();
        }
    }

}
