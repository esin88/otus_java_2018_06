package ru.otus.l52;

/**
 * @author sergey
 * created on 06.08.18.
 */
public class VisualVMdemo {
    public static void main(String[] args) throws InterruptedException {
        new VisualVMdemo().inc();
    }

    private long counter = 0;

    private void inc() throws InterruptedException {
        while (true) {
            counter++;
            System.out.println("counter:" + counter);
            Thread.sleep(10_000);
        }
    }
}
