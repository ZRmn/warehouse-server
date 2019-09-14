package models;

import java.sql.Timestamp;

public class Message
{
    private Integer id;
    private Integer userId;
    private Timestamp sendingDate;
    private String text;
    private User user;

    public Message(Integer id, Integer userId, Timestamp sendingDate, String text, User user)
    {
        this.id = id;
        this.userId = userId;
        this.sendingDate = sendingDate;
        this.text = text;
        this.user = user;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Timestamp getSendingDate()
    {
        return sendingDate;
    }

    public void setSendingDate(Timestamp sendingDate)
    {
        this.sendingDate = sendingDate;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
