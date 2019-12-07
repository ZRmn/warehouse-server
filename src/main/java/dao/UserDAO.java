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
public class UserDAO implements CrudDAO<User>
{
    private JdbcTemplate template;

    private RowMapper<User> userRowMapper = (resultSet, i) ->
    {
        Integer id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String fullName = resultSet.getString("full_name");
        String role = resultSet.getString("role");

        User user;

        switch (role)
        {
            case "Администратор":
            {
                user = new Admin(id, login, password, fullName);
                break;
            }

            case "Учетчик":
            {
                user = new CheckMan(id, login, password, fullName);
                break;
            }

            case "Заказчик":
            {
                user = new Customer(id, login, password, fullName);
                break;
            }

            default:
            {
                user = new StockMan(id, login, password, fullName);
            }
        }

        return user;
    };

    @Autowired
    public UserDAO(DataSource dataSource)
    {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(User user)
    {
        String query = "INSERT INTO warehouse.user (id, login, password, full_name, role) VALUES(?, ?, ?, ?, ?)";
        Object[] args = {findId(), user.getLogin(), user.getPassword(), user.getFullName(), this.getRole(user)};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};

        template.update(query, args, types);
    }

    @Override
    public User retrieve(User user)
    {
        String query = "SELECT * FROM warehouse.user WHERE id = ?";
        Object[] args = {user.getId()};
        int[] types = {Types.INTEGER};
        List<User> users = template.query(query, args, types, userRowMapper);

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> retrieveAll()
    {
        String query = "SELECT * FROM warehouse.user";
        return template.query(query, userRowMapper);
    }


    @Override
    public void update(User user)
    {
        String query = "UPDATE warehouse.user SET login = ?, password = ?, full_name = ?, role = ? WHERE id = ?";
        Object[] args = {user.getLogin(), user.getPassword(), user.getFullName(), this.getRole(user), user.getId()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public void delete(User user)
    {
        String query = "DELETE FROM warehouse.user WHERE id = ?";
        Object[] args = {user.getId()};
        int[] types = {Types.INTEGER};

        template.update(query, args, types);
    }

    @Override
    public void deleteAll()
    {
        String query = "DELETE FROM warehouse.user";

        template.update(query);
    }

    public boolean hasAdmin()
    {
        String query = "SELECT * FROM warehouse.user WHERE role='Администратор'";
        List<User> users =  template.query(query, userRowMapper);

        return !users.isEmpty();
    }

    public User checkUser(String login, String password)
    {
        String query = "SELECT * FROM warehouse.user WHERE login=? AND password=?";
        Object[] args = {login, password};
        int[] types = {Types.VARCHAR, Types.VARCHAR};

        List<User> users =  template.query(query, args, types, userRowMapper);

        return users.isEmpty() ? null : users.get(0);
    }

    private String getRole(User user)
    {
        String role;

        switch(user.getClass().getSimpleName())
        {
            case "Admin":
            {
                role = "Администратор";
                break;
            }

            case "CheckMan":
            {
                role = "Учетчик";
                break;
            }

            case "Customer":
            {
                role = "Заказчик";
                break;
            }

            default:
            {
                role = "Кладовщик";
            }
        }

        return role;
    }

    private Integer findId()
    {
        String query = "SELECT id FROM warehouse.user";
        List<Integer> ids = template.query(query, (resultSet, i) -> resultSet.getInt("id"));

        return IdSeeker.find(ids.toArray(new Integer[0]));
    }
}
