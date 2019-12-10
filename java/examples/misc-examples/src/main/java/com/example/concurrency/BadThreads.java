package com.example.concurrency;


/*

From: https://docs.oracle.com/javase/tutorial/essential/concurrency/QandE/questions.html

public class BadThreads {

    static String message;

    private static class CorrectorThread
        extends Thread {

        public void run() {
            try {
                sleep(1000);
            } catch (InterruptedException e) {}
            // Key statement 1:
            message = "Mares do eat oats.";
        }
    }

    public static void main(String args[])
        throws InterruptedException {

        (new CorrectorThread()).start();
        message = "Mares do not eat oats.";
        Thread.sleep(2000);
        // Key statement 2:
        System.out.println(message);
    }
}

Question:
The application should print out "Mares do eat oats." Is it guaranteed to always do this?
If not, why not? Would it help to change the parameters of the two invocations of Sleep?
How would you guarantee that all changes to message will be visible in the main thread?
 */

public class BadThreads {

    static String message;

    private static class CorrectorThread
            extends Thread {

        public void run() {
            try {
                sleep(1000);
            } catch (InterruptedException e) {}
            // Key statement 1:
            message = "Mares do eat oats.";
        }
    }

    public static void main(String args[])
            throws InterruptedException {

        CorrectorThread c = new CorrectorThread();
        c.start();
        /* Adding a join() here ensures that the messages are always printed in the same
        order. However, I think the question was really more about how to ensure all changes
        from the other thread are visible to each thread before it changes the value itself.

        To achieve that you can
            a) encapsulate `message` in it's own class and change it only through synchronised methods, or
            b) declare message to be volatile.
         */
        c.join();
        Thread.sleep(1000);
        message = "Mares do NOT eat oats.";
        // Key statement 2:
        System.out.println(message);
    }
}
