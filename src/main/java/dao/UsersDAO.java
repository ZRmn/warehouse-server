package dao;

import models.Message;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.*;

@Component
public class UsersDAO implements CrudDAO<User>
{
    private JdbcTemplate template;
    private Map<Integer, User> usersMap = new HashMap<>();

    @Autowired
    public UsersDAO(DataSource dataSource)
    {
        this.template = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (resultSet, i) ->
    {
        Integer id = resultSet.getInt("user.id");

        if(!usersMap.containsKey(id))
        {
            String  login = resultSet.getString("login");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            Timestamp registrationDate = resultSet.getTimestamp("registration_date");
            Timestamp entryDate = resultSet.getTimestamp("entry_date");
            Boolean ban = resultSet.getBoolean("ban");

            User user = new User(id, login,password, email, registrationDate,entryDate,ban, new ArrayList<>());

            usersMap.put(id, user);
        }

        Integer messageId = resultSet.getInt("message.id");

        if(messageId != 0)
        {
            Integer userId = resultSet.getInt("message.user_id");
            Timestamp sendingDate = resultSet.getTimestamp("sending_date");
            String text = resultSet.getString("text");

            Message message = new Message(messageId, userId, sendingDate, text, usersMap.get(id));
            usersMap.get(id).getMessages().add(message);
        }

        return usersMap.get(id);
    };

    private RowMapper<User> simpleUserRowMapper = (resultSet, i) ->
    {
        Integer id = resultSet.getInt("user.id");
        String  login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        Timestamp registrationDate = resultSet.getTimestamp("registration_date");
        Timestamp entryDate = resultSet.getTimestamp("entry_date");
        Boolean ban = resultSet.getBoolean("ban");

        return new User(id, login,password, email, registrationDate,entryDate,ban, new ArrayList<>());
    };


    @Override
    public User get(User user)
    {
        return null;
    }

    @Override
    public List<User> getAll()
    {
        String query = "SELECT * FROM user LEFT JOIN message ON user.id = message.user_id";
        List<User> users;

        users = template.query(query, userRowMapper);
        users = new ArrayList<User>(new LinkedHashSet<User>(users));
        usersMap.clear();

        return users;
    }

    @Override
    public void create(User user)
    {
        String query = "INSERT INTO user (id, login, password, email, registration_date, entry_date, ban) VALUES(?, ?, ?, ?, ?, ?, ?)";
        Object[] args = {user.getId(), user.getLogin(), user.getPassword(), user.getEmail(), user.getRegistrationDate(), user.getEntryDate(), user.getBan()};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.BOOLEAN};

        template.update(query , args, types);
    }

    @Override
    public void update(User user)
    {
        String query = "UPDATE user SET login = ?, password = ?, email = ?, registration_date = ?, entry_date = ?, ban = ? WHERE id = ?";
        Object[] args = {user.getLogin(), user.getPassword(), user.getEmail(), user.getRegistrationDate(), user.getEntryDate(), user.getBan(), user.getId()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.BOOLEAN, Types.INTEGER};

        template.update(query , args, types);
    }

    @Override
    public void delete(User user)
    {
        String query = "DELETE FROM user WHERE id = ?";
        Object[] args = {user.getId()};
        int[] types = {Types.INTEGER};

        template.update(query , args, types);
    }

    @Override
    public void deleteAll()
    {
        String query = "DELETE FROM user";

        template.update(query);
    }

    public boolean containsUser(User user)
    {
        String query = "SELECT * FROM user WHERE login = ? AND password = ?";
        Object[] args = {user.getLogin(), user.getPassword()};
        List<User> users;

        users = template.query(query, args, simpleUserRowMapper);

        usersMap.clear();

        return users.size() > 0 ? true : false;

    }

    public boolean containsLogin(User user)
    {
        String query = "SELECT * FROM user WHERE login = ?";
        Object[] args = {user.getLogin()};
        List<User> users;

        users = template.query(query, args, simpleUserRowMapper);

        usersMap.clear();

        return users.size() > 0 ? true : false;

    }

    public List<Integer> getIds(List<User> users)
    {
        List<Integer> identificators = new ArrayList<>();

        for(User user: users)
        {
            identificators.add(user.getId());
        }

        return identificators;
    }


    class SortByDate implements Comparator<Message>
    {
        public int compare(Message a, Message b)
        {
            if(a.getSendingDate().after(b.getSendingDate()))
                return 1;
            else
                if(a.getSendingDate().before(b.getSendingDate()))
                    return -1;
                else
                    return 0;
        }
    }

    public List<Message> getAllMessages(List<User> users)
    {
        List<Message> messages = new ArrayList<>();

        for(User user: users)
        {
            messages.addAll(user.getMessages());
        }

        messages.sort(new SortByDate());

        return messages;
    }

}
