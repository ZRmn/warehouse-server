package dao;

import models.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import utils.IdSeeker;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

@Component
public class AddressDAO implements CrudDAO<Address>
{
    private JdbcTemplate template;

    private RowMapper<Address> addressRowMapper = (resultSet, i) ->
    {
        Integer id = resultSet.getInt("id");
        String address = resultSet.getString("address");

        return new Address(id, address);
    };

    @Autowired
    public AddressDAO(DataSource dataSource)
    {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Address model)
    {
        String query = "INSERT INTO warehouse.address (id, address) VALUES(?, ?)";
        Object[] args = {findId(), model.getAddress()};
        int[] types = {Types.INTEGER, Types.VARCHAR};

        template.update(query, args, types);
    }

    @Override
    public Address retrieve(Address model)
    {
        String query = "SELECT * FROM warehouse.address WHERE id = ?";
        Object[] args = {model.getId()};
        int[] types = {Types.INTEGER};
        List<Address> addresses = template.query(query, args, types, addressRowMapper);

        return addresses.isEmpty() ? null : addresses.get(0);
    }

    @Override
    public List<Address> retrieveAll()
    {
        String query = "SELECT * FROM warehouse.address";
        return template.query(query, addressRowMapper);
    }

    @Override
    public void update(Address model)
    {
        String query = "UPDATE warehouse.address SET address = ? WHERE id = ?";
        Object[] args = {model.getAddress(), model.getId()};
        int[] types = {Types.VARCHAR, Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public void delete(Address model)
    {
        String query = "DELETE FROM warehouse.address WHERE id = ?";
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
        String query = "SELECT id FROM warehouse.address";

        List<Integer> ids = template.query(query, (resultSet, i) -> resultSet.getInt("id"));

        return IdSeeker.find(ids.toArray(new Integer[0]));
    }
}
