package models;

import java.util.List;

public class Place
{
    private Integer id;
    private String position;
    private Integer capacity;
    private Integer fullness;
    private List<Pallet> pallets;

    public Place()
    {
        
    }

    public Place(String position, Integer capacity, Integer fullness, List<Pallet> pallets)
    {
        this.position = position;
        this.capacity = capacity;
        this.fullness = fullness;
        this.pallets = pallets;
    }

    public Place(Integer id, String position, Integer capacity, Integer fullness, List<Pallet> pallets)
    {
        this.id = id;
        this.position = position;
        this.capacity = capacity;
        this.fullness = fullness;
        this.pallets = pallets;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
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

    public List<Pallet> getPallets()
    {
        return pallets;
    }

    public void setPallets(List<Pallet> pallets)
    {
        this.pallets = pallets;
    }
}
