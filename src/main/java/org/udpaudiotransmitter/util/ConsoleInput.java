package org.udpaudiotransmitter.util;

import java.io.InputStream;
import java.util.Scanner;

public class ConsoleInput {

    private Scanner scan;

    public ConsoleInput(InputStream source) {
        scan = new Scanner(source);
    }

    public int inputInt() throws NumberFormatException {
        int input = scan.nextInt();
        scan.nextLine(); // to ignore next line
        return input;
    }

    public Integer inputIntObject() throws NumberFormatException {
        int input = scan.nextInt();
        scan.nextLine(); // to ignore next line
        return input;
    }

    public float inputFloat() throws NumberFormatException {
        return Float.parseFloat(scan.next());
    }

    public Float inputFloatObject() throws NumberFormatException {
        return Float.parseFloat(scan.next());
    }

    public double inputDouble() throws NumberFormatException {
        return Double.parseDouble(scan.next());
    }

    public Double inputDoubleObject() throws NumberFormatException {
        return Double.parseDouble(scan.next());
    }

    public String getLine() {
        return scan.nextLine();
    }

    public String getWord() {
        return scan.next();
    }

}
