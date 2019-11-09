package utils;

import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DTO
{
    private UsersDAO usersDAO;
    private Integer port;
    private ObservableList<Client> clients;

    private static DTO dto;

    public static DTO getInstance()
    {
        synchronized (DTO.class)
        {
            if (dto == null)
            {
                dto = new DTO();
            }
        }

        return dto;
    }

    private DTO()
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("/resources/server-config.xml");
        usersDAO = context.getBean("usersDAO", UsersDAO.class);
        port = context.getBean("port", Integer.class);
        clients = FXCollections.observableArrayList();
    }

    public UsersDAO getUsersDAO()
    {
        return usersDAO;
    }

    public void setUsersDAO(UsersDAO usersDAO)
    {
        this.usersDAO = usersDAO;
    }

    public ObservableList<Client> getClients()
    {
        return clients;
    }

    public void setClients(ObservableList<Client> clients)
    {
        this.clients = clients;
    }

    public Integer getPort()
    {
        return port;
    }

    public void setPort(Integer port)
    {
        this.port = port;
    }
}
