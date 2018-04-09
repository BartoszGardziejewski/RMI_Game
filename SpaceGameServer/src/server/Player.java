package server;

import java.io.Serializable;

public class Player implements Serializable{

    private String name;
    private boolean inGame = false;
    private Game myGame;

    protected int points=0;

    private Order order;

    public void addPoint(){
        points++;
    }

    public int getPoints(){
        return  points;
    }

    public void removePoint(){
        if(points>0) points--;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public Player(String name){
        this.name = name;
    }

    public boolean isInGame(){
        return inGame;
    }

    public void joinGame(Game game){
        inGame = true;
        myGame = game;
    }

    public  void leaveGame(){
        inGame = false;
        myGame = null;
    }

    public Game getGame(){
        return myGame;
    }

    public String getName(){
        return name;
    }

    @Override
    public  String toString(){
        return name;
    }
}
