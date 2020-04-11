package DeadLockDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * create by park.huang@zkteco.com 2020-04-11 16:08
 **/
public class DeadLockDemo {
    public static List<Integer> listA = new ArrayList<Integer>();
    public static List<Integer> listB = new ArrayList<Integer>();

    public void start() {
        new Thread(new ThreadA()).start();
        new Thread(new ThreadB()).start();
    }

    static class ThreadA implements Runnable {
        public void run() {
            // get the lock of list A
            synchronized (listA) {
                try {
                    System.out.println(Thread.currentThread().getName() + " operating list A");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " ask lock of B");
                    synchronized (listB) {
                        System.out.println(Thread.currentThread().getName() + " operating list B");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ThreadB implements Runnable {
        public void run() {
            synchronized (listB) {
                System.out.println(Thread.currentThread().getName() + " operating list B");
                System.out.println(Thread.currentThread().getName() + " ask lock of A");
                synchronized (listA) {
                    System.out.println(Thread.currentThread().getName() + " operating list A");
                }
            }
        }
    }

    public static void main(String[] args) {
        new DeadLockDemo().start();
    }
}
