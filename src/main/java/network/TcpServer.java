package network;

import utils.DTO;
import java.net.ServerSocket;

public class TcpServer implements Runnable
{
    private boolean shutdown;

    public TcpServer()
    {
        this.shutdown = false;
    }

    @Override
    public void run()
    {
        try
        {
            DTO dto = DTO.getInstance();
            ServerSocket serverSocket = new ServerSocket(dto.getPort());

            System.out.println("Server running");

            while (!shutdown)
            {
                Client client = new Client(serverSocket.accept());
                client.startProcessing();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void shutdown()
    {
        shutdown = true;
    }
}
