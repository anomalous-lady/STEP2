/**
 * Use Case 3: Centralized Room Inventory Management
 *
 * This class demonstrates centralized inventory management using HashMap
 * for the Book My Stay application.
 * It replaces scattered availability variables with a single, consistent structure.
 *
 * Version: 3.1 (refactored)
 *
 * @author YourName
 */

package com.bookmystay.uc3;

import java.util.HashMap;
import java.util.Map;

// Inventory class to manage room availability
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add room type with initial availability
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get available rooms
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (subtract booked rooms)
    public boolean bookRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        } else {
            return false; // no rooms available
        }
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " | Available: " + entry.getValue());
        }
    }
}

// Main class for UC3
public class MyStay {
    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("  Book My Stay App - Inventory Setup");
        System.out.println("  Version 3.1");
        System.out.println("=====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Add room types with counts
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Display initial inventory
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