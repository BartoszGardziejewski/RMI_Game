package client;

import application.ApplicationManager;
import javafx.application.Platform;
import server.Defender;
import server.Engineer;
import server.GameServerI;
import server.Player;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GameClientController {

    private Defender myPlayer;
    private GameServerI serverInterface;
    private String message;
    private ApplicationManager manager;

    public GameClientController(int port, Defender player, ApplicationManager manager) throws RemoteException, NotBoundException {
        this.manager = manager;
        myPlayer = player;
        String name = "playerConnection";
        Registry registry = LocateRegistry.getRegistry("localhost", port);
        serverInterface = (GameServerI) registry.lookup(name);
        serverInterface.greetPlayer(myPlayer);
        System.out.println(name + " reported: " + myPlayer.getName());
    }

    public void sendMassage(){
        try {

            if(serverInterface.reportToCaptain(myPlayer)){
                myPlayer.addPoint();
            }else {
                myPlayer.removePoint();
            }
            Platform.runLater(()->{
                manager.setPoints();
            });

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getMassage() {
        return message;
    }

    public void logOut() throws RemoteException {
        serverInterface.playerLeft(myPlayer);
    }
}
