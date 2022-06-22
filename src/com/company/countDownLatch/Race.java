package com.company.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class Race {
    // Creating CountDownLatch for 8 conditions
    private static final CountDownLatch START = new CountDownLatch(8);
    // Length of the racing track
    private static final int trackLength = 500000;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            new Thread(new Car(i, (int) (Math.random() * 100 + 50))).start();
            Thread.sleep(1000);
        }
        while (START.getCount() > 3)
            Thread.sleep(100);
        Thread.sleep(1000);
        System.out.println("Ready!");
        START.countDown();
        Thread.sleep(1000);
        System.out.println("Steady!");
        START.countDown();
        Thread.sleep(1000);
        System.out.println("Go!");
        START.countDown();
    }

    public static class Car implements Runnable{
        private int carNumber;
        private int carSpeed;

        public Car(int carNumber, int carSpeed) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
        }

        @Override
        public void run() {
            try {
                System.out.printf("Car №%d came to the start line.\n", carNumber);
                // Car came to the start line - condition was done
                // decrementing the counter by 1
                START.countDown();
                // method await() blocks thread until CountDownLatch doesn't equal 0
                START.await();
                Thread.sleep(trackLength/carSpeed); // time to cross the finish line
                System.out.printf("Car №%d finished!\n", carNumber);
            } catch (InterruptedException e){

            }
        }
    }
}
