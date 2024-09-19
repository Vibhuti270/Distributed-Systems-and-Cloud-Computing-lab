package client;

import java.io.*;
import java.net.*;

// Client class to connect to the RPCServer
public class RPCClient {

    private Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    // Connect to the server
    public void connect(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());
        System.out.println("Connected to server at " + ip + ":" + port);
    }


    public int callAdd(int a, int b) throws IOException, ClassNotFoundException {
        output.writeObject("add"); // Send method name
        output.writeInt(a);        // Send parameters
        output.writeInt(b);
        output.flush();

        // Get result from the server
        return input.readInt();
    }

    // Disconnect from the server
    public void disconnect() throws IOException {
        input.close();
        output.close();
        clientSocket.close();
    }

    // Main method to test the client
    public static void main(String[] args) {
        RPCClient client = new RPCClient();
        try {
            System.out.println("Name : Vibhuti Gupta");
            System.out.println("Roll no. : 05120802721");
            client.connect("localhost", 8080); // Connect to server at localhost:8080
            int result = client.callAdd(10, 20); // Call the 'add' method on server
            System.out.println("Result of 10 + 20 = " + result); // Output result
            client.disconnect(); // Disconnect from the server
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

