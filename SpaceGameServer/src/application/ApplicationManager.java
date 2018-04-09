package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.GameServerController;
import server.Player;

import java.rmi.RemoteException;
import java.util.Map;

public class ApplicationManager extends Application {

        public GameServerController server;
        public ApplicationController controller;

        @Override
        public void start(Stage primaryStage) throws Exception{

            FXMLLoader loader = new  FXMLLoader(getClass().getResource("ServerWindow.fxml"));
            Parent root = loader.load();

            controller = loader.getController();

            primaryStage.setTitle(" Server ");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();

            setUpServer();
            refreshPlayersList();
        }

        @Override
        public void stop(){
            System.out.println("exiting ");
            System.exit(0);
        }


        private void setUpServer(){

            try {
                server = new GameServerController(1100,this, "playerConnection");
                addLogMassage("Connected to RMI on port - " + server.getPort());
                controller.MainLabel.setText("Connected on port "+server.getPort());
                controller.MainLabel.setStyle(" -fx-text-fill: rgb(50 ,150 ,100); ");
            } catch (RemoteException e) {

                e.printStackTrace();
                addLogMassage("Connecting to RMI failed");
                controller.MainLabel.setText("Connecting failed");
                controller.MainLabel.setStyle(" -fx-text-fill: rgb(200 ,60 ,60); ");
            }

        }

        public void addLogMassage(String massage){
            controller.serverLog.getChildren().add(new Label("-->  "+massage));
        }

        public void refreshPlayersList(){
            controller.playersList.getChildren().clear();
            controller.playersList.getChildren().add(new Label("- Players -"));
            String inGame;

            for (Map.Entry<String,Player> entry: server.players.entrySet()){

                Player player = entry.getValue();
                if(player.isInGame()){
                    inGame= "in game";
                }else {
                    inGame= "free";
                }



                controller.playersList.getChildren().add(new Label( player.toString() + "  ::  " + inGame));
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
}
