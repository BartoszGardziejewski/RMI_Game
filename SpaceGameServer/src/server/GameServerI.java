package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServerI extends Remote {

    Player greetPlayer(Player player) throws RemoteException;
    void playerLeft(Player player) throws RemoteException;
    String setUpNewServer(Player player,GameCaptainI captainI)  throws RemoteException;
    void shootDownServer(Player player,GameCaptainI captainI)  throws RemoteException;
    boolean reportToCaptain(Player player) throws RemoteException;

}
