package ru.otus.l52;

/**
 * @author sergey
 * created on 06.08.18.
 */
public class Calculator {

    public int add(int x, int y) {
        return x + y;
    }

    public double div(int x, int y) {
        return x / y;
    }

    public void longCalculation() throws InterruptedException {
        Thread.sleep(100_000_000);
    }

    /*
     * Задание для TDD:
     * Реализовать функцию сложения двух положительных чисел.
     * Функция должна:
     * 1. Принимать в качестве аргументов только положительные числа.
     * 2. Возвращать сумму переданных чисел.
     */
    public int addPositiv(int x, int y) {
        return 0;
    }
}
