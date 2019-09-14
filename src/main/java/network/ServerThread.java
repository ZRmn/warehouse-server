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
                System.out.println("Waiting for client");

                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new ClientThread(clientSocket);
                clientThread.setDaemon(true);
                clientThread.setName("Client " + clientSocket.getInetAddress() + " Thread");
                clientThread.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
