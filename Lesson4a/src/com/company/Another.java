package com.company;

public class Another {
    private volatile char currentLetter = 'A';

    public static void main(String[] args) throws InterruptedException {

        Another another = new Another();

        Thread n1 = new Thread(() -> {
            another.printA();
        });

        Thread n2 = new Thread(() -> {
            another.printB();
        });

        Thread n3 = new Thread(() -> {
            another.printC();
        });

        n1.start();
        n2.start();
        n3.start();
    }


    private void printA() {

        for (int i = 0; i < 5; i++) {
            while (currentLetter != 'A') {
            }
            System.out.print("A");
            currentLetter = 'B';
        }
    }


    private void printB() {
        for (int i = 0; i < 5; i++) {
            while (currentLetter != 'B') {
            }
            System.out.print("B");
            currentLetter = 'C';
        }
    }


    private void printC() {
        for (int i = 0; i < 5; i++) {
            while (currentLetter != 'C') {
            }
            System.out.print("C");
            currentLetter = 'A';
        }
    }
}

