package com.company;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class RaceCarRunnable extends Car implements Runnable {
    private int passed;
    private long distance;
    private boolean isFinished;
    private CountDownLatch latch;
    private long finishTime;

    public RaceCarRunnable(String name, int maxSpeed, long distance, CountDownLatch latch) {
        super(name, maxSpeed);
        this.distance = distance;
        this.latch = latch;
    }

    @Override
    public void run() {

        while (!isFinished) {
            try {
                Thread.sleep(1000);
                passed = passed + getRandomSpeed(this);
                if (passed >= distance) {
                    latch.countDown();
                    isFinished = true;
                    finishTime = System.currentTimeMillis() - Race.startRaceTime.get();
                    latch.await();
                    System.out.println(getName() + " finished, time - " + finishTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private int getRandomSpeed(Car car) {
        int speed = new Random().nextInt(car.getMaxSpeed() / 2) + car.getMaxSpeed() / 2;
        System.out.println("carName - " + car.getName() +
                ", currentSpeed - " + speed +
                ", progress: " + passed + "/" +
                distance);
        return speed;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}
