package app;

import controllers.HomePageController;
import dao.UsersDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Message;
import models.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        try
        {
            ApplicationContext context = new ClassPathXmlApplicationContext("/resources/db-config.xml");

            UsersDAO usersDAO = context.getBean("usersDAO", UsersDAO.class);


            List<User> users = usersDAO.getAll();

            for (User user : users)
            {
                System.out.print(user.getLogin() + " - (");

                for (Message message : user.getMessages())
                {
                    System.out.print(message.getId() + ",");
                }

                System.out.println("\b)");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/views/HomePage.fxml"));
        loader.setController(new HomePageController());

        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(0, "/resources/styles/style.css");

        stage = new Stage();
        stage.getIcons().add(new Image("/resources/images/gears.png"));
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();
    }
}
