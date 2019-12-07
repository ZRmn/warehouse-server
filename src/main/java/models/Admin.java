package models;

public class Admin extends User
{
    public Admin()
    {

    }

    public Admin(String login, String password, String fullName)
    {
        super(login, password, fullName);
    }

    public Admin(Integer id, String login, String password, String fullName)
    {
        super(id, login, password, fullName);
    }
}
