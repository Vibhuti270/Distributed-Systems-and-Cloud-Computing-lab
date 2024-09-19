import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Process {
    enum State { REQUESTING, EXECUTING, RELEASED }

    int pid, logicalClock, replyCount;
    State state;
    Queue<Request> deferredRequests;

    public Process(int pid) {
        this.pid = pid;
        this.logicalClock = 0;
        this.state = State.RELEASED;
        this.replyCount = 0;
        this.deferredRequests = new LinkedList<>();
    }

    // Update logical clock
    public void updateClock(int timestamp) {
        logicalClock = Math.max(logicalClock, timestamp) + 1;
    }

    // Send a request for the critical section
    public void sendRequest(ArrayList<Process> processes) {
        state = State.REQUESTING;
        logicalClock++;
        replyCount = 0;
        System.out.println("Process " + pid + " is requesting CS at time " + logicalClock);
        for (Process p : processes) {
            if (p.pid != this.pid) {
                p.receiveRequest(new Request(this.pid, this.logicalClock));
            }
        }
    }

    // Receive a request message from another process
    public void receiveRequest(Request req) {
        updateClock(req.timestamp);
        System.out.println("Process " + pid + " received request from Process " + req.pid + " with timestamp " + req.timestamp);

        if (state == State.EXECUTING || (state == State.REQUESTING && (logicalClock < req.timestamp || (logicalClock == req.timestamp && pid < req.pid)))) {
            // Defer the request
            deferredRequests.add(req);
            System.out.println("Process " + pid + " defers reply to Process " + req.pid);
        } else {
            // Reply immediately
            sendReply(req.pid);
        }
    }

    // Send a reply to a requesting process
    public void sendReply(int targetPid) {
        System.out.println("Process " + pid + " sends reply to Process " + targetPid);
        replyCount++;
    }

    // Enter the critical section if all replies are received
    public void enterCriticalSection(int numProcesses) {
        if (replyCount == numProcesses - 1) {
            state = State.EXECUTING;
            System.out.println("Process " + pid + " enters the critical section");
        }
    }

    // Exit the critical section and release resources
    public void exitCriticalSection(ArrayList<Process> processes) {
        System.out.println("Process " + pid + " leaves the critical section");
        state = State.RELEASED;
        // Send release messages
        for (Request r : deferredRequests) {
            sendReply(r.pid);
        }
        deferredRequests.clear();
    }
}

class Request implements Comparable<Request> {
    int pid, timestamp;

    public Request(int pid, int timestamp) {
        this.pid = pid;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(Request other) {
        if (this.timestamp == other.timestamp) {
            return Integer.compare(this.pid, other.pid);
        }
        return Integer.compare(this.timestamp, other.timestamp);
    }
}

public class LamportsMutualExclusion {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();
        System.out.println("Name: Vibhuti Gupta");
        System.out.println("Roll no.: 05120802721");
        // Create 3 processes
        for (int i = 0; i < 3; i++) {
            processes.add(new Process(i + 1));
        }

        boolean running = true;
        while (running) {
            System.out.println("Choose an option:");
            System.out.println("1. Process 1 request CS\n2. Process 2 request CS\n3. Process 3 request CS\n4. Process 1 exit CS\n5. Process 2 exit CS\n6. Process 3 exit CS\n7. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> processes.get(0).sendRequest(processes);
                case 2 -> processes.get(1).sendRequest(processes);
                case 3 -> processes.get(2).sendRequest(processes);
                case 4 -> processes.get(0).exitCriticalSection(processes);
                case 5 -> processes.get(1).exitCriticalSection(processes);
                case 6 -> processes.get(2).exitCriticalSection(processes);
                case 7 -> running = false;
                default -> System.out.println("Invalid choice.");
            }

            // Try to enter the critical section for each process
            for (Process p : processes) {
                p.enterCriticalSection(processes.size());
            }
        }
        sc.close();
    }
}


