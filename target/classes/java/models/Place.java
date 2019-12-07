package models;

import java.util.List;

public class Place
{
    private Integer id;
    private String address;
    private Integer capacity;
    private Integer fullness;
    private List<Box> boxes;

    public Place()
    {
        
    }

    public Place(String address, Integer capacity, Integer fullness, List<Box> boxes)
    {
        this.address = address;
        this.capacity = capacity;
        this.fullness = fullness;
        this.boxes = boxes;
    }

    public Place(Integer id, String address, Integer capacity, Integer fullness, List<Box> boxes)
    {
        this.id = id;
        this.address = address;
        this.capacity = capacity;
        this.fullness = fullness;
        this.boxes = boxes;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Integer getCapacity()
    {
        return capacity;
    }

    public void setCapacity(Integer capacity)
    {
        this.capacity = capacity;
    }

    public Integer getFullness()
    {
        return fullness;
    }

    public void setFullness(Integer fullness)
    {
        this.fullness = fullness;
    }

    public List<Box> getBoxes()
    {
        return boxes;
    }

    public void setBoxes(List<Box> boxes)
    {
        this.boxes = boxes;
    }
}
