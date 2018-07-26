package ru.otus.l51;

import org.junit.runner.JUnitCore;

public class TestRunner {
    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        junit.run(AnnotationsTest.class);
    }
}
