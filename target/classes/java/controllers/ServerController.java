package controllers;

import network.Client;
import utils.DTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerController
{
    @FXML
    private TextField port;
    @FXML
    private TextField host;
    @FXML
    private ListView<Client> clients;

    @FXML
    void initialize()
    {
        port.setText(Integer.toString(DTO.getInstance().getPort()));
        clients.setItems(DTO.getInstance().getClients());

        try
        {
            host.setText(InetAddress.getLocalHost().getHostAddress());
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}

