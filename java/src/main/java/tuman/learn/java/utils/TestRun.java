package tuman.learn.java.utils;


public class TestRun implements Runnable {

    private String name;
    private Runnable run;

    public TestRun(String name, Runnable run) {
        this.name = name;
        this.run = run;
    }

    public void run() {
        System.out.printf("---- BEGIN %s ----\n", name);
        run.run();
        System.out.printf("---- END %s ----\n", name);
    }


    public static void run(String name, Runnable run) {
        new TestRun(name, run).run();
    }

}
