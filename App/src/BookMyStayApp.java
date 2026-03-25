import java.util.*;
import java.util.concurrent.*;

// Booking Request Class
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Hotel Inventory (Shared Resource)
class HotelInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public HotelInventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
    }

    // Critical Section (Thread Safe)
    public synchronized boolean allocateRoom(String roomType) {
        int available = rooms.getOrDefault(roomType, 0);

        if (available > 0) {
            rooms.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("Remaining Inventory: " + rooms);
    }
}

// Booking Processor (Thread)
class BookingProcessor implements Runnable {
    private Queue<BookingRequest> bookingQueue;
    private HotelInventory inventory;

    public BookingProcessor(Queue<BookingRequest> bookingQueue, HotelInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // Synchronize queue access
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                request = bookingQueue.poll();
            }

            processBooking(request);
        }
    }

    private void processBooking(BookingRequest request) {
        String guest = request.getGuestName();
        String roomType = request.getRoomType();

        boolean success = inventory.allocateRoom(roomType);

        if (success) {
            System.out.println(Thread.currentThread().getName() +
                    " → Booking SUCCESS for " + guest + " (" + roomType + ")");
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " → Booking FAILED for " + guest + " (" + roomType + ")");
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Shared Booking Queue
        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        // Add Requests
        bookingQueue.add(new BookingRequest("Alice", "Single"));
        bookingQueue.add(new BookingRequest("Bob", "Single"));
        bookingQueue.add(new BookingRequest("Charlie", "Single"));
        bookingQueue.add(new BookingRequest("David", "Double"));
        bookingQueue.add(new BookingRequest("Eve", "Double"));
        bookingQueue.add(new BookingRequest("Frank", "Double"));

        // Shared Inventory
        HotelInventory inventory = new HotelInventory();

        // Create Threads
        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventory), "Processor-1");
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventory), "Processor-2");
        Thread t3 = new Thread(new BookingProcessor(bookingQueue, inventory), "Processor-3");

        // Start Threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for Completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final Inventory
        inventory.displayInventory();
    }
}