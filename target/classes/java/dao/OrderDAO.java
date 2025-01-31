package dao;

import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import utils.IdSeeker;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;

@Component
public class OrderDAO implements CrudDAO<Order>
{
    private JdbcTemplate template;
    private Map<Integer, Order> ordersMap;

    private RowMapper<Order> orderRowMapper = (resultSet, i) ->
    {
        Integer orderId = resultSet.getInt("order_id");

        if (!ordersMap.containsKey(orderId))
        {
            Integer addressId = resultSet.getInt("addressId");
            String address = resultSet.getString("address");

            Order order = new Order(orderId, new Address(addressId, address), new ArrayList<>());

            ordersMap.put(orderId, order);
        }

        Integer boxId = resultSet.getInt("boxId");
        Integer productsCount = resultSet.getInt("products_count");

        Integer productId = resultSet.getInt("productId");
        Long code = resultSet.getLong("code");
        String description = resultSet.getString("description");
        Integer count = resultSet.getInt("boxes_count");

        Product product = new Product(productId, code, description);
        Box box = new Box(boxId, productsCount, product);

        ordersMap.get(orderId).getItems().add(new Item(box, count));

        return ordersMap.get(orderId);
    };

    @Autowired
    public OrderDAO(DataSource dataSource)
    {
        template = new JdbcTemplate(dataSource);
        ordersMap = new HashMap<>();
    }

    @Override
    public void create(Order model)
    {
        int id = findId();

        for (Item item : model.getItems())
        {
            String query = "INSERT INTO warehouse.order (address_id, boxes_count, box_id, order_id) VALUES(?, ?, ?, ?)";
            Object[] args = {model.getAddress().getId(), item.getCount(), item.getBox().getId(), id};
            int[] types = {Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};
            template.update(query, args, types);
        }
    }

    @Override
    public Order retrieve(Order model)
    {
        String query = "SELECT box.id AS boxId, product.id AS productId, * FROM warehouse.order LEFT JOIN warehouse.box ON warehouse.order.box_id = box.id LEFT JOIN warehouse.product ON box.product_id = product.id WHERE order_id = ?";
        Object[] args = {model.getId()};
        int[] types = {Types.INTEGER};
        template.query(query, args, types, orderRowMapper);

        List<Order> orders = new ArrayList<>(ordersMap.values());

        ordersMap.clear();

        return orders.isEmpty() ? null : orders.get(0);
    }

    @Override
    public List<Order> retrieveAll()
    {
        String query = "SELECT warehouse.address.id AS addressId, box.id AS boxId, product.id AS productId, * FROM warehouse.order LEFT JOIN warehouse.address ON warehouse.order.address_id = address.id LEFT JOIN warehouse.box ON warehouse.order.box_id = box.id LEFT JOIN warehouse.product ON box.product_id = product.id  ORDER BY date";
        template.query(query, orderRowMapper);
        List<Order> orders = new ArrayList<>(ordersMap.values());

        ordersMap.clear();

        return orders;
    }

    @Override
    public void update(Order model)
    {

    }

    @Override
    public void delete(Order model)
    {
        String query = "DELETE FROM warehouse.order WHERE order_id = ?";
        Object[] args = {model.getId()};
        int[] types = {Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public void deleteAll()
    {

    }

    private Integer findId()
    {
        String query = "SELECT order_id FROM warehouse.order";

        List<Integer> ids = new ArrayList<>(
                new HashSet<>(template.query(query, (resultSet, i) -> resultSet.getInt("order_id"))));

        return IdSeeker.find(ids.toArray(new Integer[0]));
    }

}
