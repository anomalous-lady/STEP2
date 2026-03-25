package com.bookmystay.uc7;

import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 *
 * Allows guests to select optional services for their reservation.
 *
 * Version: 7.0
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

// Service class
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

// Add-On Service Manager
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(Reservation reservation, Service service) {
        reservationServices
                .computeIfAbsent(reservation.getReservationId(), k -> new ArrayList<>())
                .add(service);
        System.out.println("Added service " + service.getName() +
                " to reservation " + reservation.getReservationId());
    }

    public void displayServices(Reservation reservation) {
        List<Service> services = reservationServices.get(reservation.getReservationId());
        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for " + reservation.getGuestName());
            return;
        }
        double total = 0;
        System.out.println("Add-on services for " + reservation.getGuestName() + ":");
        for (Service s : services) {
            System.out.println("- " + s.getName() + " ($" + s.getCost() + ")");
            total += s.getCost();
        }
        System.out.println("Total additional cost: $" + total);
    }
}

// Main class
public class MyStay {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println(" Book My Stay App - Add-On Services");
        System.out.println(" Version 7.0");
        System.out.println("=====================================");

        // Create reservations
        Reservation r1 = new Reservation("RES001", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES002", "Bob", "Suite Room");

        // Create services
        Service breakfast = new Service("Breakfast", 10.0);
        Service airportPickup = new Service("Airport Pickup", 25.0);
        Service spa = new Service("Spa Package", 50.0);

        // Manage add-on services
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Assign services
        serviceManager.addService(r1, breakfast);
        serviceManager.addService(r1, airportPickup);
        serviceManager.addService(r2, spa);

        // Display assigned services
        System.out.println();
        serviceManager.displayServices(r1);
        System.out.println();
        serviceManager.displayServices(r2);
    }
}