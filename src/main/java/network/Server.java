package network;

import app.DTO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable
{
    private DTO dto;

    @Override
    public void run()
    {
        try
        {
            dto = DTO.getInstance();
            ServerSocket serverSocket = new ServerSocket(dto.getPort());

            System.out.println("Server running");

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.setDaemon(true);
                clientThread.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
