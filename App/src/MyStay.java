package com.bookmystay.uc8;

import java.util.*;

/**
 * Use Case 8: Booking History & Reporting
 *
 * Maintains confirmed booking history and generates simple reports.
 *
 * Version: 8.0
 *
 * Author: YourName
 */

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
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

// Booking History Service
class BookingHistory {
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    public void addBooking(Reservation reservation) {
        confirmedBookings.add(reservation);
        System.out.println("Booking confirmed for " + reservation.getGuestName());
    }

    public void displayAllBookings() {
        if (confirmedBookings.isEmpty()) {
            System.out.println("No confirmed bookings.");
            return;
        }
        System.out.println("Booking History:");
        for (Reservation r : confirmedBookings) {
            System.out.println("- " + r.getReservationId() + ": " + r.getGuestName() +
                    " (" + r.getRoomType() + ")");
        }
    }

    public void generateReport() {
        System.out.println("Booking Summary Report:");
        Map<String, Integer> roomCount = new HashMap<>();
        for (Reservation r : confirmedBookings) {
            roomCount.put(r.getRoomType(), roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }
        for (String roomType : roomCount.keySet()) {
            System.out.println(roomType + ": " + roomCount.get(roomType) + " bookings");
        }
    }
}

// Main class
public class MyStay {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println(" Book My Stay App - Booking History & Reporting");
        System.out.println(" Version 8.0");
        System.out.println("=====================================");

        // Create reservations
        Reservation r1 = new Reservation("RES001", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES002", "Bob", "Suite Room");
        Reservation r3 = new Reservation("RES003", "Charlie", "Double Room");

        // Manage booking history
        BookingHistory history = new BookingHistory();

        // Add bookings
        history.addBooking(r1);
        history.addBooking(r2);
        history.addBooking(r3);

        System.out.println();
        history.displayAllBookings();
        System.out.println();
        history.generateReport();
    }
}