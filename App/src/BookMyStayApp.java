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
    public SingleRoom() {
        super("Single Room", 1, 50.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(name + " | Beds: " + beds + " | Price: $" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 90.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(name + " | Beds: " + beds + " | Price: $" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 150.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(name + " | Beds: " + beds + " | Price: $" + price);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        Room single = new SingleRoom();
        Room doubleR = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("======================================");
        System.out.println("   Welcome to Book My Stay App v2.1");
        System.out.println("======================================\n");

        single.displayDetails();
        System.out.println("Available: " + singleRoomAvailable + "\n");

        doubleR.displayDetails();
        System.out.println("Available: " + doubleRoomAvailable + "\n");

        suite.displayDetails();
        System.out.println("Available: " + suiteRoomAvailable + "\n");

        System.out.println("Thank you for using Book My Stay!");
    }
}