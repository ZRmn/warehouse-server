package dao;

import models.Box;
import models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import utils.IdSeeker;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

@Component
public class BoxDAO implements CrudDAO<Box>
{
    private JdbcTemplate template;

    private RowMapper<Box> boxRowMapper = (resultSet, i) ->
    {
        Integer boxId = resultSet.getInt("boxId");
        Integer productsCount = resultSet.getInt("products_count");

        Integer productId = resultSet.getInt("productId");
        Long code = resultSet.getLong("code");
        String description = resultSet.getString("description");

        Product product = new Product(productId, code, description);
        return new Box(boxId, productsCount, product);
    };

    @Autowired
    public BoxDAO(DataSource dataSource)
    {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Box model)
    {
        String query = "INSERT INTO warehouse.box (id, products_count, product_id) VALUES(?, ?, ?)";
        Object[] args = {findId(), model.getCount(), model.getProduct() != null ? model.getProduct().getId() : null};
        int[] types = {Types.INTEGER, Types.INTEGER, Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public Box retrieve(Box model)
    {
        String query = "SELECT warehouse.box.id AS boxId, warehouse.product.id AS productId, * FROM warehouse.box LEFT JOIN warehouse.product ON box.product_id = product.id WHERE box.id = ?";
        Object[] args = {model.getId()};
        int[] types = {Types.INTEGER};
        List<Box> boxes = template.query(query, args, types, boxRowMapper);

        return boxes.isEmpty() ? null : boxes.get(0);
    }

    @Override
    public List<Box> retrieveAll()
    {
        String query = "SELECT warehouse.box.id AS boxId, warehouse.product.id AS productId, * FROM warehouse.box LEFT JOIN warehouse.product ON box.product_id = product.id";
        return template.query(query, boxRowMapper);
    }

    @Override
    public void update(Box model)
    {
        String query = "UPDATE warehouse.box SET products_count = ?, product_id = ? WHERE id = ?";
        Object[] args = {model.getCount(), model.getProduct() != null ? model.getProduct().getId() : null, model.getId()};
        int[] types = {Types.INTEGER, Types.INTEGER, Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public void delete(Box model)
    {
        String query = "DELETE FROM warehouse.box WHERE id = ?";
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
        String query = "SELECT id FROM warehouse.box";

        List<Integer> ids = template.query(query, (resultSet, i) -> resultSet.getInt("id"));

        return IdSeeker.find(ids.toArray(new Integer[0]));
    }
}
