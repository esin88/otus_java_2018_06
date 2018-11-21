package ru.otus.concurrent;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class DeadLockOrdering {

    class Account {
        Lock lock;

        void debit(Money amount) {
            // ...
        }

        void credit(Money amount) {
            // ...
        }
    }

    class Money {
    }

    public void transferMoney(Account from, Account to, Money amount) throws InterruptedException {

        while (true) {
            if (from.lock.tryLock()) {
                try {
                    if (to.lock.tryLock()) {
                        try {
                            from.credit(amount);
                            to.debit(amount);
                            return;
                        } finally {
                            to.lock.unlock();
                        }
                    }
                } finally {
                    from.lock.unlock();
                }
            }
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5));
        }
    }
}
