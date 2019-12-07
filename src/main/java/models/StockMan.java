package models;

public class StockMan extends User
{
    public StockMan()
    {

    }

    public StockMan(String login, String password, String fullName)
    {
        super(login, password, fullName);
    }

    public StockMan(Integer id, String login, String password, String fullName)
    {
        super(id, login, password, fullName);
    }
}
