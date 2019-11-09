package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable
{
    private Client client;

    private boolean shutdown;

    public ClientHandler(Client client)
    {
        this.client = client;
        this.shutdown = false;
    }

    @Override
    public void run()
    {
        try
        {
            Socket socket = client.getSocket();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String[] request;
            String response;

            while (!shutdown)
            {
                request = in.readUTF().toLowerCase().split("\\?");

                switch (request[0])
                {
                    case "connect":
                    {
                        System.out.println("Client " + request[1] + " connected");

                        client.setInfo(request[1]);

                        break;
                    }

                    case "disconnect":
                    {
                        System.out.println("Client " + client.getInfo() + " disconnected");

                        client.disconnect();

                        break;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void shutdown()
    {
        this.shutdown = true;
    }
}