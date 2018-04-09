package server;

import application.ApplicationManager;
import javafx.application.Platform;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class GameServerController implements GameServerI {

    public HashMap<String, Player> players;
    public ArrayList<Game> games;

    private Order order;

    private int port;
    private ApplicationManager manager;
    private int gameNumber = 0;


    public int getPort(){
        return port;
    }

    @Override
    public Player greetPlayer(Player player) throws RemoteException {

        if(players.containsKey(player.getName()) ){
            throw new RemoteException();
        }

        players.put(player.getName(),player);
        Platform.runLater(()->{
            manager.addLogMassage(player.getName()+" has connected ");
            manager.refreshPlayersList();
        });
        return player;
    }

    @Override
    public void playerLeft(Player player) throws RemoteException {
        players.remove(player.getName());
        Platform.runLater(()->{
            manager.addLogMassage(player.getName()+" has left ");
            manager.refreshPlayersList();
        });
    }

    @Override
    public String setUpNewServer(Player player,GameCaptainI captainI) throws RemoteException {
        gameNumber++;
        Player cPlayer = players.get(player.getName());

        Game tmpGame = null;
        try {
            tmpGame = new Game(captainI);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        games.add(tmpGame);

        cPlayer.joinGame(tmpGame);
        tmpGame.addPlayer(cPlayer);

        for (Map.Entry<String,Player> entry: players.entrySet()){
            Player mPlayer = entry.getValue();
            if(!(mPlayer.isInGame())){
                tmpGame.addPlayer(mPlayer);
                mPlayer.joinGame(tmpGame);
                mPlayer.setOrder(generateOrder(mPlayer,tmpGame));
            }
        }

        Platform.runLater(()-> {
            manager.refreshPlayersList();
        });

        return "game" + gameNumber;
    }

    @Override
    public void shootDownServer(Player player, GameCaptainI captainI) throws RemoteException {

        Player cPlayer = players.get(player.getName());
        Game tmpGame = games.get(games.indexOf(cPlayer.getGame()));

        for (Player fPlayer:
        tmpGame.getPlayers()) {
            fPlayer.leaveGame();
        }

        games.remove(tmpGame);

        Platform.runLater(()-> {
            manager.refreshPlayersList();
        });
    }

    @Override
    public boolean reportToCaptain(Player player) throws RemoteException {
        System.out.println(player.getName()+" sends message");

        Player tmpPlayer = players.get(player.getName());

        if ( tmpPlayer.getGame() == null ){
            System.out.println(player.getName()+" not in game");
            return false;
        }
        Game tmpGame = (games.get(games.indexOf( tmpPlayer.getGame() )));


        if( tmpPlayer.getOrder().checkOrder(player) ){
            System.out.println(player.getName() + " good job ");
            tmpGame.getCaptain().postOrder(player.toString() + " completed order");
            tmpPlayer.setOrder(generateOrder(player,tmpGame));
            return true;
        }else {
            System.out.println(player.getName() + " wrong ");
            return false;
        }
    }

    public GameServerController(int port,ApplicationManager manager,String name) throws RemoteException{

        this.manager = manager;
        this.port = port;

        games = new ArrayList<>();
        players = new HashMap<>();

        GameServerI serverI = this;
        GameServerI stub = (GameServerI) UnicastRemoteObject.exportObject(serverI, 0);

        Registry registry = LocateRegistry.getRegistry(port);
        registry.rebind(name, stub);
        System.out.println("Server bound to \"" + name + "\"");
    }

    private Order generateOrder(Player player, Game game){

        Order tmpOrder;

        if(player.getClass() == Engineer.class) {
            tmpOrder = new Order() {
                int x = (int) (Math.random() * 100);
                int y = (int) (Math.random() * 6);

                boolean startingToggle;

                {
                    switch (y) {
                        case 3:
                            startingToggle = ((Engineer) player).isRearEngineTurbo();
                            break;
                        case 4:
                            startingToggle = ((Engineer) player).isLeftEngineTurbo() ;
                            break;
                        default:
                            startingToggle = ((Engineer) player).isRightEngineTurbo() ;
                            break;

                    }
                }

                @Override
                public boolean checkOrder(Player player) {
                    switch (y){
                        case 0:
                            return
                                    (((Engineer) player).getRearEngineThrust() < x + 1) &&
                                            (((Engineer) player).getRearEngineThrust() > x - 1);
                        case 1:
                            return
                                    (((Engineer) player).getLeftEngineThrust() < x + 1) &&
                                            (((Engineer) player).getLeftEngineThrust() > x - 1);
                        case 2:
                            return
                                    (((Engineer) player).getRightEngineThrust() < x + 1) &&
                                            (((Engineer) player).getRightEngineThrust() > x - 1);
                        case 3:

                            if(startingToggle){
                            return
                                    !((Engineer) player).isRearEngineTurbo();
                            }else {
                                return ((Engineer) player).isRearEngineTurbo();
                            }
                        case 4:

                            if(startingToggle){
                                return
                                        !((Engineer) player).isLeftEngineTurbo();
                            }else {
                                return ((Engineer) player).isLeftEngineTurbo();
                            }
                        default:

                            if(startingToggle){
                                return
                                        !((Engineer) player).isRightEngineTurbo();
                            }else {
                                return ((Engineer) player).isRightEngineTurbo();
                            }
                    }
                }

                @Override
                public int getValue() {
                    return x;
                }

                @Override
                public String getElement() {
                    switch (y){
                        case 0:
                            return"rear engine thrust set to " + x;
                        case 1:
                            return"left engine thrust set to " + x;
                        case 2:
                            return"right engine thrust set to " + x;
                        case 3:
                            return"rear engine turbo toggle";
                        case 4:
                            return"left engine turbo toggle";
                        default:
                            return"right engine turbo toggle";
                    }
                }
            };
        }else if(player.getClass() == Defender.class){
            tmpOrder = new Order() {
                int x = (int) (Math.random() * 100);
                int y = (int) (Math.random() * 3);

                @Override
                public boolean checkOrder(Player player) {
                    switch (y){
                        case 0:
                            return
                                    (((Defender) player).getFrontShield() < x + 1) &&
                                            (((Defender) player).getFrontShield() > x - 1);
                        case 1:
                            return
                                    (((Defender) player).getLeftShield() < x + 1) &&
                                            (((Defender) player).getLeftShield() > x - 1);
                        default:
                            return
                                    (((Defender) player).getRightShield() < x + 1) &&
                                            (((Defender) player).getRightShield() > x - 1);
                    }
                }

                @Override
                public int getValue() {
                    return x;
                }

                @Override
                public String getElement() {
                    switch (y){
                        case 0:
                            return"front shield power set to " + x;
                        case 1:
                            return"left shield power set to " + x;
                        default:
                            return"right shield power set to " + x;
                    }
                }
            };
        }else {
            tmpOrder = new Order() {
                @Override
                public boolean checkOrder(Player player) {
                    return false;
                }

                @Override
                public int getValue() {
                    return 0;
                }

                @Override
                public String getElement() {
                    return null;
                }
            };
        }

        int x = tmpOrder.getValue();
        System.out.println("target = "+x);
        try {
            game.getCaptain().postOrder(player.toString() + " - "+tmpOrder.getElement() );
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return tmpOrder;
    }
}

