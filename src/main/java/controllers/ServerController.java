package controllers;

import app.DTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.Socket;

public class ServerController
{
    @FXML
    private TextField port;
    @FXML
    private TextField ipAddress;
    @FXML
    private ListView<Socket> clients;

    private DTO dto;

    @FXML
    void initialize()
    {
        dto = DTO.getInstance();
        port.setText(Integer.toString(dto.getPort()));
        ipAddress.setText(dto.getIp());

        clients.setCellFactory(new Callback<ListView<Socket>, ListCell<Socket>>()
        {
            @Override
            public ListCell<Socket> call(ListView<Socket> socketListView)
            {
                return new ListCell<Socket>()
                {
                    @Override
                    protected void updateItem(Socket item, boolean empty)
                    {
                        super.updateItem(item, empty);

                        if (empty || item == null)
                        {
                            setText(null);
                            setGraphic(null);
                        }
                        else
                        {
                            setText(item.getInetAddress().getHostAddress());
                        }
                    }
                };
            }
        });

        clients.setItems(dto.getClients());
    }
}

