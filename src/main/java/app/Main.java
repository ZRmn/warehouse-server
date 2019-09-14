package app;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import network.ServerThread;

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
        try
        {
            Thread serverThread = new ServerThread();
            serverThread.setDaemon(true);
            serverThread.setName("Server Thread");
            serverThread.start();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/resources/views/server.fxml")));
            scene.getStylesheets().add(0, "/resources/styles/style.css");

            stage = new Stage();
            stage.getIcons().add(new Image("/resources/images/gears.png"));
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
