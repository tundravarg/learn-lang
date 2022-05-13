package tuman.learn.java;


import tuman.learn.java.utils.MiscUtils;
import tuman.learn.java.utils.TestRun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


public class LearnThreads {

    public static void main(String[] args) {
        System.out.println("==== Learn Threads ====");
        LearnThreads learnThreads = new LearnThreads();

//        learnThreads.basicThreads();
        learnThreads.restartThread();
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
        TestRun.run("Reuse Thread", (name, out) -> {
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

}

class MyThread extends Thread {}
