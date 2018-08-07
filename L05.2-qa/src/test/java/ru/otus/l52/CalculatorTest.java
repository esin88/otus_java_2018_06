package ru.otus.l52;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author sergey
 * created on 06.08.18.
 */

@RunWith(Parameterized.class)
public class CalculatorTest {

    @Test
    public void add() {
        int x = 2;
        int y = 3;
        int result = new Calculator().add(x, y);
        assertEquals("проверка сложения:", x + y, result);
    }


    @Test(expected = ArithmeticException.class)
    public void divByZero() {
        int x = 2;
        int y = 0;
        new Calculator().div(x, y);
    }


    @Ignore("Test is ignored as a demonstration")
    @Test
    public void testVoid() {

    }


    @Test(timeout=10)
    @Ignore
    public void testLongCalculation() throws InterruptedException {
        new Calculator().longCalculation();
    }


    @BeforeClass
    public static void beforeClass() {
        System.out.println("before class");
    }


    @AfterClass
    public static void afterClass(){
        System.out.println("after class");
    }


    @Parameterized.Parameters(name = "{index}: set({0},{1})={2}")
    public static Collection<Object[]> dataForDiv() {
        return Arrays.asList(new Object[][] {
                { 4, 2, 2.0}, { 100, 10, 10.0}, { 40, 5, 8.0},
                { 3, 0, 0.0}, { 50, 5, 10.0}
        });
    }

    @Parameterized.Parameter(0)
    public int paramX;

    @Parameterized.Parameter(1)
    public int paramY;

    @Parameterized.Parameter(2)
    public double result;

    @Test
    public void test() {
        assertEquals("параметрический тест:", result, new Calculator().div(paramX, paramY), 0.1);
    }
}