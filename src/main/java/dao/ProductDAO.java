package dao;

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
public class ProductDAO implements CrudDAO<Product>
{
    private JdbcTemplate template;

    private RowMapper<Product> productRowMapper = (resultSet, i) ->
    {
        Integer id = resultSet.getInt("id");
        Long code = resultSet.getLong("code");
        String description = resultSet.getString("description");

        return new Product(id, code, description);
    };

    @Autowired
    public ProductDAO(DataSource dataSource)
    {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Product model)
    {
        String query = "INSERT INTO warehouse.product (id, code, description) VALUES(?, ?, ?)";
        Object[] args = {findId(), model.getCode(), model.getDescription()};
        int[] types = {Types.INTEGER, Types.BIGINT, Types.VARCHAR};

        template.update(query, args, types);
    }

    @Override
    public Product retrieve(Product model)
    {
        String query = "SELECT * FROM warehouse.product WHERE id = ?";
        Object[] args = {model.getId()};
        int[] types = {Types.INTEGER};
        List<Product> products = template.query(query, args, types, productRowMapper);

        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public List<Product> retrieveAll()
    {
        String query = "SELECT * FROM warehouse.product";
        return template.query(query, productRowMapper);
    }

    @Override
    public void update(Product model)
    {
        String query = "UPDATE warehouse.product SET code = ?, description = ? WHERE id = ?";
        Object[] args = {model.getCode(), model.getDescription(), model.getId()};
        int[] types = {Types.BIGINT, Types.VARCHAR, Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public void delete(Product model)
    {
        String query = "DELETE FROM warehouse.product WHERE id = ?";
        Object[] args = {model.getId()};
        int[] types = {Types.INTEGER};

        template.update(query , args, types);
    }

    @Override
    public void deleteAll()
    {

    }

    private Integer findId()
    {
        String query = "SELECT id FROM warehouse.product";

        List<Integer> ids = template.query(query, (resultSet, i) -> resultSet.getInt("id"));

        return IdSeeker.find(ids.toArray(new Integer[0]));
    }
}
