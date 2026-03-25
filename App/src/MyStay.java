package com.bookmystay.uc12;

import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 *
 * Saves booking and inventory state to a file and restores on restart.
 *
 * Version: 12.0
 *
 * Author: YourName
 */

// Serializable Reservation class
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
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

    @Override
    public String toString() {
        return reservationId + " - " + guestName + " (" + roomType + ")";
    }
}

// Serializable Inventory class
class BookingInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> roomInventory;

    public BookingInventory() {
        roomInventory = new HashMap<>();
        roomInventory.put("Single Room", 2);
        roomInventory.put("Double Room", 2);
        roomInventory.put("Suite Room", 1);
    }

    public boolean allocateRoom(String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void restoreInventory(Map<String, Integer> snapshot) {
        this.roomInventory = snapshot;
    }

    public Map<String, Integer> getInventorySnapshot() {
        return new HashMap<>(roomInventory);
    }

    @Override
    public String toString() {
        return roomInventory.toString();
    }
}

public class MyStay {

    private static final String FILE_PATH = "booking_system_state.ser";

    public static void saveState(List<Reservation> bookings, BookingInventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(bookings);
            oos.writeObject(inventory);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Pair<List<Reservation>, BookingInventory> loadState() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh.");
            return new Pair<>(new ArrayList<>(), new BookingInventory());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            List<Reservation> bookings = (List<Reservation>) ois.readObject();
            BookingInventory inventory = (BookingInventory) ois.readObject();
            System.out.println("System state restored successfully.");
            return new Pair<>(bookings, inventory);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state: " + e.getMessage());
            return new Pair<>(new ArrayList<>(), new BookingInventory());
        }
    }

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println(" Book My Stay App - Data Persistence & Recovery");
        System.out.println(" Version 12.0");
        System.out.println("=====================================");

        // Load persisted state
        Pair<List<Reservation>, BookingInventory> state = loadState();
        List<Reservation> bookings = state.first;
        BookingInventory inventory = state.second;

        // Sample booking attempt
        Reservation r1 = new Reservation("RES301", "Eve", "Double Room");
        if (inventory.allocateRoom(r1.getRoomType())) {
            bookings.add(r1);
            System.out.println("Booking successful: " + r1);
        } else {
            System.out.println("Booking failed: " + r1.getRoomType());
        }

        // Save current state
        saveState(bookings, inventory);

        // Display inventory snapshot
        System.out.println("Current Inventory: " + inventory.getInventorySnapshot());
        System.out.println("Booking History: " + bookings);
    }
}

// Simple generic Pair class
class Pair<F, S> {
    public final F first;
    public final S second;
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}