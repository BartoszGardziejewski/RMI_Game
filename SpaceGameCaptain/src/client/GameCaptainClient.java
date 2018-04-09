package client;

import application.ApplicationManager;
import server.GameCaptainI;
import server.GameServerI;
import server.Captain;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GameCaptainClient implements GameCaptainI{

    private GameServerI serverInterface;
    private Captain myPlayer;
    private ApplicationManager manager;

    public GameCaptainClient(int port, String playerName, ApplicationManager manager) throws RemoteException, NotBoundException {

        this.manager = manager;
        myPlayer = new Captain(playerName);
        String name = "playerConnection";
        Registry registry = LocateRegistry.getRegistry("localhost", port);
        serverInterface = (GameServerI) registry.lookup(name);
        serverInterface.greetPlayer(myPlayer);
        System.out.println(name + " reported: " + myPlayer.getName() );


        GameCaptainI stub = (GameCaptainI) UnicastRemoteObject.exportObject(this, 1);
        System.out.println("Server bound to \"" + name + "\"");
    }

    public String setGameServer() throws RemoteException {
        return serverInterface.setUpNewServer(myPlayer,this);
    }

    public void logOut() throws RemoteException {
        serverInterface.playerLeft(myPlayer);
    }

    @Override
    public void postOrder(String message) throws RemoteException {
        System.out.println(message);
        manager.displayMessage(message);
    }
}
