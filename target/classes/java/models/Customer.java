package models;

public class Customer extends User
{
    public Customer()
    {

    }

    public Customer(String login, String password, String fullName)
    {
        super(login, password, fullName);
    }

    public Customer(Integer id, String login, String password, String fullName)
    {
        super(id, login, password, fullName);
    }
}
