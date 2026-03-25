package com.bookmystay.uc11;

import java.util.*;

/**
 * Use Case 11: Concurrent Booking Simulation
 *
 * Simulates multiple guests booking concurrently with thread-safe inventory updates.
 *
 * Version: 11.0
 *
 * Author: YourName
 */

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingInventory {
    private Map<String, Integer> roomInventory;

    public BookingInventory() {
        roomInventory = new HashMap<>();
        roomInventory.put("Single Room", 2);
        roomInventory.put("Double Room", 2);
        roomInventory.put("Suite Room", 1);
    }

    // synchronized method for thread-safe allocation
    public synchronized boolean allocateRoom(String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized Map<String, Integer> getInventorySnapshot() {
        return new HashMap<>(roomInventory);
    }
}

class BookingTask implements Runnable {
    private Reservation reservation;
    private BookingInventory inventory;

    public BookingTask(Reservation reservation, BookingInventory inventory) {
        this.reservation = reservation;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        boolean success = inventory.allocateRoom(reservation.getRoomType());
        if (success) {
            System.out.println("Booking successful for " + reservation.getGuestName() +
                    " (" + reservation.getRoomType() + ")");
        } else {
            System.out.println("Booking failed for " + reservation.getGuestName() +
                    " (" + reservation.getRoomType() + "): No availability");
        }
    }
}

public class MyStay {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=====================================");
        System.out.println(" Book My Stay App - Concurrent Booking Simulation");
        System.out.println(" Version 11.0");
        System.out.println("=====================================");

        BookingInventory inventory = new BookingInventory();

        // Sample reservations
        Reservation r1 = new Reservation("RES201", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES202", "Bob", "Single Room");
        Reservation r3 = new Reservation("RES203", "Charlie", "Single Room"); // should fail
        Reservation r4 = new Reservation("RES204", "Diana", "Suite Room");

        List<Thread> threads = new ArrayList<>();
        threads.add(new Thread(new BookingTask(r1, inventory)));
        threads.add(new Thread(new BookingTask(r2, inventory)));
        threads.add(new Thread(new BookingTask(r3, inventory)));
        threads.add(new Thread(new BookingTask(r4, inventory)));

        // Start all threads simultaneously
        for (Thread t : threads) t.start();

        // Wait for all threads to finish
        for (Thread t : threads) t.join();

        System.out.println("Final Inventory: " + inventory.getInventorySnapshot());
    }
}