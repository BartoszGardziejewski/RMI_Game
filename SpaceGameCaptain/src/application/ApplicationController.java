package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.rmi.RemoteException;

public class ApplicationController {
    public Label MainLabel;
    private ApplicationManager manager;

    public Button bottomButton;
    public VBox mainLog;

    public void setManager(ApplicationManager manager){
        this.manager = manager;
    }



    public void startGame(){

        bottomButton.setText("Exit Game");
        bottomButton.getStyleClass().set(1,"button-bad");
        bottomButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    manager.stopGame();

                    bottomButton.setText("Start Game");
                    bottomButton.getStyleClass().set(1,"button-good");

                    bottomButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            startGame();
                        }
                    });

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            manager.startGame();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
