package utils;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DTO
{
    private UserDAO userDAO;
    private PlaceDAO placeDAO;
    private BoxDAO boxDAO;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private AddressDAO addressDAO;
    private WarehouseMapDAO warehouseMapDAO;
    private Integer port;
    private ObservableList<Client> clients;

    private static DTO dto;

    public static DTO getInstance()
    {
        synchronized (DTO.class)
        {
            if (dto == null)
            {
                dto = new DTO();
            }
        }

        return dto;
    }

    private DTO()
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("/resources/server-config.xml");
        userDAO = context.getBean("userDAO", UserDAO.class);
        placeDAO = context.getBean("placeDAO", PlaceDAO.class);
        boxDAO = context.getBean("boxDAO", BoxDAO.class);
        productDAO = context.getBean("productDAO", ProductDAO.class);
        orderDAO = context.getBean("orderDAO", OrderDAO.class);
        addressDAO = context.getBean("addressDAO", AddressDAO.class);
        warehouseMapDAO = context.getBean("warehouseMapDAO", WarehouseMapDAO.class);
        port = context.getBean("port", Integer.class);
        clients = FXCollections.observableArrayList();
    }

    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public PlaceDAO getPlaceDAO()
    {
        return placeDAO;
    }

    public void setPlaceDAO(PlaceDAO placeDAO)
    {
        this.placeDAO = placeDAO;
    }

    public BoxDAO getBoxDAO()
    {
        return boxDAO;
    }

    public void setBoxDAO(BoxDAO boxDAO)
    {
        this.boxDAO = boxDAO;
    }

    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }

    public OrderDAO getOrderDAO()
    {
        return orderDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO)
    {
        this.orderDAO = orderDAO;
    }

    public AddressDAO getAddressDAO()
    {
        return addressDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO)
    {
        this.addressDAO = addressDAO;
    }

    public WarehouseMapDAO getWarehouseMapDAO()
    {
        return warehouseMapDAO;
    }

    public void setWarehouseMapDAO(WarehouseMapDAO warehouseMapDAO)
    {
        this.warehouseMapDAO = warehouseMapDAO;
    }

    public ObservableList<Client> getClients()
    {
        return clients;
    }

    public void setClients(ObservableList<Client> clients)
    {
        this.clients = clients;
    }

    public Integer getPort()
    {
        return port;
    }

    public void setPort(Integer port)
    {
        this.port = port;
    }
}
