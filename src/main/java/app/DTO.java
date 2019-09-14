package app;

import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetAddress;
import java.util.List;

public class DTO
{
    private UsersDAO usersDAO;
    private boolean isConnected;
    private int port;
    private String ipAddress;
    private ObservableList<String> clients;

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
        ApplicationContext context = new ClassPathXmlApplicationContext("/resources/db-config.xml");
        usersDAO = context.getBean("usersDAO", UsersDAO.class);

        isConnected = false;
        port = 4815;
        clients = FXCollections.observableArrayList();

        try
        {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    public UsersDAO getUsersDAO()
    {
        return usersDAO;
    }

    public void setUsersDAO(UsersDAO usersDAO)
    {
        this.usersDAO = usersDAO;
    }

    public boolean isConnected()
    {
        return isConnected;
    }

    public void setConnected(boolean connected)
    {
        isConnected = connected;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public ObservableList<String> getClients()
    {
        return clients;
    }

    public void setClients(ObservableList<String> clients)
    {
        this.clients = clients;
    }
}
