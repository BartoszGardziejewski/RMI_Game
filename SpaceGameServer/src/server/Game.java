package server;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Game  implements Serializable {

    private GameCaptainI captain;
    private ArrayList<Player> players;

    public GameCaptainI getCaptain(){
        return captain;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Game(GameCaptainI gameCaptain) throws RemoteException, NotBoundException {
        this.players = new ArrayList<>();
        captain = gameCaptain;

    }
}
