import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() { availability = new HashMap<>(); }

    public void addRoomType(String roomType, int count) { availability.put(roomType, count); }

    public int getAvailability(String roomType) { return availability.getOrDefault(roomType, 0); }

    public boolean decrementAvailability(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            availability.put(roomType, available - 1);
            return true;
        }
        return false;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() { queue = new LinkedList<>(); }

    public void addRequest(Reservation reservation) { queue.offer(reservation); }

    public Reservation pollRequest() { return queue.poll(); }

    public boolean isEmpty() { return queue.isEmpty(); }
}

class BookingService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
    }

    private String generateRoomID(String roomType) {
        return roomType.replaceAll("\\s", "") + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public void processBooking(BookingRequestQueue requestQueue) {
        System.out.println("\nProcessing Bookings:");

        while (!requestQueue.isEmpty()) {
            Reservation r = requestQueue.pollRequest();
            if (inventory.decrementAvailability(r.getRoomType())) {
                String roomID = generateRoomID(r.getRoomType());
                allocatedRooms.putIfAbsent(r.getRoomType(), new HashSet<>());
                allocatedRooms.get(r.getRoomType()).add(roomID);

                System.out.println("Reservation Confirmed: " + r.getGuestName() +
                        " | Room Type: " + r.getRoomType() +
                        " | Assigned Room ID: " + roomID);
            } else {
                System.out.println("Reservation Failed (No Availability): " + r.getGuestName() +
                        " | Room Type: " + r.getRoomType());
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Suite Room", 1);

        BookingRequestQueue requestQueue = new BookingRequestQueue();
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Double Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        requestQueue.addRequest(new Reservation("Diana", "Single Room"));
        requestQueue.addRequest(new Reservation("Evan", "Double Room"));

        System.out.println("======================================");
        System.out.println("   Welcome to Book My Stay App v6.0");
        System.out.println("======================================");

        BookingService bookingService = new BookingService(inventory);
        bookingService.processBooking(requestQueue);

        System.out.println("\nAll bookings processed.");
        System.out.println("Thank you for using Book My Stay!");
    }
}