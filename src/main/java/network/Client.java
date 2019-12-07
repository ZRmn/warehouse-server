package network;

import java.io.IOException;
import java.net.Socket;

public class Client
{
    private Socket socket;
    private String info;
    private ClientHandler handler;

    public Client(Socket socket)
    {
        this.socket = socket;
        this.handler = new ClientHandler(this);
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
        Thread clientThread = new Thread(handler);
        clientThread.setDaemon(true);
        clientThread.start();
    }

    public void disconnect()
    {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        handler.shutdown();
    }
}
