package network;

import javafx.application.Platform;
import utils.DTO;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class Client
{
    private Socket socket;
    private String info;
    private ClientHandler handler;

    private boolean handles;

    public Client(Socket socket)
    {
        this.socket = socket;
        this.handler = new ClientHandler(this);
        this.handles = false;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return info;
    }

    public void startProcessing()
    {
        if (!handles)
        {
            handles = true;

            Thread clientThread = new Thread(handler);
            clientThread.setDaemon(true);
            clientThread.start();
        }
    }

    public void disconnect()
    {
        if (handles)
        {
            Platform.runLater(() ->
            {
                DTO.getInstance().getClients().remove(this);

                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            });

            handler.shutdown();
        }
    }
}
