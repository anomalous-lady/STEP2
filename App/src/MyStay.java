package com.bookmystay.uc9;

import java.util.*;

/**
 * Use Case 9: Error Handling & Validation
 *
 * Validates booking requests and ensures system state consistency.
 *
 * Version: 9.0
 *
 * Author: YourName
 */

// Custom exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) throws InvalidBookingException {
        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        if (!roomType.equals("Single Room") && !roomType.equals("Double Room") && !roomType.equals("Suite Room")) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking Service with validation
class BookingService {
    private List<Reservation> confirmedBookings;
    private Map<String, Integer> roomInventory;

    public BookingService() {
        confirmedBookings = new ArrayList<>();
        roomInventory = new HashMap<>();
        roomInventory.put("Single Room", 2);
        roomInventory.put("Double Room", 2);
        roomInventory.put("Suite Room", 1);
    }

    public void confirmBooking(Reservation reservation) throws InvalidBookingException {
        String room = reservation.getRoomType();
        int available = roomInventory.getOrDefault(room, 0);
        if (available <= 0) {
            throw new InvalidBookingException("No available rooms of type: " + room);
        }
        confirmedBookings.add(reservation);
        roomInventory.put(room, available - 1);
        System.out.println("Booking confirmed for " + reservation.getGuestName() + " (" + room + ")");
    }

    public void showBookings() {
        System.out.println("Confirmed Bookings:");
        for (Reservation r : confirmedBookings) {
            System.out.println("- " + r.getReservationId() + ": " + r.getGuestName() + " (" + r.getRoomType() + ")");
        }
    }
}

// Main class
public class MyStay {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println(" Book My Stay App - Error Handling & Validation");
        System.out.println(" Version 9.0");
        System.out.println("=====================================");

        BookingService service = new BookingService();

        try {
            Reservation r1 = new Reservation("RES101", "Alice", "Single Room");
            service.confirmBooking(r1);

            Reservation r2 = new Reservation("RES102", "Bob", "Suite Room");
            service.confirmBooking(r2);

            Reservation r3 = new Reservation("RES103", "", "Double Room"); // Invalid guest name
            service.confirmBooking(r3);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        System.out.println();
        service.showBookings();
    }
}