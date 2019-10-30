package com.company;

public class SuperTests {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Prepare to testing...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 0)
    public void testNumber1() {
        System.out.println("Начало теста №1");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }

    @Test(priority = 1)
    public void testNumber2() {
        System.out.println("Начало теста №2");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("Finishing all tests...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

