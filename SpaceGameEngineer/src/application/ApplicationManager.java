package application;

import client.GameClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.Engineer;
import server.GameServerController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ApplicationManager extends Application {

        private ApplicationController controller;
        private Engineer myEngineer;

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

        public void setPoints(){
            controller.MainLabel.setText( myEngineer.toString() + "  Point: " +  myEngineer.getPoints());
        }

        public void acknowledgeCaptain(){
            myEngineer.setThrusts(
                    controller.rearEngineThrust.getValue(),
                    controller.leftEngineThrust.getValue(),
                    controller.rightEngineThrust.getValue()
            );

            myEngineer.setTurbo(
                    controller.rearEngineTurbo.getValue(),
                    controller.leftEngineTurbo.getValue(),
                    controller.rightEngineTurbo.getValue()
            );

            client.sendMassage();
        }

        private void connectToServer(){

            myEngineer = new Engineer(clientName);

            controller.MainLabel.setText( myEngineer.toString() );

            try {
                client = new GameClientController(1100,myEngineer,this);

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
