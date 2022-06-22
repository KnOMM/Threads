package com.company.exchanger;

import java.util.concurrent.Exchanger;

public class Delivery {
    // Creating exchanger that will exchange with type String
    private static final Exchanger<String> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) throws InterruptedException {
        String[] p1 = new String[]{"{parcel A -> D}", "{parcel A -> C}"};
        String[] p2 = new String[]{"{parcel B -> C}", "{parcel B -> D}"};
        new Thread(new Truck(1, "A", "D", p1)).start();
        Thread.sleep(100);
        new Thread(new Truck(2, "B", "C", p2)).start();
    }

    public static class Truck implements Runnable {
        private int number;
        private String dep;
        private String dest;
        private String[] parcels;

        public Truck(int number, String dep, String dest, String[] parcels) {
            this.number = number;
            this.dep = dep;
            this.dest = dest;
            this.parcels = parcels;
        }

        @Override
        public void run() {
            try {
                System.out.printf("Parcels %s and %s were loaded in the truck №%d.\n", parcels[0], parcels[1], number);
                System.out.printf("The truck №%d left the point %s and move to the point %s.\n", number, dep, dest);
                Thread.sleep(1000 + (long) Math.random() * 5000);
                System.out.printf("The truck №%d arrived in point E.\n", number);
                parcels[1] = EXCHANGER.exchange(parcels[1]);
                System.out.printf("The parcel for the point %s was loaded to the truck №%d.\n", dest, number);
                Thread.sleep(1000 + (long) Math.random() * 5000);
                System.out.printf("The truck №%d came to the point %s and delivered %s and %s.\n",number, dest,parcels[0],parcels[1]);
            } catch (Exception e) {}
        }
    }
}
