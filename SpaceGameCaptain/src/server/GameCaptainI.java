package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameCaptainI  extends Remote {

    void postOrder(String message)throws RemoteException;

}
