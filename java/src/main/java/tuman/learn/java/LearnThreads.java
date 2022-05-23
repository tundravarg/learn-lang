package tuman.learn.java;


import tuman.learn.java.utils.MiscUtils;
import tuman.learn.java.utils.TestRun;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class LearnThreads {

    public static void main(String[] args) {
        System.out.println("==== Learn Threads ====");
        LearnThreads learnThreads = new LearnThreads();

//        learnThreads.basicThreads();
//        learnThreads.restartThread();
//        learnThreads.reuseThread();
//        learnThreads.priorities();
//        learnThreads.deadLock();
        learnThreads.synchronizedAccess();
    }


    private void basicThreads() {
        final var NUMBER_OF_THREADS = 5;
        final var NUMBER_OF_ITERATIONS = 10;

        final Thread[] threads = new Thread[NUMBER_OF_THREADS];
        final List<Integer>[] result = new List[10];
        IntStream.range(0, result.length).forEach(i -> result[i] = new ArrayList<>());

        TestRun.run("Basic", (name, out) -> {

            out.out("Starting %d threads of %d iterations", NUMBER_OF_THREADS, NUMBER_OF_ITERATIONS);

            for (int ti = 0; ti < NUMBER_OF_THREADS; ti++) {
                final var threadId = ti;
                var threadName = String.format("TestThread-%2d", threadId);
                var thread = new Thread(() -> {
                    for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                        MiscUtils.sleep(100);
                        result[i].add(threadId);
                        out.out("%s: %d", Thread.currentThread().getName(), i);
                    }
                }, threadName);
                thread.start();
                threads[ti] = thread;
            }

            out.out("Threads have been stared");

            MiscUtils.join(threads);

            out.out("Threads have finished");

            Arrays.stream(result).forEach(out::out);
        });
    }

    private void restartThread() {
        TestRun.run("Restart Thread", (name, out) -> {
            final var thread = new Thread(() -> {
                out.out("Thread run");
            });

            out.out("Start 1");
            thread.start();
            MiscUtils.join(thread);
            out.out("Finish 1");

//            out.out("Start 2");
//            thread.start();           // Illegal
//            MiscUtils.join(thread);
//            out.out("Finish 2");
        });
    }

    private void reuseThread() {
        TestRun.run("Reuse Thread", (name, out) -> {

            final var thread = new MultiRunThread();
            thread.start();

            final BiFunction<String, Double, Runnable> task = (taskName, duration) -> () -> {
                out.out("Begin %s", taskName);
                MiscUtils.sleep(duration);
                out.out("End %s", taskName);
            };

            thread.addTask(task.apply("1", 1.0));
            thread.addTask(task.apply("2", 1.0));
            thread.addTask(task.apply("3", 1.0));
            thread.addTask(task.apply("4", 1.0));
            thread.addTask(task.apply("5", 1.0));

            MiscUtils.sleep(2.5);

            thread.finishNow(); // TODO
//            thread.finishAfterCurrentTask();
//            thread.finishAfterAllTasks();
            out.out("Finish requested");

            thread.addTask(task.apply("6", 1.0));
            thread.addTask(task.apply("7", 1.0));

            MiscUtils.join(thread);

        });
    }

    private void priorities() {
        TestRun.run("Thread Priorities", (name, out) -> {

            final var NUMBER_OF_THREADS = 5;
            final var NUMBER_OF_ITERATIONS = 1000;
            final int[] PRIORITIES = {5, 1, 5, 10, 5};

            final Map<Integer, Integer> stats = new HashMap<>();
            boolean[] finish = {false};
            List<Thread> threads = IntStream.range(0, NUMBER_OF_THREADS).boxed()
                    .map(ti -> {
                        Thread thread = new Thread(() -> {
                            l0: for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                                for (int j = 0; j < 1000000; j++) {
                                    if (finish[0]) break l0;
                                    Math.sin(i * j);
                                }
                                stats.compute(ti, (k, v) -> v != null ? v + 1 : 1);
                            }
                            finish[0] = true;
                        });
                        thread.setPriority(PRIORITIES[ti]);
                        return thread;
                    })
                    .collect(Collectors.toList());

            stats.clear();
            finish[0] = false;
            threads.forEach(Thread::start);
            out.out("Start");

            MiscUtils.join(threads);
            out.out("Finish");

            stats.entrySet().stream()
                    .sorted(Comparator.comparing((Map.Entry<Integer, Integer> e) -> e.getValue()).reversed())
                    .forEach(e -> out.out("%d (%d): %d", e.getKey(), PRIORITIES[e.getKey()], e.getValue()));

            // TODO No correlation betwee priority and scores. Why?

        });
    }

    private void deadLock() {
        String l1 = "Lock-1";
        String l2 = "Lock-2";

        TestRun.run("Dead Lock", (name, out) -> {
            final var fout = TestRun.DecoratedOut.withTimeAndThread(out);
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.execute(() -> {
                fout.out("Locking %s", l1);
                synchronized (l1) {
                    fout.out("%s locked", l1);

                    fout.out("Locking %s", l2);
                    synchronized (l2) { // <------------------------ DEAD LOCK HERE
                        fout.out("%s locked", l2);
                    }
                    fout.out("%s unlocked", l2);
                }
                fout.out("%s unlocked", l1);
            });
            executor.execute(() -> {
                fout.out("Locking %s", l2);
                synchronized (l2) {
                    fout.out("%s locked", l2);

                    fout.out("Locking %s", l1);
                    synchronized (l1) { // <------------------------ DEAD LOCK HERE
                        fout.out("%s locked", l1);
                    }
                    fout.out("%s unlocked", l1);
                }
                fout.out("%s unlocked", l2);
            });
            MiscUtils.shutdownAndAwaitTermination(executor);
        });
    }

    private void synchronizedAccess() {
        TestRun.Out fout = TestRun.DecoratedOut.withTimeAndThread(new TestRun.StdOut());
        ExecutorService executor = Executors.newCachedThreadPool();
        Synchronized obj = new Synchronized();

        TestRun.run("Call general and synchronized Methods", (name, out) -> {
            try {
                executor.invokeAll(Arrays.asList(
                    (Callable<?>)
                    () -> {
                        // Call synchronized method
                        obj.syncCall1(1.0);
                        return null;
                    },
                    () -> {
                        // General method calls while another synchronized one is in progress
                        obj.asyncCall(0.3);
                        obj.asyncCall(0.3);
                        // Synchronized call while another synchronized one is in progress
                        obj.syncCall2(0.3);
                        return null;
                    }
                ));
            } catch (InterruptedException ex) {}
        });

        TestRun.run("Call synchronized methods while object is in synchronized", (name, out) -> {
            try {
                executor.invokeAll(Arrays.asList(
                    (Callable<?>)
                    () -> {
                        fout.out("BEGIN");
                        MiscUtils.sleep(1.0);
                        synchronized (obj) {
                            fout.out("Sync: BEGIN");
                            MiscUtils.sleep(3.0);
                            fout.out("Sync: END");
                        }
                        MiscUtils.sleep(1.0);
                        fout.out("END");
                        return null;
                    },
                    () -> {
                        obj.syncCall1(0.3);
                        MiscUtils.sleep(0.1);
                        obj.syncCall1(0.3);
                        MiscUtils.sleep(0.1);
                        obj.syncCall1(0.3);
                        MiscUtils.sleep(0.1);

                        obj.syncCall1(0.3);
                        MiscUtils.sleep(0.1);
                        obj.syncCall1(0.3);
                        MiscUtils.sleep(0.1);
                        obj.syncCall1(0.3);

                        return null;
                    }
                ));
            } catch (InterruptedException ex) {}
        });

        TestRun.run("Static synchronized", (name, out) -> {
            try {
                executor.invokeAll(Arrays.asList(
                    (Callable<?>)
                    () -> {
                        fout.out("BEGIN");
                        MiscUtils.sleep(1.0);
                        synchronized (obj.getClass()) {
                            fout.out("Sync: BEGIN");
                            MiscUtils.sleep(3.0);
                            fout.out("Sync: END");
                        }
                        MiscUtils.sleep(1.0);
                        fout.out("END");
                        return null;
                    },
                    () -> {
                        // Class isn't synchronized yet
                        obj.asyncCall(0.3);
                        MiscUtils.sleep(0.1);
                        obj.syncCall1(0.3);
                        MiscUtils.sleep(0.1);
                        obj.staticSyncCall(0.3);
                        MiscUtils.sleep(0.1);

                        // Class is synchronized, but object isn't
                        obj.asyncCall(0.3);
                        MiscUtils.sleep(0.1);
                        obj.syncCall1(0.3);
                        MiscUtils.sleep(0.1);
                        obj.staticSyncCall(0.3);
                        MiscUtils.sleep(0.1);

                        return null;
                    }
                ));
            } catch (InterruptedException ex) {}
        });

        executor.shutdown();
    }

}



class Synchronized {

    private static final TestRun.Out out = TestRun.DecoratedOut.withDateTimeAndThread(new TestRun.StdOut());

    public void asyncCall(double duration) {
        out.out("asyncCall: BEGIN");
        MiscUtils.sleep(duration);
        out.out("asyncCall: END");
    }

    public synchronized void syncCall1(double duration) {
        out.out("syncCall1: BEGIN");
        MiscUtils.sleep(duration);
        out.out("syncCall1: END");
    }

    public synchronized void syncCall2(double duration) {
        out.out("syncCall1: BEGIN");
        MiscUtils.sleep(duration);
        out.out("syncCall1: END");
    }

    public static synchronized void staticSyncCall(double duration) {
        out.out("staticSyncCall1: BEGIN");
        MiscUtils.sleep(duration);
        out.out("staticSyncCall1: END");
    }

}



class MultiRunThread extends Thread {

    private enum State {
        READY,
        RUN,
        FINISH_NOW,
        FINISH_AFTER_CURRENT_TASK,
        FINISH_AFTER_ALL_TASKS,
        FINISHED
    }

    private static final long PAUSE_IF_NO_TASKS = 1000;

    private Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private State state = State.READY;


    @Override
    public void run() {
        state = State.RUN;
        loop: while (true) {
            if (isInterrupted()) {
                break loop;
            }
            switch (state) {
                case READY -> {
                    throw new IllegalStateException("Thread isn't started");
                }
                case FINISHED, FINISH_NOW, FINISH_AFTER_CURRENT_TASK -> {
                    break loop;
                }
                case FINISH_AFTER_ALL_TASKS -> {
                    if (tasks.isEmpty()) {
                        break loop;
                    }
                }
            }

            Runnable task = tasks.poll();
            if (task == null) {
                MiscUtils.sleep(PAUSE_IF_NO_TASKS);
                continue loop;
            }

            try {
                task.run();
            } catch (Exception ex) {
                System.err.printf("Task failed: %s: %s\n", ex.getClass().getSimpleName(), ex.getMessage());
            }
        }
        state = State.FINISHED;
    }


    public void addTask(Runnable task) {
        if (isReadyForNewTasks()) {
            this.tasks.add(task);
        } else {
            throw new IllegalStateException("Illegal state: " + state);
        }
    }


    public void finishNow() {
        if (isInRunState()) {
            this.interrupt();
            state = State.FINISH_NOW;
        } else {
            throw new IllegalStateException("Illegal state: " + state);
        }
    }

    public void finishAfterCurrentTask() {
        if (isInRunState()) {
            state = State.FINISH_AFTER_CURRENT_TASK;
        } else {
            throw new IllegalStateException("Illegal state: " + state);
        }
    }

    public void finishAfterAllTasks() {
        if (isInRunState()) {
            state = State.FINISH_AFTER_ALL_TASKS;
        } else {
            throw new IllegalStateException("Illegal state: " + state);
        }
    }


    private boolean isInRunState() {
        if (isInterrupted()) {
            return false;
        }
        switch (state) {
            case READY, RUN, FINISH_NOW, FINISH_AFTER_CURRENT_TASK, FINISH_AFTER_ALL_TASKS -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private boolean isReadyForNewTasks() {
        if (isInterrupted()) {
            return false;
        }
        switch (state) {
            case READY, RUN, FINISH_AFTER_ALL_TASKS -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

}
