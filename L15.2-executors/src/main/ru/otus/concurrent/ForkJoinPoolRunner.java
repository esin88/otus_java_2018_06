package ru.otus.concurrent;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolRunner {

    public static void main(String[] args) {
        ForkJoinPoolRunner runner = new ForkJoinPoolRunner();
        runner.runRecursiveAction();
        runner.runRecursiveTask();
    }

    public void runRecursiveTask() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        int[] a = {1, 2, 3, 4, 5, 6};

        System.out.println("array: \n" + Arrays.toString(a));
        ArrayCounter incrementTask = new ArrayCounter(a, 0, a.length);
        Integer result = forkJoinPool.invoke(incrementTask);
        System.out.println("Result: " + result);
    }

    public void runRecursiveAction() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        int[] a = new Random().ints(10, 0, 100)
                              .toArray();

        System.out.println("before: \n" + Arrays.toString(a));
        IncrementTask incrementTask = new IncrementTask(a, 0, a.length);
        forkJoinPool.invoke(incrementTask);
        System.out.println("after: \n" + Arrays.toString(a));

    }

    class IncrementTask extends RecursiveAction {
        final static int THRESHOLD = 2;
        final int[] array;
        final int start, end;

        IncrementTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start < THRESHOLD) {
                for (int i = start; i < end; ++i)
                    array[i]++;
            } else {
                int mid = (start + end) / 2;
                invokeAll(new IncrementTask(array, start, mid),
                        new IncrementTask(array, mid, end));
            }
        }
    }

    class ArrayCounter extends RecursiveTask<Integer> {
        final static int THRESHOLD = 2;
        int[] array;
        int start;
        int end;

        public ArrayCounter(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start < THRESHOLD) {
                return computeDirectly();
            } else {
                int mid = (end + start) / 2;

                ArrayCounter subTask1 = new ArrayCounter(array, start, mid);
                ArrayCounter subTask2 = new ArrayCounter(array, mid, end);

                invokeAll(subTask1, subTask2);
                return subTask1.join() + subTask2.join();
            }
        }

        protected Integer computeDirectly() {
            int count = 0;

            for (int i = start; i < end; i++) {
                if (array[i] % 2 == 0) {
                    count++;
                }
            }

            return count;
        }
    }
}
