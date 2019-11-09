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
    private TextField ipAddress;
    @FXML
    private ListView<Client> clients;

    private DTO dto;

    @FXML
    void initialize()
    {
        dto = DTO.getInstance();
        port.setText(Integer.toString(dto.getPort()));
        clients.setItems(dto.getClients());

        try
        {
            ipAddress.setText(InetAddress.getLocalHost().getHostAddress());
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}

