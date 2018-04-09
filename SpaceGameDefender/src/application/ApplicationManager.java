package application;

import client.GameClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.Defender;
import server.Engineer;
import server.GameServerController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ApplicationManager extends Application {

        private ApplicationController controller;
        private Defender myDefender;

        public GameClientController client;
        private static String clientName;

        @Override
        public void start(Stage primaryStage) throws IOException {

            FXMLLoader loader = new  FXMLLoader(getClass().getResource("ClientWindow.fxml"));
            Parent root = loader.load();

            controller = loader.getController();
            controller.setManager(this);

            primaryStage.setTitle(" Client ");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();

            connectToServer();
        }

        public void acknowledgeCaptain(){
            myDefender.setShield(
                    controller.frontShieldPower.getValue(),
                    controller.leftShieldPower.getValue(),
                    controller.rightShieldPower.getValue()
            );

            client.sendMassage();
        }

        public void setPoints(){
            controller.MainLabel.setText(myDefender.toString()+ "  Point: " +  myDefender.getPoints());
        }

        private void connectToServer(){

            myDefender = new Defender(clientName);

            controller.MainLabel.setText(myDefender.toString());

            try {
                client = new GameClientController(1100,myDefender,this);

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

        public static void main(String[] args) {
            if(args.length == 0){
                clientName = "player";
            }
            else {
                clientName = args[0];
            }
            launch(args);
        }
}
