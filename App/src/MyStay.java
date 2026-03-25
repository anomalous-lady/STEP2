package com.bookmystay.uc5;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates fair booking request handling using a queue.
 *
 * Version: 5.0
 *
 * Author: YourName
 */

// Reservation class representing a guest's request
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Room Requested: " + roomType);
    }
}

// Booking Request Queue class
class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request to the queue
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Process requests (just display for now)
    public void processRequests() {
        System.out.println("\nProcessing booking requests in FIFO order:");
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            r.display();
        }
    }

    public int pendingRequests() {
        return queue.size();
    }
}

// Main class
public class MyStay {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println(" Book My Stay App - Booking Requests");
        System.out.println(" Version 5.0");
        System.out.println("=====================================");

        BookingQueue bookingQueue = new BookingQueue();

        // Simulate booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double Room"));

        // Show pending requests
        System.out.println("\nPending requests: " + bookingQueue.pendingRequests());

        // Process requests in FIFO order
        bookingQueue.processRequests();

        System.out.println("\nApplication executed successfully!");
    }
}