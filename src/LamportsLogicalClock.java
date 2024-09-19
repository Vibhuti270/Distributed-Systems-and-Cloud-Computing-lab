import java.util.Scanner;

class LamportsClock {
    int logicalClock;

    // Constructor to initialize the clock
    public LamportsClock() {
        logicalClock = 0;
    }

    // Function to send an event (increments the clock)
    public void sendEvent() {
        logicalClock++;
        System.out.println("Send event occurred, updated logical clock: " + logicalClock);
    }

    // Function to receive an event (updates the clock based on received timestamp)
    public void receiveEvent(int receivedTimestamp) {
        logicalClock = Math.max(logicalClock, receivedTimestamp) + 1;
        System.out.println("Receive event occurred, updated logical clock: " + logicalClock);
    }

    // Function to display the logical clock
    public void displayClock() {
        System.out.println("Current logical clock value: " + logicalClock);
    }
}

public class LamportsLogicalClock {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Creating two processes with Lamport clocks
        LamportsClock process1 = new LamportsClock();
        LamportsClock process2 = new LamportsClock();
        System.out.println("Name: Vibhuti Gupta");
        System.out.println("Roll no.: 05120802721");
        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Process 1 sends event");
            System.out.println("2. Process 2 sends event");
            System.out.println("3. Process 1 receives event");
            System.out.println("4. Process 2 receives event");
            System.out.println("5. Display clocks");
            System.out.println("6. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    process1.sendEvent();
                    break;
                case 2:
                    process2.sendEvent();
                    break;
                case 3:
                    System.out.print("Enter the timestamp received by Process 1: ");
                    int receivedTimestamp1 = sc.nextInt();
                    process1.receiveEvent(receivedTimestamp1);
                    break;
                case 4:
                    System.out.print("Enter the timestamp received by Process 2: ");
                    int receivedTimestamp2 = sc.nextInt();
                    process2.receiveEvent(receivedTimestamp2);
                    break;
                case 5:
                    System.out.println("Process 1 Clock: ");
                    process1.displayClock();
                    System.out.println("Process 2 Clock: ");
                    process2.displayClock();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        sc.close();
    }
}

