package app;

import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DTO
{
    private UsersDAO usersDAO;
    private Integer port;
    private String ip;
    private ObservableList<Socket> clients;

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

        try
        {
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
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

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public ObservableList<Socket> getClients()
    {
        return clients;
    }

    public void setClients(ObservableList<Socket> clients)
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
