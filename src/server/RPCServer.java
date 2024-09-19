package server;

import rpc.RPCInterface;
import java.io.*;
import java.net.*;

// Server class that implements the RPCInterface
public class RPCServer implements RPCInterface {
    private ServerSocket serverSocket;

    // Implement the add method
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    // Method to start the server
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while (true) {
                new ClientHandler(serverSocket.accept(), this).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner class to handle client requests
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private RPCServer server;

        public ClientHandler(Socket socket, RPCServer server) {
            this.clientSocket = socket;
            this.server = server;
        }

        public void run() {
            try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

                // Read method name and parameters from the client
                String methodName = (String) input.readObject();
                int a = input.readInt();
                int b = input.readInt();

                // Call the add method if requested
                if (methodName.equals("add")) {
                    int result = server.add(a, b);
                    output.writeInt(result); // Send result back to the client
                    output.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Main method to start the server
    public static void main(String[] args) {
        RPCServer server = new RPCServer();
        server.start(8080); // Server will listen on port 8080
    }
}
