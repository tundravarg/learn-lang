package tuman.learn.java.utils;


import java.io.OutputStream;


public class TestRun implements Runnable {

    public interface Run {
        void run(String name, Out out);
    }


    public static class Out {

        public synchronized void out(Object obj, Object... args) {
            if (obj instanceof String format && args.length > 0) {
                System.out.printf(format, args);
                System.out.println();
            } else {
                System.out.println(obj);
            }
        }

        public OutputStream getOutStream() {
            return System.out;
        }

    }


    private String name;
    private Run run;

    public TestRun(String name, Run run) {
        this.name = name;
        this.run = run;
    }

    public void run() {
        System.out.printf("---- BEGIN %s ----\n", name);
        run.run(name, new Out());
        System.out.printf("---- END %s ----\n", name);
    }


    public static void run(String name, Run run) {
        new TestRun(name, run).run();
    }

    public static void run(String name, Runnable run) {
        run(name, (n, o) -> run.run());
    }

}
