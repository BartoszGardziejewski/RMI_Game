package client;

import application.ApplicationManager;
import javafx.application.Platform;
import server.Engineer;
import server.GameServerI;
import server.Player;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GameClientController {

    private Engineer myPlayer;
    private GameServerI serverInterface;
    private String message;
    private ApplicationManager manager;

    public GameClientController(int port, Engineer player, ApplicationManager manager) throws RemoteException, NotBoundException {
        this.manager = manager;
        myPlayer = player;
        String name = "playerConnection";
        Registry registry = LocateRegistry.getRegistry("localhost", port);
        serverInterface = (GameServerI) registry.lookup(name);
        serverInterface.greetPlayer(myPlayer);
        System.out.println(name + " reported: " + myPlayer.getName());
    }

    public void sendMassage(){

        System.out.println(" Engineer reports ");
        System.out.println(" --  Rear Engine Thrust = " + myPlayer.getRearEngineThrust());
        System.out.println(" --  Left Engine Thrust = " + myPlayer.getLeftEngineThrust());
        System.out.println(" --  Right Engine Thrust = " + myPlayer.getRightEngineThrust());

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
