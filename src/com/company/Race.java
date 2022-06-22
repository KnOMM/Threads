package com.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Race {

    public static AtomicLong startRaceTime = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        List<RaceCarRunnable> cars = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(3);
        cars.add(new RaceCarRunnable("car1",200,1000, latch));
        cars.add(new RaceCarRunnable("car2",400,1000, latch));
        cars.add(new RaceCarRunnable("car3",650,1000, latch));

        List<Thread>threads = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            threads.add(new Thread(cars.get(i)));
        }



        //new Thread(new RaceCarRunnable("car1",200,1000)).start();

        startRaceTime.set(System.currentTimeMillis());
        startRace(threads);
        latch.await();
        System.out.println(cars.stream().min(Comparator.comparing(RaceCarRunnable::getFinishTime)).get().getName()+" is the fastest");
        System.out.println(convertToTime(cars.stream().min(Comparator.comparing(RaceCarRunnable::getFinishTime)).get().getFinishTime())+" sec.");



    }

    public static void startRace(List<Thread>threads){
        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("3");
                try {
                    Thread.sleep(500);
                    System.out.println("2");
                    Thread.sleep(500);
                    System.out.println("1");
                    Thread.sleep(500);
                    System.out.println("GO!");


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for (Thread t : threads) {
            t.start();
        }
    }

    public static String convertToTime(long time){
        return new SimpleDateFormat("s,SSS").format(time);
    }
}
