package network;

import javafx.application.Platform;
import models.*;
import org.springframework.dao.DataAccessException;
import utils.DTO;
import utils.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientHandler implements Runnable
{
    private Client client;
    private boolean shutdown;
    private DTO dto;

    public ClientHandler(Client client)
    {
        this.client = client;
        this.shutdown = false;
        this.dto = DTO.getInstance();
    }

    @Override
    public void run()
    {
        try
        {
            Socket socket = client.getSocket();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String request;
            String response;
            String[] temp;
            String command;
            String[] args;


            while (!shutdown)
            {
                request = in.readUTF();
                temp = request.split("\\?");
                command = temp[0];

                args = temp.length > 1 ? temp[1].split("&") : null;

                switch (command)
                {
                    case "connect":
                    {
                        if (args != null)
                        {
                            client.setInfo(args[0]);

                            System.out.println("Client " + args[0] + " connected on " + ZonedDateTime.now().format(
                                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

                            Platform.runLater(() -> dto.getClients().add(client));
                        }

                        break;
                    }

                    case "disconnect":
                    {
                        System.out.println(
                                "Client " + client.getInfo() + " disconnected on " + ZonedDateTime.now().format(
                                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

                        Platform.runLater(() -> dto.getClients().remove(client));

                        client.disconnect();

                        break;
                    }

                    case "has-admin":
                    {
                        if (dto.getUserDAO().hasAdmin())
                        {
                            response = "200";
                        }
                        else
                        {
                            response = "400";
                        }

                        out.writeUTF(response);
                        out.flush();

                        break;
                    }

                    case "authorise-user":
                    {
                        if (args != null)
                        {
                            User user = dto.getUserDAO().checkUser(args[0], args[1]);

                            response = user == null ? "400" : JsonParser.jsonFromObject(user);

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "add-user":
                    {
                        if (args != null)
                        {
                            User user = JsonParser.objectFromJson(args[0], User.class);

                            try
                            {
                                dto.getUserDAO().create(user);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "edit-user":
                    {
                        if (args != null)
                        {
                            User user = JsonParser.objectFromJson(args[0], User.class);

                            try
                            {
                                dto.getUserDAO().update(user);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "delete-user":
                    {
                        if (args != null)
                        {
                            User user = JsonParser.objectFromJson(args[0], User.class);

                            dto.getUserDAO().delete(user);
                        }

                        break;
                    }

                    case "get-users":
                    {
                        response = JsonParser.jsonFromObject(dto.getUserDAO().retrieveAll().toArray(new User[0]));

                        out.writeUTF(response);
                        out.flush();

                        break;
                    }

                    case "add-product":
                    {
                        if (args != null)
                        {
                            Product product = JsonParser.objectFromJson(args[0], Product.class);

                            try
                            {
                                dto.getProductDAO().create(product);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "edit-product":
                    {
                        if (args != null)
                        {
                            Product product = JsonParser.objectFromJson(args[0], Product.class);

                            try
                            {
                                dto.getProductDAO().update(product);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "delete-product":
                    {
                        if (args != null)
                        {
                            Product product = JsonParser.objectFromJson(args[0], Product.class);

                            dto.getProductDAO().delete(product);
                        }

                        break;
                    }

                    case "get-products":
                    {
                        response = JsonParser.jsonFromObject(dto.getProductDAO().retrieveAll().toArray(new Product[0]));

                        out.writeUTF(response);
                        out.flush();

                        break;
                    }

                    case "add-box":
                    {
                        if (args != null)
                        {
                            Box box = JsonParser.objectFromJson(args[0], Box.class);

                            try
                            {
                                dto.getBoxDAO().create(box);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "edit-box":
                    {
                        if (args != null)
                        {
                            Box box = JsonParser.objectFromJson(args[0], Box.class);

                            try
                            {
                                dto.getBoxDAO().update(box);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "delete-box":
                    {
                        if (args != null)
                        {
                            Box box = JsonParser.objectFromJson(args[0], Box.class);

                            dto.getBoxDAO().delete(box);
                        }

                        break;
                    }

                    case "get-boxes":
                    {
                        response = JsonParser.jsonFromObject(dto.getBoxDAO().retrieveAll().toArray(new Box[0]));

                        out.writeUTF(response);
                        out.flush();

                        break;
                    }

                    case "add-order":
                    {
                        if (args != null)
                        {
                            Order order = JsonParser.objectFromJson(args[0], Order.class);

                            try
                            {
                                dto.getOrderDAO().create(order);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "edit-order":
                    {
                        if (args != null)
                        {
                            Order order = JsonParser.objectFromJson(args[0], Order.class);

                            try
                            {
                                dto.getOrderDAO().update(order);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "delete-order":
                    {
                        if (args != null)
                        {
                            Order order = JsonParser.objectFromJson(args[0], Order.class);

                            dto.getOrderDAO().delete(order);
                        }

                        break;
                    }

                    case "get-orders":
                    {
                        response = JsonParser.jsonFromObject(dto.getOrderDAO().retrieveAll().toArray(new Order[0]));

                        out.writeUTF(response);
                        out.flush();

                        break;
                    }

                    case "add-address":
                    {
                        if (args != null)
                        {
                            Address address = JsonParser.objectFromJson(args[0], Address.class);

                            try
                            {
                                dto.getAddressDAO().create(address);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "edit-address":
                    {
                        if (args != null)
                        {
                            Address address = JsonParser.objectFromJson(args[0], Address.class);

                            try
                            {
                                dto.getAddressDAO().update(address);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "delete-address":
                    {
                        if (args != null)
                        {
                            Address address = JsonParser.objectFromJson(args[0], Address.class);

                            dto.getAddressDAO().delete(address);
                        }

                        break;
                    }

                    case "get-addresses":
                    {
                        response = JsonParser.jsonFromObject(dto.getAddressDAO().retrieveAll().toArray(new Address[0]));

                        out.writeUTF(response);
                        out.flush();

                        break;
                    }

                    case "edit-warehouse-map":
                    {
                        if (args != null)
                        {
                            WarehouseMap warehouseMap = JsonParser.objectFromJson(args[0], WarehouseMap.class);

                            try
                            {
                                dto.getWarehouseMapDAO().deleteAll();
                                dto.getWarehouseMapDAO().create(warehouseMap);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "get-warehouse-map":
                    {
                        List<WarehouseMap> warehouseMaps = dto.getWarehouseMapDAO().retrieveAll();

                        if (!warehouseMaps.isEmpty())
                        {
                            response = JsonParser.jsonFromObject(warehouseMaps.get(0));
                        }
                        else
                        {
                            response = "400";
                        }

                        out.writeUTF(response);
                        out.flush();

                        break;
                    }

                    case "get-place":
                    {
                        if (args != null)
                        {
                            Place place = dto.getPlaceDAO().getPlaceByPosition(args[0]);

                            if (place != null)
                            {
                                response = JsonParser.jsonFromObject(place);
                            }
                            else
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "add-place":
                    {
                        if (args != null)
                        {
                            Place place = JsonParser.objectFromJson(args[0], Place.class);

                            try
                            {
                                dto.getPlaceDAO().create(place);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "set-place-capacity":
                    {
                        if (args != null)
                        {
                            Integer capacity = JsonParser.objectFromJson(args[0], Integer.class);
                            dto.getPlaceDAO().setCapacity(capacity);
                        }

                        break;
                    }

                    case "edit-place":
                    {
                        if (args != null)
                        {
                            Place place = JsonParser.objectFromJson(args[0], Place.class);

                            try
                            {
                                dto.getPlaceDAO().update(place);
                                response = "200";
                            }
                            catch (DataAccessException e)
                            {
                                response = "400";
                            }

                            out.writeUTF(response);
                            out.flush();
                        }

                        break;
                    }

                    case "delete-place":
                    {
                        if (args != null)
                        {
                            Place place = JsonParser.objectFromJson(args[0], Place.class);

                            dto.getPlaceDAO().delete(place);
                        }

                        break;
                    }

                    case "get-places":
                    {
                        response = JsonParser.jsonFromObject(dto.getPlaceDAO().retrieveAll().toArray(new Place[0]));

                        out.writeUTF(response);
                        out.flush();

                        break;
                    }

                    default:
                    {
                        out.writeUTF("400");
                        out.flush();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void shutdown()
    {
        this.shutdown = true;
    }
}