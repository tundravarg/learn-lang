package tuman.learn.java;


import tuman.learn.java.utils.MiscUtils;
import tuman.learn.java.utils.TestRun;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LearnMySemaphore {

    public static void main(String[] args) {
        System.out.println("==== Learn My Semaphore ====");
        LearnMySemaphore learnMySemaphore = new LearnMySemaphore();

//        learnMySemaphore.learnMySemaphore();
        learnMySemaphore.learnMyRwSemaphore();
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

    private void learnMyRwSemaphore() {
        TestRun.Out fout = TestRun.DecoratedOut.withTimeAndThread(new TestRun.StdOut());
        MyRwSemaphore semaphore = new MyRwSemaphore();

        class Task implements Runnable {
            private String name;
            private boolean writer;
            private double duration;

            public Task(String name, boolean writer, double duration) {
                this.name = name;
                this.writer = writer;
                this.duration = duration;
            }

            @Override
            public void run() {
                String action = writer ? "WRITE" : "READ";
                fout.out("%s: Locking %s...", name, action);
                if (writer) {
                    semaphore.lockWrite();
                } else {
                    semaphore.lockRead();
                }
                fout.out(semaphore);
                fout.out("%s: Begin %s", name, action);
                MiscUtils.sleep(duration);
                fout.out("%s: End %s", name, action);
                semaphore.unlock();
                fout.out(semaphore);
                fout.out("%s: Unlocked %s", name, action);
            }
        }

        for (int i = 0; i < 1; i++) {
            TestRun.run("My Semaphore " + i, (name, out) -> {
                final double DURATION = 3.0;
                final double PAUSE = 2.0;
                List<Task> tasks = Arrays.asList(
                        new Task("Writer-0", true,  DURATION),
                        new Task("Reader-0", false, DURATION),
                        new Task("Reader-1", false, DURATION),
                        new Task("Reader-2", false, DURATION),
                        new Task("Reader-3", false, DURATION),
                        new Task("Reader-4", false, DURATION),
                        new Task("Writer-1", true,  DURATION),
                        new Task("Reader-5", false, DURATION),
                        new Task("Reader-6", false, DURATION),
                        new Task("Writer-2", true,  DURATION),
                        new Task("Reader-7", false, DURATION),
                        new Task("Reader-8", false, DURATION),
                        new Task("Reader-9", false, DURATION)
                );
//            Collections.shuffle(tasks);

                ExecutorService executor = Executors.newCachedThreadPool();
                for (Task task : tasks) {
                    executor.execute(task);
                    MiscUtils.sleep(PAUSE);
                }
                MiscUtils.shutdownAndAwaitTermination(executor);
            });
        }
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



class MyRwSemaphore {

    private Set<Thread> readLockers = new HashSet<>();
    private Thread writeLocker = null;
    private int numberOfWriteLockers = 0;


    public void lockRead() {
        checkLockedByCurrentThread(false);
        while (writeLocker != null || numberOfWriteLockers > 0) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    return;
                }
            }
        }
        // Synchronized block is necessary here to prevent access collision
        synchronized (readLockers) {
            readLockers.add(Thread.currentThread());
        }
    }

    public void lockWrite() {
        checkLockedByCurrentThread(false);
        numberOfWriteLockers++;
        while (writeLocker != null || !readLockers.isEmpty()) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    return;
                }
            }
        }
        writeLocker = Thread.currentThread();
    }

    public void unlock() {
        checkLockedByCurrentThread(true);
        if (writeLocker == Thread.currentThread()) {
            writeLocker = null;
            numberOfWriteLockers--;
        } else {
            // Synchronized block is necessary here to prevent access collision
            synchronized (readLockers) {
                readLockers.remove(Thread.currentThread());
            }
        }
        synchronized (this) {
            // `notifyAll`, not `notify`, because we must awake all readers
            notifyAll();
        }
    }

    private void checkLockedByCurrentThread(boolean mustBeLocked) {
        Thread thread = Thread.currentThread();
        boolean lockedByCurrentThread = writeLocker == thread || readLockers.contains(thread);
        if (mustBeLocked && !lockedByCurrentThread) {
            throw new IllegalStateException("Semaphore has not been locked by this thread: " + thread.getName());
        } else if (!mustBeLocked && lockedByCurrentThread) {
            throw new IllegalStateException("Semaphore has already been locked by this thread: " + thread.getName());
        }
    }

    @Override
    public String toString() {
        return String.format("Semaphore: r:%d w:%d", readLockers.size(), writeLocker != null ? 1 : 0);
    }
}
