import java.util.*;

// Booking class to store reservation details
class Booking {
    String bookingId;
    String guestName;
    String roomType;
    String roomId;
    boolean isCancelled;

    public Booking(String bookingId, String guestName, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

// Inventory Manager
class InventoryManager {
    private Map<String, Integer> roomInventory = new HashMap<>();

    public void addRoomType(String roomType, int count) {
        roomInventory.put(roomType, count);
    }

    public boolean allocateRoom(String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void releaseRoom(String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        roomInventory.put(roomType, available + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " : " + roomInventory.get(type));
        }
    }
}

// Cancellation Service
class CancellationService {
    private Map<String, Booking> bookingMap;
    private InventoryManager inventoryManager;
    private Stack<String> rollbackStack;

    public CancellationService(Map<String, Booking> bookingMap, InventoryManager inventoryManager) {
        this.bookingMap = bookingMap;
        this.inventoryManager = inventoryManager;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String bookingId) {
        System.out.println("\nProcessing cancellation for Booking ID: " + bookingId);

        // Step 1: Validate booking existence
        if (!bookingMap.containsKey(bookingId)) {
            System.out.println("❌ Invalid booking ID. Cancellation failed.");
            return;
        }

        Booking booking = bookingMap.get(bookingId);

        // Step 2: Check if already cancelled
        if (booking.isCancelled) {
            System.out.println("❌ Booking already cancelled.");
            return;
        }

        // Step 3: Push roomId to rollback stack
        rollbackStack.push(booking.roomId);

        // Step 4: Restore inventory
        inventoryManager.releaseRoom(booking.roomType);

        // Step 5: Update booking state
        booking.isCancelled = true;

        // Step 6: Confirm rollback
        System.out.println("✅ Booking cancelled successfully.");
        System.out.println("Room " + booking.roomId + " released back to inventory.");
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent Releases): " + rollbackStack);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Step 1: Setup inventory
        InventoryManager inventory = new InventoryManager();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);

        // Step 2: Simulate bookings
        Map<String, Booking> bookingMap = new HashMap<>();

        Booking b1 = new Booking("B101", "Alice", "Single", "S1");
        Booking b2 = new Booking("B102", "Bob", "Double", "D1");

        bookingMap.put(b1.bookingId, b1);
        bookingMap.put(b2.bookingId, b2);

        // Reduce inventory as if booked
        inventory.allocateRoom("Single");
        inventory.allocateRoom("Double");

        System.out.println("=== Initial State ===");
        inventory.displayInventory();

        // Step 3: Cancellation Service
        CancellationService cancelService = new CancellationService(bookingMap, inventory);

        // Valid cancellation
        cancelService.cancelBooking("B101");

        // Invalid cancellation (duplicate)
        cancelService.cancelBooking("B101");

        // Invalid booking ID
        cancelService.cancelBooking("B999");

        // Final state
        System.out.println("\n=== Final State ===");
        inventory.displayInventory();

        cancelService.showRollbackStack();
    }
}