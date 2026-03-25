import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        validateAvailability(roomType);
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

class BookingService {
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void createBooking(String guestName, String roomType) {
        try {
            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            inventory.bookRoom(roomType);

            System.out.println("Booking confirmed for " + guestName + " in " + roomType + " room.");
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        inventory.displayInventory();

        System.out.println();

        bookingService.createBooking("Arun", "Deluxe");
        bookingService.createBooking("", "Suite");
        bookingService.createBooking("Priya", "Luxury");
        bookingService.createBooking("Rahul", "Suite");
        bookingService.createBooking("Kiran", "Suite");

        System.out.println();
        inventory.displayInventory();
    }
}