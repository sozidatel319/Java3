package com.company;

public class Main {

    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {

        Main main1 = new Main();

        Thread n1 = new Thread(() ->{
            main1.printA();
        });

        Thread n2 = new Thread(() ->{
            main1.printB();
        });

        Thread n3 = new Thread(() -> {
            main1.printC();
        });

        n1.start();
        n2.start();
        n3.start();

    }

    public void printA() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {

                    while (currentLetter != 'A') {
                        mon.wait();
                    }
                    System.out.print("A");
                    currentLetter = 'B';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'B') {
                        mon.wait();
                    }
                    System.out.print("B");
                    currentLetter = 'C';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'C') {
                        mon.wait();
                    }
                    System.out.print("C");
                    currentLetter = 'A';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
