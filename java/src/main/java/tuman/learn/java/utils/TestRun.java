package tuman.learn.java.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;


public class TestRun implements Runnable {

    public interface Run {
        void run(String name, Out out);
    }


    public interface Out {
        void out(Object obj, Object... args);
    }

    public static abstract class AbstractOut implements Out {

        public void out(Object obj, Object... args) {
            doOut(decorate(format(obj, args)));
        }

        protected abstract void doOut(String message);

        protected String format(Object obj, Object... args) {
            if (obj instanceof String format && args.length > 0) {
                return String.format(format, args);
            } else {
                return obj != null ? obj.toString() : "null";
            }
        }

        protected String decorate(String str) {
            return str;
        }

    }

    public static class StdOut extends AbstractOut {

        @Override
        protected void doOut(String message) {
            System.out.println(message);
        }

    }

    public static class DecoratedOut extends AbstractOut {

        private Out out;
        private Function<String, String> decorate;

        public DecoratedOut(Out out, Function<String, String> decorate) {
            this.out = out;
            this.decorate = decorate;
        }

        public DecoratedOut(Out out, String prefix, String suffix) {
            this(out, (message) -> {
                message = prefix != null ? prefix + message : message;
                message = suffix != null ? suffix + message : message;
                return message;
            });
        }

        public static DecoratedOut decorating(Out out, Function<String, String> decorate) {
            return new DecoratedOut(out, decorate);
        }

        public static DecoratedOut wrapping(Out out, String prefix, String suffix) {
            return new DecoratedOut(out, prefix, suffix);
        }

        public static DecoratedOut formatting(Out out, String format) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat zoneFormat = new SimpleDateFormat("X");
            return new DecoratedOut(out, (message) -> {
                String result = format != null ? format : "%M";

                Date date = new Date();
                result = result.replace("%d", dateFormat.format(date));
                result = result.replace("%t", String.format("%s.%03d", timeFormat.format(date), MiscUtils.nanosInCurrentSecond() / 1000));
                result = result.replace("%z", zoneFormat.format(date));

                result = result.replace("%G", Thread.currentThread().getThreadGroup().getName());
                result = result.replace("%T", Thread.currentThread().getName());
                result = result.replace("%M", message != null ? message : "");

                return result;
            });
        }

        public static DecoratedOut withDateTimeAndThread(
                Out out,
                boolean withDate, boolean withTime, boolean withZone,
                boolean withThreadGroup, boolean withThread) {
            String format = "%M";

            if (withThread) format = "%T: " + format;
            if (withThreadGroup) format = "%G: " + format;

            String timeFormat = "";
            if (withZone) timeFormat = "%z " + timeFormat;
            if (withTime) timeFormat = "%t " + timeFormat;
            if (withDate) timeFormat = "%d " + timeFormat;
            if (timeFormat.length() > 0) format = timeFormat.substring(0, timeFormat.length() - 1) + ": " + format;

            return formatting(out, format);
        }

        public static DecoratedOut withDateTimeAndThread(Out out) {
            return withDateTimeAndThread(out, true, true, true, false, true);
        }

        public static DecoratedOut withTimeAndThread(Out out) {
            return withDateTimeAndThread(out, false, true, false, false, true);
        }

        @Override
        protected void doOut(String message) {
            if (out instanceof AbstractOut abstractOut) {
                abstractOut.doOut(message);
            } else {
                out.out(message);
            }
        }

        @Override
        protected String decorate(String str) {
            return decorate != null ? decorate.apply(str) : str;
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
        run.run(name, new StdOut());
        System.out.printf("---- END %s ----\n", name);
    }


    public static void run(String name, Run run) {
        new TestRun(name, run).run();
    }

    public static void run(String name, Runnable run) {
        run(name, (n, o) -> run.run());
    }

}
