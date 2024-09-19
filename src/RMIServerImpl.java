import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerImpl extends UnicastRemoteObject implements RMIServerInterface {

    // Constructor must handle RemoteException
    protected RMIServerImpl() throws RemoteException {
        super();
    }

    // Implement the sayHello() method
    @Override
    public String sayHello() throws RemoteException {
        return "Hello from the RMI server!";
    }
}

