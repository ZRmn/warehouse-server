package models;

import java.sql.Timestamp;
import java.util.List;

public class User
{
    private enum Role
    {
        ADMIN,
        USER
    }

    private Integer id;
    private String login;
    private String password;
    private String email;
    private Timestamp registrationDate;
    private Timestamp entryDate;
    private Boolean ban;
    private Role role;
    private List<Message> messages;

    public User()
    {

    }


    public User(Integer id, String login, String password, String email, Timestamp registrationDate,
                Timestamp entryDate, Boolean ban, List<Message> messages)
    {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.registrationDate = registrationDate;
        this.entryDate = entryDate;
        this.ban = ban;
        this.messages = messages;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Timestamp getRegistrationDate()
    {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public Timestamp getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate)
    {
        this.entryDate = entryDate;
    }

    public Boolean getBan()
    {
        return ban;
    }

    public void setBan(Boolean ban)
    {
        this.ban = ban;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }
}
