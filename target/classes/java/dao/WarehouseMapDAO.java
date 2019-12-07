package dao;

import models.WarehouseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

@Component
public class WarehouseMapDAO implements CrudDAO<WarehouseMap>
{
    private JdbcTemplate template;

    private RowMapper<WarehouseMap> warehouseMapRowMapper = (resultSet, i) ->
    {
        Integer rows = resultSet.getInt("rows");
        Integer columns = resultSet.getInt("columns");

        return new WarehouseMap(rows, columns);
    };

    @Autowired
    public WarehouseMapDAO(DataSource dataSource)
    {
        template = new JdbcTemplate(dataSource);
    }


    @Override
    public void create(WarehouseMap model)
    {
        String query = "INSERT INTO warehouse.map (rows, columns) VALUES(?, ?)";
        Object[] args = {model.getRows(), model.getColumns()};
        int[] types = {Types.INTEGER, Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public WarehouseMap retrieve(WarehouseMap model)
    {
        return null;
    }

    @Override
    public List<WarehouseMap> retrieveAll()
    {
        String query = "SELECT * FROM warehouse.map";
        return template.query(query, warehouseMapRowMapper);
    }

    @Override
    public void update(WarehouseMap model)
    {

    }

    @Override
    public void delete(WarehouseMap model)
    {

    }

    @Override
    public void deleteAll()
    {
        String query = "DELETE FROM warehouse.map";
        template.update(query);
    }
}
