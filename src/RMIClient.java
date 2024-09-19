import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) {
        try {
            // Locate the registry where the remote object is bound
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Look up the remote object by name
            RMIServerInterface stub = (RMIServerInterface) registry.lookup("HelloServer");

            // Call the remote method and print the result
            String response = stub.sayHello();
            System.out.println("Name: Vibhuti Gupta");
            System.out.println("Roll no.: 0512080271");
            System.out.println("Response from server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
