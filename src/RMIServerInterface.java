import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
    // Method to be invoked remotely
    String sayHello() throws RemoteException;
}

