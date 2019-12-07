package models;

import java.util.List;

public class Order
{
    private Integer id;
    private Address address;
    private List<Item> items;

    public Order()
    {

    }

    public Order(Address address, List<Item> items)
    {
        this.address = address;
        this.items = items;
    }

    public Order(Integer id, Address address, List<Item> items)
    {
        this.id = id;
        this.address = address;
        this.items = items;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }
}
