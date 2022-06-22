package com.company.semaphore;

import java.util.concurrent.Semaphore;

public class Parking {
    // Parking place occupied - true, is free - false
    private static final boolean[] PARKING_PLACE = new boolean[5];
    // Set the "fair", in that case
    // acquire() will give permits in order
    private static final Semaphore SEMAPHORE = new Semaphore(5, true);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 7; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(400);
        }
    }

    public static class Car implements Runnable {
        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            System.out.printf("Car №%d came to the parking lot.\n", carNumber);
            try {
                SEMAPHORE.acquire();

                int parkingNumber = -1;
                // Finding free place and parking
                synchronized (PARKING_PLACE) {
                    for (int i = 0; i < 5; i++)
                        if (!PARKING_PLACE[i]) { // if parking lot is free
                            PARKING_PLACE[i] = true; // occupying that lot and making it true
                            parkingNumber = i; // remembering lot that we have occupied
                            System.out.printf("Car №%d parked to the parking lot %d.\n", carNumber, i);
                            break;
                        }
                    }
                    Thread.sleep(5000);

                    synchronized (PARKING_PLACE) {
                        PARKING_PLACE[parkingNumber] = false;
                    }

                    SEMAPHORE.release();
                    System.out.printf("Car №%d left parking lot.\n", carNumber);
                } catch (InterruptedException e) {

            }
        }
    }
}
