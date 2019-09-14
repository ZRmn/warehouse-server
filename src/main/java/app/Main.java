package app;

import controllers.ServerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/views/server.fxml"));
//            loader.setController(new ServerController());
//
//            Scene scene = new Scene(loader.load());

            DTO dto = DTO.getInstance();
            dto.getClients().addAll("1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1");

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/resources/views/server.fxml")));



            scene.getStylesheets().add(0, "/resources/styles/style.css");

            stage = new Stage();
            stage.getIcons().add(new Image("/resources/images/gears.png"));
            stage.setTitle("Server");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
