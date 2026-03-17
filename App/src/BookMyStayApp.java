import java.util.LinkedList;
import java.util.Queue;

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

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() { queue = new LinkedList<>(); }

    public void addRequest(Reservation reservation) { queue.offer(reservation); }

    public void displayRequests() {
        System.out.println("\nPending Booking Requests (FIFO Order):");
        for (Reservation r : queue) {
            System.out.println("Guest: " + r.getGuestName() + " | Room Type: " + r.getRoomType());
        }
    }

    public boolean isEmpty() { return queue.isEmpty(); }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        requestQueue.addRequest(new Reservation("Alic", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Double Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        requestQueue.addRequest(new Reservation("Diana", "Single Room"));

        System.out.println("======================================");
        System.out.println("   Welcome to Book My Stay App v5.0");
        System.out.println("======================================");

        requestQueue.displayRequests();

        System.out.println("\nBooking requests are stored in arrival order and ready for allocation.");
        System.out.println("Thank you for using Book My Stay!");
    }
}