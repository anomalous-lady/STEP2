package com.bookmystay.uc4;

import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only access to centralized inventory
 * and safe separation of search functionality from booking logic.
 *
 * Version: 4.0
 *
 * Author: YourName
 */

// Inventory class reused from UC3
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return new HashMap<>(inventory); // return a copy to prevent modification
    }
}

// Room class
class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public void display() {
        System.out.println(type + " | Price: $" + price);
    }
}

// Main class
public class MyStay {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println(" Book My Stay App - Room Search");
        System.out.println(" Version 4.0");
        System.out.println("=====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 0);
        inventory.addRoomType("Suite Room", 2);

        // Initialize rooms
        Room single = new Room("Single Room", 100);
        Room doubleR = new Room("Double Room", 180);
        Room suite = new Room("Suite Room", 300);

        Room[] rooms = {single, doubleR, suite};

        // Search: show only available rooms
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (inventory.getAvailability(room.getType()) > 0) {
                room.display();
            }
        }

        System.out.println("\nSearch executed successfully!");
    }
}