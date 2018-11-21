package ru.otus.concurrent;

public class DeadLock {

    class Account {
        void debit(Money amount) {
            // ...
        }

        void credit(Money amount) {
            // ...
        }
    }

    class Money {
    }

    public void transferMoney(Account from, Account to, Money amount) {

        synchronized (from) {
            // ...
            synchronized (to) {
                from.credit(amount);
                to.debit(amount);
            }
        }
    }

    // A: transferMoney(myAccount, yourAccount, 10);
    // B: transferMoney(yourAccount, myAccount, 20);

}
