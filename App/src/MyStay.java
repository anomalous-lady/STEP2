package com.bookmystay.uc10;

import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Safely cancels confirmed bookings and restores system state.
 *
 * Version: 10.0
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

class CancellationService {
    private List<Reservation> confirmedBookings;
    private Map<String, Integer> roomInventory;
    private Stack<String> rollbackStack;

    public CancellationService() {
        confirmedBookings = new ArrayList<>();
        roomInventory = new HashMap<>();
        rollbackStack = new Stack<>();
        roomInventory.put("Single Room", 2);
        roomInventory.put("Double Room", 2);
        roomInventory.put("Suite Room", 1);
    }

    public void confirmBooking(Reservation r) {
        confirmedBookings.add(r);
        String room = r.getRoomType();
        roomInventory.put(room, roomInventory.get(room) - 1);
        System.out.println("Booking confirmed for " + r.getGuestName() + " (" + room + ")");
    }

    public void cancelBooking(String reservationId) {
        Reservation toCancel = null;
        for (Reservation r : confirmedBookings) {
            if (r.getReservationId().equals(reservationId)) {
                toCancel = r;
                break;
            }
        }
        if (toCancel == null) {
            System.out.println("Cancellation failed: Reservation ID not found.");
            return;
        }

        // Push room type onto rollback stack
        rollbackStack.push(toCancel.getRoomType());

        // Restore inventory
        String room = rollbackStack.pop();
        roomInventory.put(room, roomInventory.get(room) + 1);

        // Remove from confirmed bookings
        confirmedBookings.remove(toCancel);
        System.out.println("Booking cancelled for " + toCancel.getGuestName() + " (" + room + ")");
    }

    public void showBookings() {
        System.out.println("\nConfirmed Bookings:");
        for (Reservation r : confirmedBookings) {
            System.out.println("- " + r.getReservationId() + ": " + r.getGuestName() + " (" + r.getRoomType() + ")");
        }
        System.out.println("Current Inventory: " + roomInventory);
    }
}

public class MyStay {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println(" Book My Stay App - Booking Cancellation & Inventory Rollback");
        System.out.println(" Version 10.0");
        System.out.println("=====================================");

        CancellationService service = new CancellationService();

        // Confirm some bookings
        Reservation r1 = new Reservation("RES101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES102", "Bob", "Suite Room");
        service.confirmBooking(r1);
        service.confirmBooking(r2);

        // Attempt cancellation
        service.cancelBooking("RES101"); // valid cancellation
        service.cancelBooking("RES999"); // invalid cancellation

        // Show current bookings and inventory
        service.showBookings();
    }
}