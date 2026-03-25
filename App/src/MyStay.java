/**
 * Use Case 2: Basic Room Types & Static Availability
 *
 * This class demonstrates object-oriented domain modeling for rooms in
 * the Book My Stay application.
 * It introduces inheritance, abstraction, polymorphism, and static availability.
 *
 * Version: 2.1 (refactored)
 *
 * @author YourName
 */

package com.bookmystay.uc2;

// Abstract Room class
abstract class Room {
    private String roomType;
    private int beds;
    private double pricePerNight;

    public Room(String roomType, int beds, double pricePerNight) {
        this.roomType = roomType;
        this.beds = beds;
        this.pricePerNight = pricePerNight;
    }

    // Abstract method to show room details
    public abstract void displayRoomDetails();

    // Getters
    public String getRoomType() { return roomType; }
    public int getBeds() { return beds; }
    public double getPricePerNight() { return pricePerNight; }
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom(double pricePerNight) {
        super("Single Room", 1, pricePerNight);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(getRoomType() + " | Beds: " + getBeds() + " | Price: $" + getPricePerNight());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom(double pricePerNight) {
        super("Double Room", 2, pricePerNight);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(getRoomType() + " | Beds: " + getBeds() + " | Price: $" + getPricePerNight());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(double pricePerNight) {
        super("Suite Room", 4, pricePerNight);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(getRoomType() + " | Beds: " + getBeds() + " | Price: $" + getPricePerNight());
    }
}

// Main class
public class MyStay


{
    // Static availability for each room type
    static int singleRoomAvailable = 5;
    static int doubleRoomAvailable = 3;
    static int suiteRoomAvailable = 2;

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("  Book My Stay App - Room Initialization");
        System.out.println("  Version 2.1");
        System.out.println("=====================================");

        // Initialize room objects
        Room single = new SingleRoom(50.0);
        Room doubleR = new DoubleRoom(90.0);
        Room suite = new SuiteRoom(200.0);

        // Display room details
        single.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailable);

        doubleR.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailable);

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailable);

        System.out.println("Application executed successfully!");
    }
}