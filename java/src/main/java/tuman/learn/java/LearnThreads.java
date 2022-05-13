package tuman.learn.java;


import tuman.learn.java.utils.MiscUtils;
import tuman.learn.java.utils.TestRun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiFunction;
import java.util.stream.IntStream;


public class LearnThreads {

    public static void main(String[] args) {
        System.out.println("==== Learn Threads ====");
        LearnThreads learnThreads = new LearnThreads();

//        learnThreads.basicThreads();
//        learnThreads.restartThread();
        learnThreads.reuseThread();
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
