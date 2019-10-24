package com.company;

import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private Semaphore semaphore;
    boolean isReady;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        isReady = false;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            semaphore.acquire();
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            CARS_COUNT--;
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (isReady){
            semaphore.release();
        }


            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

    }
}

