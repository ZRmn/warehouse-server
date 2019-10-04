package network;

import app.DTO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread
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

            while (!this.isInterrupted())
            {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new ClientThread(clientSocket);
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
