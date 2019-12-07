package models;

public class CheckMan extends User
{
    public CheckMan()
    {

    }

    public CheckMan(String login, String password, String fullName)
    {
        super(login, password, fullName);
    }

    public CheckMan(Integer id, String login, String password, String fullName)
    {
        super(id, login, password, fullName);
    }
}
