package com.company.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Ferry {
    private static final CyclicBarrier BARRIER = new CyclicBarrier(3, new FerryBoat());
    // Initializing the barrier with 3 threads and one task
    // that will be done after all threads are released
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 9; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(400);
        }
    }

    // The task that will occur after achieving the barrier
    public static class  FerryBoat implements Runnable{
        @Override
        public void run(){
            try {
                Thread.sleep(500);
                System.out.println("The ferry transported cars!");
            } catch (InterruptedException e){}
        }
    }

    public static class Car implements Runnable {
        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run(){
            try {
                System.out.printf("Car №%d came to the ferry crossing.\n", carNumber);
                // To let thread know that it has reached the barrier
                // we invoke method await() that will hold the thread
                // until barrier is released
                BARRIER.await();
                System.out.printf("Car №%d proceeded moving.\n", carNumber);
            } catch (BrokenBarrierException | InterruptedException e) {}
        }
    }
}
