package app;

import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class DTO
{
    private UsersDAO usersDAO;
    private Integer port;
    private String ip;
    private ObservableList<String> clients;
    private List<Socket> clientSockets;

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
        clientSockets = new ArrayList<>();

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

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public ObservableList<String> getClients()
    {
        return clients;
    }

    public void setClients(ObservableList<String> clients)
    {
        this.clients = clients;
    }

    public List<Socket> getClientSockets()
    {
        return clientSockets;
    }

    public void setClientSockets(List<Socket> clientSockets)
    {
        this.clientSockets = clientSockets;
    }
}
