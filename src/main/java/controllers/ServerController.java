package controllers;

import app.DTO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ServerController
{
    @FXML
    private TextField port;
    @FXML
    private TextField ipAddress;
    @FXML
    private ListView<String> clientsList;

    private DTO dto;

    @FXML
    void initialize()
    {
        dto = DTO.getInstance();
        port.setText(Integer.toString(dto.getPort()));
        ipAddress.setText(dto.getIpAddress());
        clientsList.setItems(dto.getClients());
    }
}

