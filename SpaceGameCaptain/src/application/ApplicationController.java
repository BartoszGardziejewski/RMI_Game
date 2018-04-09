package application;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.rmi.RemoteException;

public class ApplicationController {
    public Label MainLabel;
    private ApplicationManager manager;

    public VBox mainLog;

    public void setManager(ApplicationManager manager){
        this.manager = manager;
    }

    public void startGame(){
        try {
            manager.startGame();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
