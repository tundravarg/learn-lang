package tuman.learn.java.model.util;


import tuman.learn.java.model.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;


public class LocationPrinter {

    private Writer writer;
    private String indent = "  ";


    public LocationPrinter(OutputStream out) throws IOException {
        writer = new OutputStreamWriter(out);
    }

    public LocationPrinter(Writer out) {
        this.writer = out;
    }


    public void print(Location location) throws IOException {
        print(location, "");
        writer.flush();
    }

    private void print(Location location, String indent) throws IOException {
        writer.write(String.format("%s%s #%s '%s', parent: %s, area: %s, volume: %s\n",
                indent,
                location.getType().getLabel(), location.getId(), location.getName(),
                location.getParent() != null ? "#" + location.getParent().getId() : null,
                location.getArea(), location.getVolume()));
        String nextIndent = indent + this.indent;
        for (Location child: location.getChildren()) {
            print(child, nextIndent);
        }
    }


    public static String toString(Location location) {
        try {
            StringWriter out = new StringWriter();
            LocationPrinter printer = new LocationPrinter(out);
            printer.print(location);
            out.getBuffer().setLength(out.getBuffer().length() - 1);
            return out.toString();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static void print(Location location, OutputStream out) {
        try {
            LocationPrinter printer = new LocationPrinter(out);
            printer.print(location);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static void print(Location location, Writer out) {
        try {
            LocationPrinter printer = new LocationPrinter(out);
            printer.print(location);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static void stdOut(Location location) {
        print(location, System.out);
    }

}
