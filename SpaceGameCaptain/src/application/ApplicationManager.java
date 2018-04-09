package application;

import client.GameCaptainClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.GameServerController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ApplicationManager extends Application {

        private  ApplicationController controller;
        private GameServerController server;

        private static int port;

    public GameCaptainClient client;

        private static String clientName;

        @Override
        public void start(Stage primaryStage) throws IOException {

            FXMLLoader loader = new  FXMLLoader(getClass().getResource("ClientWindow.fxml"));
            Parent root = loader.load();

            controller = loader.getController();
            controller.setManager(this);

            primaryStage.setTitle(" Captain ");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();

            connectToServer();
        }

        public void startGame() throws RemoteException {
            setUpServer( client.setGameServer() );
        }

        public void stopGame() throws RemoteException {
            client.stopGameServer();
            server = null;
        }

        private void setUpServer(String name){

            try {
                server = new GameServerController(port,this, name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        private void connectToServer(){

            controller.MainLabel.setText("Hallow "+clientName);

            try {
                client = new GameCaptainClient(port,clientName,this);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void stop() throws RemoteException {
            client.logOut();
            System.out.println("exiting");
            System.exit(0);
        }

        public void displayMessage(String message){
            Platform.runLater(()->{
                    controller.mainLog.getChildren().add(new Label(message));}
                    );
        }

        public static void main(String[] args) {
            if(args.length > 0 && args[0]!=null){
                clientName = args[0];
            }
            else {
                clientName = "player";
            }
            if(args.length > 1 && args[1]!=null){
                port=Integer.parseInt(args[1]);
            }else {
                port=1100;
            }

            launch(args);
        }
}
