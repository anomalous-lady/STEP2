package com.bookmystay.uc6;

import java.util.*;

/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Processes booking requests in FIFO order, allocates unique rooms,
 * updates inventory, and prevents double-booking.
 *
 * Version: 6.0
 *
 * Author: YourName
 */

// Reservation class reused from UC5
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
}

// Inventory class
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        availability.put(type, count);
    }

    public boolean isAvailable(String type) {
        return availability.getOrDefault(type, 0) > 0;
    }

    public void decrement(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }
}

// Allocation Service
class RoomAllocationService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
    }

    public void allocateRoom(Reservation r) {
        String type = r.getRoomType();

        if (!inventory.isAvailable(type)) {
            System.out.println("No available " + type + " for " + r.getGuestName());
            return;
        }

        // Generate unique room ID
        Set<String> assigned = allocatedRooms.getOrDefault(type, new HashSet<>());
        String roomId;
        do {
            roomId = type.substring(0, 2).toUpperCase() + (assigned.size() + 1);
        } while (assigned.contains(roomId));

        assigned.add(roomId);
        allocatedRooms.put(type, assigned);

        // Update inventory
        inventory.decrement(type);

        System.out.println("Reservation confirmed for " + r.getGuestName() +
                " | Room Type: " + type + " | Room ID: " + roomId);
    }
}

// Main class
public class MyStay {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println(" Book My Stay App - Room Allocation");
        System.out.println(" Version 6.0");
        System.out.println("=====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Suite Room", 1);

        // Simulate booking queue
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Suite Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Double Room"));

        // Allocation service
        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        // Process bookings
        while (!bookingQueue.isEmpty()) {
            allocationService.allocateRoom(bookingQueue.poll());
        }

        System.out.println("\nAll booking requests processed successfully!");
    }
}