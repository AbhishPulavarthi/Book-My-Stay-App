import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ID: " + reservationId + ", Guest: " + guestName + ", Room: " + roomType + ", Price: ₹" + price;
    }
}

class BookingHistory {
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("=== Booking History ===");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public void generateSummary(List<Reservation> reservations) {
        System.out.println("\n=== Booking Summary Report ===");

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getPrice();
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        history.addReservation(new Reservation("RES101", "Arun", "Deluxe", 3000));
        history.addReservation(new Reservation("RES102", "Priya", "Suite", 5000));
        history.addReservation(new Reservation("RES103", "Rahul", "Standard", 2000));

        List<Reservation> allBookings = history.getAllReservations();

        reportService.displayAllBookings(allBookings);
        reportService.generateSummary(allBookings);
    }
}