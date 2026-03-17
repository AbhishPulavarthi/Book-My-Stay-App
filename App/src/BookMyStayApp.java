import java.util.HashMap;
import java.util.Map;

abstract class Room {
    protected String name;
    protected int beds;
    protected double price;

    public Room(String name, int beds, double price) {
        this.name = name;
        this.beds = beds;
        this.price = price;
    }

    public abstract void displayDetails();
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 50.0); }
    public void displayDetails() { System.out.println(name + " | Beds: " + beds + " | Price: $" + price); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 90.0); }
    public void displayDetails() { System.out.println(name + " | Beds: " + beds + " | Price: $" + price); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 150.0); }
    public void displayDetails() { System.out.println(name + " | Beds: " + beds + " | Price: $" + price); }
}

class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        availability.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        availability.put(roomType, newCount);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " | Available: " + entry.getValue());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Room single = new SingleRoom();
        Room doubleR = new DoubleRoom();
        Room suite = new SuiteRoom();

        inventory.addRoomType(single.name, 5);
        inventory.addRoomType(doubleR.name, 3);
        inventory.addRoomType(suite.name, 2);

        System.out.println("======================================");
        System.out.println("   Welcome to Book My Stay App v3.1");
        System.out.println("======================================\n");

        single.displayDetails();
        doubleR.displayDetails();
        suite.displayDetails();

        System.out.println("\nCurrent Room Inventory:");
        inventory.displayInventory();

        System.out.println("\nThank you for using Book My Stay!");
    }
}