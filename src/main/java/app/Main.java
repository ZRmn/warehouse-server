package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.image.Image;

import javafx.stage.Stage;
import network.Server;
import utils.MD5;

import java.io.IOException;
public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        Thread serverThread = new Thread(new Server());
        serverThread.setDaemon(true);
        serverThread.start();

        System.out.println(MD5.encode("ZRmn4815162342"));


        try
        {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/resources/views/server.fxml")));
            scene.getStylesheets().add(0, "/resources/styles/style.css");

            stage.getIcons().add(new Image("/resources/images/server.png"));
            stage.setTitle("Server");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
