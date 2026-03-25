import java.io.*;
import java.util.*;

// Booking Class (Serializable)
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private String guestName;
    private String roomType;

    public Booking(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return guestName + " -> " + roomType;
    }
}

// Inventory Class (Serializable)
class HotelInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> rooms;

    public HotelInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
    }

    public boolean allocateRoom(String roomType) {
        int available = rooms.getOrDefault(roomType, 0);

        if (available > 0) {
            rooms.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Integer> rooms) {
        this.rooms = rooms;
    }

    public void displayInventory() {
        System.out.println("Inventory: " + rooms);
    }
}

// Wrapper Class to Persist State
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Booking> bookings;
    HotelInventory inventory;

    public SystemState(List<Booking> bookings, HotelInventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {
    private static final String FILE_NAME = "hotel_state.ser";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting with clean state.");
            return null;
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Attempt Recovery
        SystemState state = PersistenceService.load();

        List<Booking> bookings;
        HotelInventory inventory;

        if (state != null) {
            bookings = state.bookings;
            inventory = state.inventory;
        } else {
            bookings = new ArrayList<>();
            inventory = new HotelInventory();
        }

        // Simulate New Bookings
        System.out.println("\nProcessing Bookings...\n");

        processBooking("Alice", "Single", bookings, inventory);
        processBooking("Bob", "Single", bookings, inventory);
        processBooking("Charlie", "Single", bookings, inventory);

        // Display Current State
        System.out.println("\nCurrent Bookings:");
        for (Booking b : bookings) {
            System.out.println(b);
        }

        inventory.displayInventory();

        // Save State Before Shutdown
        PersistenceService.save(new SystemState(bookings, inventory));
    }

    private static void processBooking(String guest, String roomType,
                                       List<Booking> bookings,
                                       HotelInventory inventory) {

        if (inventory.allocateRoom(roomType)) {
            Booking booking = new Booking(guest, roomType);
            bookings.add(booking);
            System.out.println("Booking SUCCESS for " + guest);
        } else {
            System.out.println("Booking FAILED for " + guest);
        }
    }
}