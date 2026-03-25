package com.bookmystay.uc3;

import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 3: Centralized Room Inventory Management
 *
 * Centralizes room availability using HashMap to replace scattered variables.
 * Demonstrates encapsulation, single source of truth, and scalable design.
 *
 * Version: 3.1 (refactored)
 *
 * @author YourName
 */

// Inventory class
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add room type with availability
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get available rooms
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Book a room (decrement availability)
    public boolean bookRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " | Available: " + entry.getValue());
        }
    }
}

// Main class
public class MyStay {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("  Book My Stay App - Inventory Setup");
        System.out.println("  Version 3.1");
        System.out.println("=====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Add room types
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Display inventory
        inventory.displayInventory();

        // Simulate booking
        System.out.println("\nBooking a Single Room...");
        if (inventory.bookRoom("Single Room")) {
            System.out.println("Booking successful!");
        } else {
            System.out.println("No rooms available!");
        }

        // Display inventory after booking
        System.out.println();
        inventory.displayInventory();

        System.out.println("\nApplication executed successfully!");
    }
}